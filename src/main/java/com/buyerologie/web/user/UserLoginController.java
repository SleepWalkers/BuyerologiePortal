package com.buyerologie.web.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buyerologie.common.vo.JsonVO;
import com.buyerologie.security.SecurityService;
import com.buyerologie.user.exception.UserException;

@Controller
public class UserLoginController {

    @Resource
    private SecurityService securityService;

    @ResponseBody
    @RequestMapping(value = "/api/user/login", method = RequestMethod.POST)
    public String userLogin(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(required = true) String phone,
                            @RequestParam(required = true) String password) throws UserException {

        securityService.login(request, response, phone, password);

        return new JsonVO(true).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/api/user/logout", method = RequestMethod.POST)
    public String logout(@RequestParam String redirectUrl, HttpServletRequest request,
                         HttpServletResponse response) throws UserException {

        securityService.logout(request, response);

        JsonVO jsonVO = new JsonVO(true);
        jsonVO.setIsRedirect(true);

        if (!redirectUrl.contains("/account/")) {
            jsonVO.setRedirectURL(redirectUrl);
        } else {
            jsonVO.setRedirectURL("/");
        }

        return jsonVO.toString();
    }
}
