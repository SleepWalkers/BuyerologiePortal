package com.buyerologie.web.trade;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.buyerologie.trade.TradeService;
import com.buyerologie.trade.exception.TradeException;
import com.buyerologie.trade.model.TradeOrder;
import com.buyerologie.trade.pay.exception.PayException;
import com.buyerologie.user.exception.UserException;

@Controller
public class PayOrderController {

    @Resource
    private TradeService tradeService;

    @RequestMapping(value = "/trade/payorder", method = { RequestMethod.GET, RequestMethod.POST })
    public String tradePay(Model model, @RequestParam long orderNumber) throws UserException,
                                                                        TradeException,
                                                                        PayException {

        TradeOrder tradeOrder = tradeService.get(orderNumber);

        model.addAttribute("pageName", "payorder");
        model.addAttribute("tradeOrder", tradeOrder);

        return "page/trade/payOrder";
    }
}
