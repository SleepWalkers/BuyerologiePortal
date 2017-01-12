package com.buyerologie.web.trade;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.buyerologie.enums.PayType;
import com.buyerologie.trade.TradeService;
import com.buyerologie.trade.exception.TradeException;
import com.buyerologie.trade.pay.exception.PayException;
import com.buyerologie.user.UserService;
import com.buyerologie.user.exception.UserException;
import com.buyerologie.user.model.User;

@Controller
public class TradeCheckController {

    @Resource
    private UserService  userService;

    @Resource
    private TradeService tradeService;

    @RequestMapping(value = "/trade/check", method = { RequestMethod.GET, RequestMethod.POST })
    public String tradeCheck(Model model, @RequestParam int productId, @RequestParam int payType)
                                                                                                 throws UserException,
                                                                                                 TradeException,
                                                                                                 PayException {

        User user = userService.getCurrentUser();
        tradeService.trade(user.getId(), PayType.get(payType), productId);
        return "page/trade/confirmOrder";
    }
}
