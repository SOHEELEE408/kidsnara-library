package com.kidsnara.library.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kidsnara.library.config.exceptionhandler.BaseErrorResult;
import com.kidsnara.library.config.exceptionhandler.BaseException;
import com.kidsnara.library.dto.user.AccountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtDecoder {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public AccountDto decodeJwt(String token){
        DecodedJWT decodedJWT = isValidToken(token)
                .orElseThrow(() -> new BaseException(BaseErrorResult.INVALID_JWT));

        String email = decodedJWT
                .getClaim("USER_EMAIL")
                .asString();

        String role = decodedJWT
                .getClaim("USER_ROLE")
                .asString();

        return AccountDto.builder()
                .email(email)
                .role(role)
                .build();
    }

    private Optional<DecodedJWT> isValidToken(String token){
        DecodedJWT jwt = null;

        try{
            Algorithm algorithm = Algorithm.HMAC256("kidsnara");
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .build();
            jwt = verifier.verify(token);
        } catch (Exception e){
            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }
}
