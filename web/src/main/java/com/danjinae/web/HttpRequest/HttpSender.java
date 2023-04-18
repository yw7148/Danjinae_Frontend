package com.danjinae.web.HttpRequest;

import java.net.http.HttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.danjinae.web.HttpRequest.Response.MyHttpResponse;
import com.danjinae.web.HttpRequest.loginDTO.JwtToken;
import com.danjinae.web.HttpRequest.loginDTO.RequestLoginUser;
import com.danjinae.web.HttpRequest.loginService.CookieUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${danjinae.backend.url}")
    String backendUrl;

    RestTemplate restTemplate = new RestTemplate();

    public MyHttpResponse httpReqToDanjinaeBackend(String path, Object datas , HttpServletRequest HttpServletRequest, HttpServletResponse HttpServletResponse, HttpMethod method)
    {
        if(!path.startsWith("/")) path = "/" + path;
        String url = backendUrl + path;

        String accessToken = "";
        String refreshToken = "";
        try{
            accessToken = cookieUtil.getCookie(HttpServletRequest, JwtToken.ACCESS_TOKEN_NAME).getValue();
            refreshToken = cookieUtil.getCookie(HttpServletRequest, JwtToken.REFRESH_TOKEN_NAME).getValue();
        }catch(NullPointerException e) {
        }

        HttpHeaders header = new HttpHeaders();
        header.add(JwtToken.ACCESS_TOKEN_NAME, accessToken);

        try {
            HttpEntity<Object> request = new HttpEntity<Object>(datas, header);
            HttpEntity<Object> response = restTemplate.exchange(url, method, request,
                    Object.class);

            return new MyHttpResponse(true, "성공", response.getBody(), 200);
        } 
        catch (HttpClientErrorException eh) {
        	
            if (eh.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                try{
                    header.add(JwtToken.REFRESH_TOKEN_NAME, refreshToken);
                    HttpEntity<Object> request = new HttpEntity<Object>(datas, header);

                    HttpEntity<Object> response = restTemplate.exchange(url, method, request,
                    Object.class);

            
                    var isToken = response.getHeaders().get(JwtToken.ACCESS_TOKEN_NAME);

                    if (isToken != null)
                        HttpServletResponse
                                .addCookie(cookieUtil.createCookie(JwtToken.ACCESS_TOKEN_NAME, isToken.get(0), false));

                    return new MyHttpResponse(true, "성공", response.getBody(), 200);
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

            HttpEntity<Boolean> response = restTemplate.exchange("http://101.101.219.69:8080/user/login",
                    HttpMethod.POST, request, Boolean.class);

            Boolean resbody = response.getBody();
            if (resbody) {
                JwtToken myToken;
                myToken = new JwtToken(response.getHeaders().get(JwtToken.ACCESS_TOKEN_NAME).get(0),
                        response.getHeaders().get(JwtToken.REFRESH_TOKEN_NAME).get(0));

                return new MyHttpResponse(true, "성공", myToken, 400);
            }
            else 
            {
                return new MyHttpResponse(false, "실패하였습니다", 406);
            }
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
            var result = restTemplate.exchange("http://101.101.219.69:8080/user/validate", HttpMethod.GET, request,
                    Boolean.class).getBody();

            return new MyHttpResponse(result, "성공", 200);
        } 
        catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                try {
                    header.add(JwtToken.REFRESH_TOKEN_NAME, refreshToken);
                    HttpEntity<Object> request = new HttpEntity<Object>(null, header);

                    HttpEntity<Boolean> response = restTemplate.exchange("http://101.101.219.69:8080/user/validate",
                            HttpMethod.GET, request, Boolean.class);
                    var isToken = response.getHeaders().get(JwtToken.ACCESS_TOKEN_NAME);

                    if (isToken != null)
                        HttpServletResponse
                                .addCookie(cookieUtil.createCookie(JwtToken.ACCESS_TOKEN_NAME, isToken.get(0), false));

                    return new MyHttpResponse(response.getBody(), "성공", 200);
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

            HttpEntity<MyHttpResponse> response = restTemplate.exchange("http://101.101.219.69:8080/user/logout",
                    HttpMethod.POST, request, MyHttpResponse.class);
            MyHttpResponse resbody = response.getBody();

            return resbody;

        } catch (Exception e) 
        {
            return new MyHttpResponse(false, e.getMessage(), 406);
        }
    }

}
