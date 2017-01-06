package com.buyerologie.web.comment;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buyerologie.common.vo.JsonVO;
import com.buyerologie.security.SecurityService;
import com.buyerologie.topic.CommentService;
import com.buyerologie.topic.exception.TopicException;
import com.buyerologie.topic.vo.ListComment;
import com.buyerologie.utils.PageUtil;

@Controller
public class CommentApiController {

    @Resource
    private CommentService  commentService;

    @Resource
    private SecurityService securityService;

    @ResponseBody
    @RequestMapping(value = "/api/comment/publish", method = RequestMethod.POST)
    public String publishNews(@RequestParam(required = true) int newsId,
                              @RequestParam(required = true) String comment,
                              HttpServletRequest request) throws TopicException {

        commentService.publicComment(newsId, securityService.getCurrentUser().getId(), comment);
        return new JsonVO(true).toString();
    }

    @RequestMapping(value = "/api/comment/getComment", method = RequestMethod.GET)
    public String getComment(@RequestParam(required = true) int topicId,
                             @RequestParam(required = true) int page,
                             @RequestParam(required = true) int pageSize,
                             @RequestParam(required = false) String getCommentsUrl, Model model)
                                                                                                throws TopicException {

        List<ListComment> listComments = commentService.getListComments(topicId, page, pageSize);
        model.addAttribute("comments", listComments);
        model.addAttribute("getCommentsUrl", getCommentsUrl);
        model.addAttribute("page", page);

        model.addAttribute("topicId", topicId);
        int totalNum = commentService.countComment(topicId);
        model.addAttribute("totalNum", totalNum);
        model.addAttribute("totalPage", PageUtil.calcPageTotal(totalNum, pageSize));
        return "page/brick/commentBrick";
    }
}
