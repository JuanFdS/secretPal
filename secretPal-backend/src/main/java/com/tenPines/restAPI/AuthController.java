package com.tenPines.restAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimbusds.jose.JOSEException;
import com.tenPines.application.SecretPalSystem;
import com.tenPines.auth.GoogleAuth;
import com.tenPines.model.Worker;
import com.tenPines.utils.AuthUtils;
import com.tenPines.utils.Payload;
import com.tenPines.utils.PropertyParser;
import org.apache.http.impl.client.DefaultHttpClient;
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
        Properties googleAuthProperties = new PropertyParser("src/main/resources/gmailAPIAuth.properties");
        GoogleAuth googleAuth = new GoogleAuth(googleAuthProperties, new DefaultHttpClient());
        String access_token = googleAuth.authUserWithPayload(payload);

        return validateWorker(request, googleAuth.getAuthUserEmail(access_token));
    }

    private ResponseEntity<Token> validateWorker(HttpServletRequest request, String workerEmail) throws JOSEException {
        Optional<Worker> anUser = system.retrieveWorkerByEmail(workerEmail);
        if (anUser.isPresent()) {
            Token token = AuthUtils.createToken(request.getRemoteHost(), anUser.get().geteMail());
            return new ResponseEntity<Token>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<Token>(HttpStatus.NOT_ACCEPTABLE);
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
