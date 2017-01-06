package com.buyerologie.web.trade;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buyerologie.trade.ProductService;

@Controller
public class ConfirmOrderPageControler {

    @Resource
    private ProductService productService;

    @RequestMapping(value = "/confirm/order.html", method = RequestMethod.GET)
    public String aboutUs(Model model) {
        model.addAttribute("products", productService.getAllProducts());

        model.addAttribute("pageName", "confirmorder");
        return "page/trade/confirmOrder";
    }
}
