package com.buyerologie.web.trade;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buyerologie.enums.PayType;
import com.buyerologie.trade.TradeService;
import com.buyerologie.trade.exception.TradeException;
import com.buyerologie.user.UserService;
import com.buyerologie.user.exception.UserException;
import com.buyerologie.vip.exception.VipException;

@Controller
public class PayReturnController {

    @Resource
    private UserService  userService;

    @Resource
    private TradeService tradeService;

    @RequestMapping(value = "/alipay/pay_return", method = { RequestMethod.GET,
                                                             RequestMethod.POST })
    public String payReturn(HttpServletRequest request) throws TradeException, UserException,
                                                        VipException {
        tradeService.payReturn(request, PayType.ALIPAY);
        return "redirect:/trade/paysuccess";
    }

    @ResponseBody
    @RequestMapping(value = "/alipay/pay_notify", method = { RequestMethod.GET,
                                                             RequestMethod.POST })
    public String payNotify(HttpServletRequest request) throws TradeException, UserException,
                                                        VipException {
        tradeService.payReturn(request, PayType.ALIPAY);
        return "success";
    }
}
