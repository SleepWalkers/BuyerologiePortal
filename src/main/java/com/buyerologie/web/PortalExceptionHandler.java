package com.buyerologie.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.buyerologie.common.vo.JsonVO;
import com.buyerologie.core.spring.mvc.ExceptionHandler;

@Component("ExceptionHandler")
public class PortalExceptionHandler implements ExceptionHandler {

    private static final Logger logger = Logger.getLogger(PortalExceptionHandler.class);

    /*
     (non-Javadoc)
     * @see com.yunjee.mvc.spring.ExceptionHandler#resolveSyncException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Exception)
     */
    @Override
    public ModelAndView resolveSyncException(HttpServletRequest request,
                                             HttpServletResponse response, Exception exception) {

        logger.error("", exception);
        ModelAndView modelAndView = new ModelAndView();

        if (exception instanceof RuntimeException) {
            //无权限用户判断
            //            if (exception instanceof AccessDeniedException) {
            //                String redirectURL = HttpServletUtil.getEncodedHttpRequestURL(request);
            //
            //                modelAndView.setViewName("redirect:/login?redirectURL=" + redirectURL);
            //
            //                return modelAndView;
            //                //密码错误用户判断 或 用户名不存在
            //            } else if (exception instanceof UserNotFoundException
            //                       || exception instanceof UserPasswordErrorException) {
            //                String redirectURL = request.getParameter("redirectURL");
            //
            //                modelAndView.setViewName("redirect:/login?redirectURL=" + redirectURL);
            //
            //                return modelAndView;
            //                //其他Runtime异常处理
            //            } else {
            //                logError(exception);
            //                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            //
            //                modelAndView.setViewName("/error/50x");
            //
            //                return modelAndView;
            //            }
            //非Runtime异常默认404页面处理
        } else {

            //            if (exception instanceof BizException) {
            //                logError(exception);
            //                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            //                modelAndView.setViewName("/error/50x");
            //                modelAndView.addObject("msg", exception.getMessage());
            //                return modelAndView;
            //            }
            //            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        	modelAndView.setViewName("/error/404");

            return modelAndView;
        }
        return modelAndView;
    }

    /*
     (non-Javadoc)
     * @see com.yunjee.mvc.spring.ExceptionHandler#resolveAsyncException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Exception)
     */
    @Override
    public String resolveAsyncException(HttpServletRequest request, HttpServletResponse response,
                                        Exception exception) {
        logger.error("", exception);

        JsonVO jsonVO = new JsonVO();
        jsonVO.setIsSuccess(false);
        jsonVO.setMsg(exception.getMessage());
        return jsonVO.toString();
        //
        //        if (exception instanceof AccessDeniedException) {
        //            Map<String, Object> jsonMap = new HashMap<String, Object>();
        //            StringBuffer redirectURL = new StringBuffer();
        //            //获取登录跳转URL
        //            String scheme = request.getScheme();
        //            String serverName = request.getServerName();
        //            int serverPort = request.getServerPort();
        //
        //            String servletPath = request.getServletPath();
        //            String service = scheme + "://" + serverName;
        //
        //            if (serverPort != 80) {
        //                service = service + ":" + serverPort;
        //            }
        //            try {
        //                redirectURL.append(SSOUtil.LOGIN_SERVER_NAME).append("?service=")
        //                    .append(URLEncoder.encode(service, "utf8")).append("&loginRedirectUri=")
        //                    .append(URLEncoder.encode(servletPath, "utf8"));
        //            } catch (UnsupportedEncodingException e) {
        //                logger.error("", e);
        //            }
        //            jsonMap.put("isSuccess", 0);
        //            jsonMap.put("isRedirect", 1);
        //            jsonMap.put("redirectURL", redirectURL.toString());
        //            return new Gson().toJson(jsonMap);
        //        } else {

    }

}
