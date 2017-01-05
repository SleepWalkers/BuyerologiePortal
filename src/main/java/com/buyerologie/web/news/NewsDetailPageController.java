package com.buyerologie.web.news;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buyerologie.common.exception.PageNotFoundException;
import com.buyerologie.security.SecurityService;
import com.buyerologie.security.UserAuthCredentials;
import com.buyerologie.topic.CommentService;
import com.buyerologie.topic.NewsService;
import com.buyerologie.topic.vo.DetailedNews;
import com.buyerologie.user.UserActionService;
import com.buyerologie.utils.PageUtil;
import com.buyerologie.web.comment.CommentApiController;

@Controller
public class NewsDetailPageController {

    @Resource
    private NewsService          newsService;

    @Resource
    private CommentService       commentService;

    @Resource
    private SecurityService      securityService;

    @Resource
    private UserActionService    userActionService;

    @Resource
    private CommentApiController commentApiController;

    private static final int     DEFAULT_COMMENT_PAGE      = 1;

    private static final int     DEFAULT_COMMENT_PAGE_SIZE = 3;

    @RequestMapping(value = "/news/articles/{newsId}.html", method = RequestMethod.GET)
    public String userDetail(Model model, @PathVariable int newsId) throws PageNotFoundException {
        DetailedNews detailedNews = newsService.getNews(newsId);

        UserAuthCredentials user = securityService.getCurrentUser();
        if (user != null) {
            detailedNews.setIsCollected(userActionService.isCollected(user.getId(), newsId));
        }
        model.addAttribute("newsDetail", detailedNews);

        int totalNum = commentService.countComment(newsId);
        model.addAttribute("page", DEFAULT_COMMENT_PAGE);
        model.addAttribute("totalNum", totalNum);
        model
            .addAttribute("totalPage", PageUtil.calcPageTotal(totalNum, DEFAULT_COMMENT_PAGE_SIZE));

        model.addAttribute("pageName", "infoDetail");
        return "page/topic/infoDetail";
    }

}
