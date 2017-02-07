package com.buyerologie.web.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buyerologie.common.vo.JsonVO;
import com.buyerologie.security.SecurityService;
import com.buyerologie.user.UserActionService;

@Controller
public class UserActionApiController {

    @Resource
    private SecurityService   securityService;

    @Resource
    private UserActionService userActionService;

    @ResponseBody
    @RequestMapping(value = "/api/topic/collect", method = { RequestMethod.POST, RequestMethod.GET })
    public String collect(@RequestParam(required = true) int topicId, HttpServletRequest request) {
        if (userActionService.isCollected(securityService.getCurrentUser().getId(), topicId)) {
            userActionService.uncollect(securityService.getCurrentUser().getId(), topicId);
        } else {
            userActionService.collect(securityService.getCurrentUser().getId(), topicId);
        }
        return new JsonVO(true).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/api/course/uncollect", method = { RequestMethod.POST,
            RequestMethod.GET })
    public String uncollect(@RequestParam(required = true) String videoId,
                            HttpServletRequest request) {
        userActionService.uncollect(securityService.getCurrentUser().getId(), videoId);
        return new JsonVO(true).toString();
    }
}
