package com.buyerologie.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.buyerologie.core.spring.mvc.interceptor.DefaultControllerHandler;
import com.buyerologie.security.SecurityService;

@Component("ControllerHandler")
public class PortalControllerHandler extends DefaultControllerHandler {

    @Resource
    private SecurityService securityService;

    @Override
    public void postSyncHandle(HttpServletRequest request, HttpServletResponse response,
                               ModelAndView modelAndView) throws Exception {

        modelAndView.addObject("requestUri", request.getRequestURI());

        modelAndView.addObject("gvUser", securityService.getCurrentUser());
    }

    @Override
    public void postAsyncHandle(HttpServletRequest request, HttpServletResponse response,
                                ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void preHandle(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
    }

}
