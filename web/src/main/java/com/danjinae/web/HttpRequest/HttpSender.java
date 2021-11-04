package com.danjinae.web.HttpRequest;

import javax.servlet.http.HttpServletResponse;

import com.danjinae.web.HttpRequest.Response.MyHttpResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Repository
public class HttpSender {
    RestTemplate restTemplate = new RestTemplate();

    public MyHttpResponse defHttpRequest(String url, Object datas, HttpMethod method)
    {
        HttpHeaders header = new HttpHeaders();

        try {
            HttpEntity<Object> request = new HttpEntity<Object>(datas, header);
            HttpEntity<Object> response = restTemplate.exchange(url, method, request, Object.class);

            return new MyHttpResponse(true, response.getBody().toString(), response.getBody(), 0);
        } 
        catch (HttpClientErrorException eh) {
            if (eh.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return new MyHttpResponse(false, "로그인 해주세요.", 408);
            }
            else
                return new MyHttpResponse(false, eh.getMessage(), 406);
        }
        catch (Exception e) {
            return new MyHttpResponse(false, e.getMessage(), 406);
        }
    }
}
