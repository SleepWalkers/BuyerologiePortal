package com.buyerologie.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.buyerologie.cms.service.CmsService;
import com.buyerologie.core.spring.mvc.interceptor.DefaultControllerHandler;
import com.buyerologie.domain.Domain;
import com.buyerologie.security.SecurityService;

@Component("ControllerHandler")
public class PortalControllerHandler extends DefaultControllerHandler {

    @Resource
    private CmsService          cmsService;

    @Resource
    private SecurityService     securityService;

    private static final String PAGE_NAME = "全局设置";

    private static final String DOMAIN    = Domain.getInstance().getDomain();

    @Override
    public void postSyncHandle(HttpServletRequest request, HttpServletResponse response,
                               ModelAndView modelAndView) throws Exception {

        modelAndView.addObject("globalPage", cmsService.getPageVO(PAGE_NAME));

        modelAndView.addObject("requestUri", request.getRequestURI());

        modelAndView.addObject("domain", DOMAIN);

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
