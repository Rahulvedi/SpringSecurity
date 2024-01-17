package com.springbasics.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbasics.security.exception.CustomExceptions.CustomExceptionSchema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final CustomExceptionSchema exceptionSchema=new CustomExceptionSchema();
        exceptionSchema.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        exceptionSchema.setSuccess(false);
        exceptionSchema.setMessage("Access Denied. " + authException.getMessage());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), exceptionSchema);

    }
}
