package com.buyerologie.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomepageContoller {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String userDetail(Model model) {
        model.addAttribute("pageName", "homepage");
        return "page/homepage";
    }
}
