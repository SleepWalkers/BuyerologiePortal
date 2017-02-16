package com.buyerologie.web.news;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.buyerologie.cms.service.CmsService;
import com.buyerologie.topic.NewsService;
import com.buyerologie.topic.vo.ListNews;
import com.buyerologie.utils.PageUtil;

@Controller
public class NewsListPageController {

    @Resource
    private CmsService          cmsService;

    @Resource
    private NewsService         newsService;

    private static final String PAGE_NAME         = "资讯列表页";

    private static final int    DEFAULT_PAGE_SIZE = 12;

    @RequestMapping(value = "/news/news.html", method = RequestMethod.GET)
    public String news(Model model, @RequestParam(required = false, defaultValue = "1") int page) {

        List<ListNews> listNewses = newsService.getListNewses(page, DEFAULT_PAGE_SIZE);

        model.addAttribute("cmsPage", cmsService.getPageVO(PAGE_NAME));

        model.addAttribute("totalPage",
            PageUtil.calcPageTotal(newsService.count(), DEFAULT_PAGE_SIZE));
        model.addAttribute("newses", listNewses);
        model.addAttribute("page", ++page);
        model.addAttribute("pageName", "infoList");
        return "page/topic/infoList";
    }

    @RequestMapping(value = "/news/list", method = RequestMethod.GET)
    public String newsList(Model model,
                           @RequestParam(required = false, defaultValue = "2") int page) {

        List<ListNews> listNewses = newsService.getListNewses(page, DEFAULT_PAGE_SIZE);

        model.addAttribute("totalPage",
            PageUtil.calcPageTotal(newsService.count(), DEFAULT_PAGE_SIZE));
        model.addAttribute("page", ++page);
        model.addAttribute("newses", listNewses);
        return "page/brick/infoListBrick";
    }

}
