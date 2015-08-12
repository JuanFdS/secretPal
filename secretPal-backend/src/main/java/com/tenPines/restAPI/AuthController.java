package com.tenPines.restAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimbusds.jose.JOSEException;
import com.tenPines.application.SecretPalSystem;
import com.tenPines.auth.GoogleAuth;
import com.tenPines.configuration.AdminProperties;
import com.tenPines.model.User;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
        try{
            Worker aUser = system.retrieveWorkerByEmail(workerEmail);
            Token token = AuthUtils.createToken(request.getRemoteHost(), aUser.geteMail());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }



    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @ResponseBody
    public Worker getAdmin() throws IOException {
        return system.retrieveWorkerByEmail(AdminProperties.getAdminEmail());
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    @ResponseBody
    public void setAdmin(@RequestHeader(value = "Authorization") String header,
                         @RequestBody Worker newAdmin) throws ParseException, JOSEException, IOException {
        User user = new User(system.retrieveWorkerByEmail(AuthUtils.tokenSubject(header)));

        if( user.isAdmin() ){
            AdminProperties.setAdmin(newAdmin);
        } else {
            throw new RuntimeException("This user is not an admin");
        }
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ResponseBody
    public User retrieveLogedWorker(@RequestHeader(value = "Authorization") String header) throws ParseException, JOSEException {
        return new User(system.retrieveWorkerByEmail(AuthUtils.tokenSubject(header)));
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
