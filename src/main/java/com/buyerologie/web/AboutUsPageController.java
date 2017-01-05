package com.buyerologie.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AboutUsPageController {

    @RequestMapping(value = "/aboutus/aboutus.html", method = RequestMethod.GET)
    public String aboutUs(Model model) {
        model.addAttribute("pageName", "aboutus");
        return "page/aboutus";
    }
}
