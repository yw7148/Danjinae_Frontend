package com.danjinae.web.home.Controller;

import com.danjinae.web.HttpRequest.HttpSender;
import com.danjinae.web.HttpRequest.Response.MyHttpResponse;
import com.danjinae.web.HttpRequest.loginDTO.JwtToken;
import com.danjinae.web.HttpRequest.loginService.CookieUtil;
import com.danjinae.web.home.dto.LoginRequest;
import com.danjinae.web.SETTING;
import com.danjinae.web.notice.RequestDTO.Notice;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

  @Autowired
  CookieUtil cookieUtil;

  @Autowired
  HttpSender httpSender;

  @GetMapping(path = "/err/report")
  public String ErrorPage(
    Model model,
    @RequestParam(value = "message") String message
  ) {
    model.addAttribute("message", message);
    model.addAttribute("redirectUri", "javascript:history.back();");
    return "alert";
  }

  @GetMapping(path = "/changeApt")
  public String ChangeAptId(Model model) {
    if (SETTING.APT_ID == 1) {
      SETTING.APT_ID = 2;
      SETTING.MGR_ID = 2;
    } else if (SETTING.APT_ID == 2) {
      SETTING.APT_ID = 1;
      SETTING.MGR_ID = 1;
    }
    model.addAttribute("message", "매니저 및 아파트 ID: " + SETTING.APT_ID);
    model.addAttribute("redirectUri", "javascript:history.back();");
    return "alert";
  }

  @GetMapping(path = "/")
  public String Index(Model model) {
    return "index";
  }

  @GetMapping(path = "/login")
  public String LoginPage(
    HttpServletRequest request,
    HttpServletResponse response,
    Model model
  ) {
    try {
      String accessToken = cookieUtil
        .getCookie(request, JwtToken.ACCESS_TOKEN_NAME)
        .getValue();
      String refreshToken = cookieUtil
        .getCookie(request, JwtToken.REFRESH_TOKEN_NAME)
        .getValue();
      var result = httpSender.CheckIsLogin(accessToken, refreshToken, response);
      if (result.getResponse() && result.getErrorcode() != 809) {
        return "redirect:/";
      }
    } catch (NullPointerException e) {}

    return "login1";
  }

  @PostMapping(path = "/login/result")
	public String LoginToServer(HttpServletRequest request, LoginRequest bodydata, HttpServletResponse response,
			Model model) {
		try {
			String username = bodydata.getId();
			String password = bodydata.getPassword();
			MyHttpResponse result = httpSender.LoginGetJwt(username, password);
			if (result.getResponse()) {
				JwtToken myToken = (JwtToken) result.getData();
				Cookie accessToken = cookieUtil.createCookie(JwtToken.ACCESS_TOKEN_NAME, myToken.getAccessToken(),
						false);
				Cookie refreshToken = cookieUtil.createCookie(JwtToken.REFRESH_TOKEN_NAME, myToken.getRefreshToken(),
						false);

				response.addCookie(accessToken);
				response.addCookie(refreshToken);

				var fromUri = request.getSession().getAttribute("from");
				return "redirect:" + (fromUri != null ? (String) fromUri : "/");
			} else {
				throw new Exception(result.getMessage());
			}

		} catch (Exception e) {
			try {
        return "redirect:/err/report?message=" + URLDecoder.decode(e.getMessage(), "UTF-8" );
      } catch (UnsupportedEncodingException e1) {
        return "redirect:/err/report?message=알수없는오류";
      }
		}
	}

  @GetMapping(path = "/testcookie")
  public @ResponseBody Object TestCookie(HttpServletRequest request)
  {
    return request.getCookies();
  }

}
