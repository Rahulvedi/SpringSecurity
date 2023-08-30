package com.springbasics.security.exception.CustomExceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomExceptionSchema {
    private Boolean success;
    private int code;
    private String message;
}
