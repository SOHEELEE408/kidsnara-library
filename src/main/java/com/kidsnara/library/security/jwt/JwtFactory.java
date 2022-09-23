package com.kidsnara.library.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kidsnara.library.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JWT 토큰 생성
 */
@Component
public class JwtFactory {

    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    public String generateToken(CustomUserDetails userDetails){
        String token = null;

        try {
            Set<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(r -> r.getAuthority())
                    .collect(Collectors.toSet());
            String role = roles.iterator().next();

            token = JWT.create()
                    .withIssuer("kidsnara")
                    .withClaim("USER_EMAIL", userDetails.getUsername())
                    .withClaim("USER_ROLE", role)
                    .withExpiresAt(new Date(System.currentTimeMillis() + (1000*60*60*24)))
                    .sign(generateAlgorithm());

        } catch (Exception e){
            log.error(e.getMessage());
        }
        return token;
    }

    private Algorithm generateAlgorithm() throws UnsupportedEncodingException{
        String signingKey = "kidsnara";
        return Algorithm.HMAC256(signingKey);
    }
}
