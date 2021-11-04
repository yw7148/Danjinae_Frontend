package com.danjinae.web.HttpRequest.Response;

public class MyHttpResponse {
    private Boolean response;
    private String message;
    private Object data;
    private int errorcode;


    public MyHttpResponse()
    {
        
    }

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

    public Boolean getResponse() {
        return this.response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getErrorcode() {
        return this.errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    @Override
    public String toString()
    {
        return "res: " + this.response + "//mes: " + this.message + "//data: " + this.data.toString();
    }
}

