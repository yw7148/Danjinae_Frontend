package com.danjinae.web.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danjinae.web.HttpRequest.Response.MyHttpResponse;
import com.danjinae.web.HttpRequest.loginDTO.JwtToken;
import com.danjinae.web.HttpRequest.loginDTO.RequestLoginUser;
import com.danjinae.web.HttpRequest.loginService.CookieUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpSender {
    @Autowired
    CookieUtil cookieUtil;

    RestTemplate restTemplate = new RestTemplate();

    public MyHttpResponse defHttpRequest(String url, Object datas , HttpServletRequest HttpServletRequest, HttpServletResponse HttpServletResponse, HttpMethod method)
    {
        String accessToken;
        String refreshToken;
        try{
            accessToken = cookieUtil.getCookie(HttpServletRequest, JwtToken.ACCESS_TOKEN_NAME).getValue();
            refreshToken = cookieUtil.getCookie(HttpServletRequest, JwtToken.REFRESH_TOKEN_NAME).getValue();
        }catch(Exception e)
        {
            return new MyHttpResponse(false, "로그인 해주세요.", 408);
        }

        HttpHeaders header = new HttpHeaders();
        header.add(JwtToken.ACCESS_TOKEN_NAME, accessToken);

        try {
            HttpEntity<Object> request = new HttpEntity<Object>(datas, header);
            HttpEntity<MyHttpResponse> response = restTemplate.exchange(url, method, request,
                    MyHttpResponse.class);

            return response.getBody();
        } 
        catch (HttpClientErrorException eh) {
        	
            if (eh.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                try{
                    header.add(JwtToken.REFRESH_TOKEN_NAME, refreshToken);
                    HttpEntity<Object> request = new HttpEntity<Object>(datas, header);

                    HttpEntity<MyHttpResponse> response = restTemplate.exchange(url, method, request,
                            MyHttpResponse.class);
                    var isToken = response.getHeaders().get(JwtToken.ACCESS_TOKEN_NAME);

                    if (isToken != null)
                        HttpServletResponse
                                .addCookie(cookieUtil.createCookie(JwtToken.ACCESS_TOKEN_NAME, isToken.get(0), false));

                    return response.getBody();
                } catch (HttpClientErrorException ehc) {
                    if (ehc.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                        return new MyHttpResponse(false, "로그인 해주세요.", 408);
                    }
                    else {
                        return new MyHttpResponse(false, ehc.getMessage(), 406);
                    }
                }
            }
            else
                return new MyHttpResponse(false, eh.getMessage(), 406);
        }
        catch (Exception e) {
            return new MyHttpResponse(false, e.getMessage(), 406);
        }
    }

    public MyHttpResponse LoginGetJwt(String username, String password) {
        try {
            RequestLoginUser reqUser = new RequestLoginUser(username, password);
            HttpHeaders reqheader = new HttpHeaders();
            HttpEntity<RequestLoginUser> request = new HttpEntity<RequestLoginUser>(reqUser, reqheader);

            HttpEntity<MyHttpResponse> response = restTemplate.exchange("http://localhost:8081/user/login",
                    HttpMethod.POST, request, MyHttpResponse.class);

            MyHttpResponse resbody = response.getBody();
            if (resbody.getResponse()) {
                JwtToken myToken;
                myToken = new JwtToken(response.getHeaders().get(JwtToken.ACCESS_TOKEN_NAME).get(0),
                        response.getHeaders().get(JwtToken.REFRESH_TOKEN_NAME).get(0));

                resbody.setData(myToken);
            }
            
            return resbody;
        } catch (Exception e) 
        {
            return new MyHttpResponse(false, e.getMessage(), 406);
        }
    }

    public MyHttpResponse CheckIsLogin(String accessToken, String refreshToken, HttpServletResponse HttpServletResponse) {
        HttpHeaders header = new HttpHeaders();
        header.add(JwtToken.ACCESS_TOKEN_NAME, accessToken);

        try {
            HttpEntity<Object> request = new HttpEntity<Object>(null, header);
            var result = restTemplate.exchange("http://localhost:8081/user/validate", HttpMethod.POST, request,
                    MyHttpResponse.class).getBody();

            return result;
        } 
        catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                try {
                    header.add(JwtToken.REFRESH_TOKEN_NAME, refreshToken);
                    HttpEntity<Object> request = new HttpEntity<Object>(null, header);

                    HttpEntity<MyHttpResponse> response = restTemplate.exchange("http://localhost:8081/user/validate",
                            HttpMethod.POST, request, MyHttpResponse.class);
                    var isToken = response.getHeaders().get(JwtToken.ACCESS_TOKEN_NAME);

                    if (isToken != null)
                        HttpServletResponse
                                .addCookie(cookieUtil.createCookie(JwtToken.ACCESS_TOKEN_NAME, isToken.get(0), false));

                    return response.getBody();
                } catch (HttpClientErrorException ex) {
                    if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                        return new MyHttpResponse(false, "로그인 중이 아닙니다.", 201);
                    } else {
                        return new MyHttpResponse(false, ex.getMessage(), 406);
                    }
                }
            } else
                return new MyHttpResponse(false, e.getMessage(), 406);
        }
        catch(Exception e)
        {
            return new MyHttpResponse(false, e.getMessage(), 406);
        }
    }
    
    public MyHttpResponse LogOutGetJwt(String username, String password) {
        try {
            RequestLoginUser reqUser = new RequestLoginUser(username, password);
            HttpHeaders reqheader = new HttpHeaders();
            HttpEntity<RequestLoginUser> request = new HttpEntity<RequestLoginUser>(reqUser, reqheader);

            HttpEntity<MyHttpResponse> response = restTemplate.exchange("http://localhost:8081/user/logout",
                    HttpMethod.POST, request, MyHttpResponse.class);
            MyHttpResponse resbody = response.getBody();

            return resbody;

        } catch (Exception e) 
        {
            return new MyHttpResponse(false, e.getMessage(), 406);
        }
    }

}
