package com.buyerologie.web.course;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buyerologie.cms.service.CmsService;

@Controller
public class CoursePageController {

    @Resource
    private CmsService          cmsService;

    private static final String PAGE_NAME = "尚课页";

    @RequestMapping(value = "/course/course.html", method = RequestMethod.GET)
    public String test(Model model) {
        model.addAttribute("page", cmsService.getPageVO(PAGE_NAME));
        model.addAttribute("pageName", "course");
        return "page/course/course";
    }
}