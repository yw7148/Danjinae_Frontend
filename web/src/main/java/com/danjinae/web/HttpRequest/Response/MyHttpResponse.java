package com.danjinae.web.HttpRequest.Response;

import lombok.Data;

@Data
public class MyHttpResponse {
    private Boolean response;
    private String message;
    private Object data;
    private int errorcode;

    public MyHttpResponse(Boolean response, String message, int errorcode) {
        this.response = response;
        this.message = message;
        this.errorcode = errorcode;
    }

    public MyHttpResponse(Boolean response, String message, Object data, int errorcode) {
        this.response = response;
        this.message = message;
        this.data = data;
        this.errorcode = errorcode;
    }

    @Override
    public String toString()
    {
        return "res: " + this.response + "//mes: " + this.message + "//data: " + this.data.toString();
    }
}

