package com.example.backendservice.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Builder
public class ApiResponse implements Serializable {
    private int status;
    private String message;
    private Object data;
}
