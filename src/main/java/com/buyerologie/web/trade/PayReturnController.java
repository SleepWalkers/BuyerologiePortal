package com.buyerologie.web.trade;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buyerologie.trade.TradeService;
import com.buyerologie.trade.pay.exception.PayException;

@Controller
public class PayReturnController {

    @Resource
    private TradeService        tradeService;

    private static final Logger logger = Logger.getLogger(PayReturnController.class);

    @RequestMapping(value = "/pay_return", method = { RequestMethod.GET, RequestMethod.POST })
    public String payReturn(HttpServletRequest request) throws PayException {

        tradeService.payReturn(request);
        return "redirect:/trade/paysuccess";
    }

    @ResponseBody
    @RequestMapping(value = "/pay_notify", method = { RequestMethod.GET, RequestMethod.POST })
    public String payNotify(HttpServletRequest request) throws PayException {
        tradeService.payReturn(request);
        return "success";
    }
}
