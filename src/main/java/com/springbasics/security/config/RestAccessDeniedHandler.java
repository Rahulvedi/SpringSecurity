package com.springbasics.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbasics.security.exception.CustomExceptions.CustomExceptionSchema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);


        final CustomExceptionSchema exceptionSchema=new CustomExceptionSchema();
        exceptionSchema.setCode(HttpServletResponse.SC_FORBIDDEN);
        exceptionSchema.setSuccess(false);
        exceptionSchema.setMessage(accessDeniedException.getMessage()+". You do not have sufficient privileges to access this resource." );

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), exceptionSchema);

    }
}
