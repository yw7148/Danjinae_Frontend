package com.danjinae.web.HttpRequest.loginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danjinae.web.HttpRequest.HttpSender;
import com.danjinae.web.HttpRequest.loginDTO.JwtToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;


public class LoginCheckIntercepter implements HandlerInterceptor{
    @Autowired
	HttpSender httpSender;
    
    @Autowired
    CookieUtil cookieUtil;


    private void saveDest(HttpServletRequest req) {

        String uri = req.getRequestURI();
        String query = req.getQueryString();

        if (query == null || query.equals("null")) {
            query = "";
        } else {
            query = "?" + query;
        }

        req.getSession().setAttribute("from", uri + query);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
			String accessToken = cookieUtil.getCookie(request, JwtToken.ACCESS_TOKEN_NAME).getValue();
			String refreshToken = cookieUtil.getCookie(request, JwtToken.REFRESH_TOKEN_NAME).getValue();
            var result = httpSender.CheckIsLogin(accessToken, refreshToken, response);

            if (result.getResponse())
                    return true;
            
        } catch (NullPointerException e) {
        }
        saveDest(request);
        response.sendRedirect("/login");
        return false;
    }

}
