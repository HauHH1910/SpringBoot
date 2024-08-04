package com.hauhh.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseError {

    private int code;

    private String message;
}
