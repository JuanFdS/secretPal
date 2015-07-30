package com.tenPines.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tenPines.restAPI.AuthController;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;


public class AuthUtils {

    public static AuthController.Token createToken(String host, long sub) throws JOSEException {
        Calendar calendar = Calendar.getInstance(); // starts with today's date and time
        calendar.add(Calendar.DAY_OF_YEAR, 2);  // advances day by 2

        JWTClaimsSet claim = new JWTClaimsSet();
        claim.setSubject(Long.toString(sub));
        claim.setIssuer(host);
        claim.setIssueTime(new Date());
        claim.setExpirationTime(calendar.getTime());

        JWSSigner signer = new MACSigner("aliceinwonderlandisthishorriblehash");
        SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claim);
        jwt.sign(signer);

        return new AuthController.Token(jwt.serialize());
    }
}
