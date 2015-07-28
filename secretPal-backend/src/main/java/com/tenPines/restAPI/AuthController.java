package com.tenPines.restAPI;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.text.translate.EntityArrays;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("/auth")
public class AuthController {


    public static final String CLIENT_ID_KEY = "client_id", REDIRECT_URI_KEY = "redirect_uri",
            CLIENT_SECRET = "client_secret", CODE_KEY = "code", GRANT_TYPE_KEY = "grant_type",
            AUTH_CODE = "authorization_code";

    @RequestMapping(value = "/google", method = RequestMethod.POST)
    @ResponseBody
    public String loginGoogle(@RequestBody Payload payload) throws IOException {
        List<BasicNameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("code", payload.getCode()));
        pairs.add(new BasicNameValuePair("client_id", payload.getClientId()));
        pairs.add(new BasicNameValuePair("client_secret", "e9P_yLxXR7yoJR5PuwOytS4-"));
        pairs.add(new BasicNameValuePair("redirect_uri", payload.getRedirectUri()));
        pairs.add(new BasicNameValuePair("grant_type", "authorization_code"));

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost("https://accounts.google.com/o/oauth2/token");
        post.setEntity(new UrlEncodedFormEntity(pairs));
        HttpResponse response = httpClient.execute(post);
        JSONObject jsonResponse = new JSONObject(EntityUtils.toString(response.getEntity()));
        String access_token = (String) jsonResponse.get("access_token");

        HttpGet get = new HttpGet("https://www.googleapis.com/plus/v1/people/me/openIdConnect");
        get.addHeader("Authorization",  String.format("Bearer %s", access_token));
        HttpResponse userResponse = httpClient.execute(get);
        JSONObject userInfoResponse = new JSONObject(EntityUtils.toString(userResponse.getEntity()));
        return userInfoResponse.toString();
    }


    /*
   * Inner classes for entity wrappers
   */
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

}
