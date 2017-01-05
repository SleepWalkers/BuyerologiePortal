package com.buyerologie.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buyerologie.video.VideoService;

@Controller
public class SyncVideoController {

    @Resource
    private VideoService videoService;

    @ResponseBody
    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    public String userDetail(Model model) {
        videoService.sync();
        return "success";
    }
}
