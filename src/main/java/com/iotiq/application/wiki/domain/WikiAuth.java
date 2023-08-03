package com.iotiq.application.wiki.domain;

import com.iotiq.application.wiki.exception.WikiAuthFailException;
import com.iotiq.application.wiki.messages.WikiAuthResponse;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import lombok.Builder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

@Getter
@Builder
public class WikiAuth {
    private static final Logger logger = LoggerFactory.getLogger(WikiAuth.class);

    private String authToken;
    private Date accessTokenValidUntil;

    public static WikiAuth from(WikiAuthResponse authResponse) {
        String authToken = authResponse.jwt();
        if (authToken == null) {
            throw new WikiAuthFailException();
        }

        Date accessExpirationTime = getExpirationTime(authToken);
        return WikiAuth.builder()
                .authToken(authToken)
                .accessTokenValidUntil(accessExpirationTime)
                .build();
    }

    private static Date getExpirationTime(String token) {
        try {
            JWT parse = JWTParser.parse(token);
            return parse.getJWTClaimsSet().getExpirationTime();
        } catch (ParseException e) {
            logger.error("Could not parse expiration date from token");
        }
        return Date.from(Instant.now());
    }

    public boolean isAccessTokenExpired() {
        return accessTokenValidUntil.before(Date.from(Instant.now()));
    }
}
