package com.buyerologie.web.source;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SourcePageController {

    @RequestMapping(value = "/source/source.html", method = RequestMethod.GET)
    public String aboutUs(Model model) {
        model.addAttribute("pageName", "source");
        return "page/source/source";
    }
}
