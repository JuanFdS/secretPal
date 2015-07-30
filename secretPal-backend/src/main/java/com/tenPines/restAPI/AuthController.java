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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;
import java.util.Properties;

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
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ResponseBody
    public Worker retrieveLogedWorker(@RequestHeader(value = "Authorization") String header) throws ParseException, JOSEException {
        return system.retrieveWorkerByEmail(AuthUtils.tokenSubject(header)).orElseThrow(
                () -> new RuntimeException("The user does not exist")
        );
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
