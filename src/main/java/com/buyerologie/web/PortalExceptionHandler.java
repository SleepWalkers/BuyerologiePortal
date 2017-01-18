package com.buyerologie.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.buyerologie.common.vo.JsonVO;
import com.buyerologie.core.spring.mvc.ExceptionHandler;
import com.buyerologie.trade.exception.TradeException;
import com.buyerologie.vip.exception.VipExpireException;

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

        ModelAndView modelAndView = new ModelAndView();
        logger.error("", exception);
        try {
            if (exception instanceof RuntimeException) {
            } else {
                if (exception instanceof VipExpireException) {

                    response.sendRedirect("/confirm/order.html");

                } else if (exception instanceof AccessDeniedException) {
                    response.sendRedirect("/");
                } else if (exception instanceof TradeException) {
                    response.sendRedirect("/trade/payfailed?error=" + exception.getMessage());
                } else {
                    modelAndView.setViewName("/error/404");
                }
            }
        } catch (IOException e) {
            logger.error("", e);
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
    }

}
