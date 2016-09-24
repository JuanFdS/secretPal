package com.tenPines.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tenPines.restAPI.AuthController;

import java.text.ParseException;
import java.util.Calendar;

public class AuthUtils {

    public static final String TOKEN_SECRET = "aliceinwonderlandisthishorriblehash";

    public static AuthController.Token createToken(String host, String subject) throws JOSEException {
        JWTClaimsSet claim = new JWTClaimsSet();
        claim.setSubject(subject);
        claim.setIssuer(host);

        Calendar calendar = Calendar.getInstance(); // starts with today's date and time
        claim.setIssueTime(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, 2);  // advances day by 2
        claim.setExpirationTime(calendar.getTime());

        JWSSigner signer = new MACSigner(TOKEN_SECRET);
        SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claim);
        jwt.sign(signer);

        return new AuthController.Token(jwt.serialize());
    }

    public static String tokenSubject(String authHeader) throws ParseException, JOSEException {
        return decodeToken(authHeader).getSubject();
    }

    public static ReadOnlyJWTClaimsSet decodeToken(String authHeader) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(getSerializedToken(authHeader));
        if (signedJWT.verify(new MACVerifier(TOKEN_SECRET))) {
            return signedJWT.getJWTClaimsSet();
        } else {
            throw new JOSEException("Signature verification failed");
        }
    }

    public static String getSerializedToken(String authHeader) {
        return authHeader.split(" ")[1];
    }
}
