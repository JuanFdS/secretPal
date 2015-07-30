package com.tenPines.restAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tenPines.application.SecretPalSystem;
import com.tenPines.model.Worker;
import com.tenPines.utils.AuthUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SecretPalSystem system;

    @RequestMapping(value = "/google", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Token> loginGoogle(@RequestBody Payload payload, @Context final HttpServletRequest request) throws IOException, JOSEException {

        HttpPost post = new HttpPost("https://accounts.google.com/o/oauth2/token");
        post.setEntity(new UrlEncodedFormEntity(buildOAuthRequest(payload)));
        JSONObject jsonResponse = makeRequest(post);
        String access_token = (String) jsonResponse.get("access_token");

       HttpGet get = new HttpGet("https://www.googleapis.com/plus/v1/people/me/openIdConnect");
        get.addHeader("Authorization",  String.format("Bearer %s", access_token));
        JSONObject userInfoResponse = makeRequest(get);

        return authWorker(request, userInfoResponse.get("email").toString());



    }

    private ResponseEntity<Token> authWorker(HttpServletRequest request, String workerEmail) throws JOSEException {
        Optional<Worker> anUser = system.retrieveWorkerByEmail(workerEmail);
        if (anUser.isPresent()) {
            Token token = AuthUtils.createToken(request.getRemoteHost(), anUser.get().getId());
            return new ResponseEntity<Token>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<Token>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private List<BasicNameValuePair> buildOAuthRequest(Payload payload) {
        return Arrays.asList(
            new BasicNameValuePair("code", payload.getCode()),
            new BasicNameValuePair("client_id", payload.getClientId()),
            new BasicNameValuePair("client_secret", "e9P_yLxXR7yoJR5PuwOytS4-"),
            new BasicNameValuePair("redirect_uri", payload.getRedirectUri()),
            new BasicNameValuePair("grant_type", "authorization_code"));
    }

    private JSONObject makeRequest(HttpRequestBase request) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse userResponse = httpClient.execute(request);
        return new JSONObject(EntityUtils.toString(userResponse.getEntity()));
    }

    public static class Payload {
        String clientId;
        String redirectUri;
        String code;

        public String getClientId() {
            return clientId;
        }
        public String getRedirectUri() {
            return redirectUri;
        }
        public String getCode() {
            return code;
        }
    }

    public static class Token {
        String token;
        public Token(@JsonProperty("token") String token) {
            this.token = token;
        }
        public String getToken() {
            return token;
        }
    }
}
