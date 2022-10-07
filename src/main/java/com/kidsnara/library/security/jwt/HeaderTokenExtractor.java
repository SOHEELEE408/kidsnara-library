package com.kidsnara.library.security.jwt;

import com.kidsnara.library.config.exceptionhandler.BaseErrorResult;
import com.kidsnara.library.config.exceptionhandler.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class HeaderTokenExtractor {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public String extract(String header, HttpServletRequest request){
        if(header == null || header.equals("") || header.length() < 1){
            log.info("error request : "+ request.getRequestURI());
            throw new BaseException(BaseErrorResult.EMPTY_JWT);
        }

        return header;
    }

}
