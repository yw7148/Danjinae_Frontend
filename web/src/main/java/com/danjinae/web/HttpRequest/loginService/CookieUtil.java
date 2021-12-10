package com.danjinae.web.HttpRequest.loginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.danjinae.web.HttpRequest.loginDTO.JwtToken;

import org.springframework.stereotype.Service;

@Service
public class CookieUtil {
    
    public Cookie createCookie(String cookieName, String value, boolean permanent){
        Cookie token = new Cookie(cookieName,value);
        token.setHttpOnly(true);
        if(!permanent)
            token.setMaxAge((int)JwtToken.TOKEN_LIFE_SECOND);
        token.setPath("/");
        return token;
    }

    public Cookie getCookie(HttpServletRequest req, String cookieName){
        final Cookie[] cookies = req.getCookies();
        if(cookies==null) return null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }

}

