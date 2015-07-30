package com.tenPines.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tenPines.restAPI.AuthController;

import java.util.Date;


public class AuthUtils {

    public static AuthController.Token createToken(String host, long sub) throws JOSEException {
        JWTClaimsSet claim = new JWTClaimsSet();
        claim.setSubject(Long.toString(sub));
        claim.setIssuer(host);
        claim.setIssueTime(new Date());
        //claim.setExpirationTime(LocalDate.now().plusDays(14).toDate());

        JWSSigner signer = new MACSigner("aliceinwonderlandisthishorriblehash");
        SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claim);
        jwt.sign(signer);

        return new AuthController.Token(jwt.serialize());
    }
}
