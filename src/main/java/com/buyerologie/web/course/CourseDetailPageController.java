package com.buyerologie.web.course;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buyerologie.common.exception.PageNotFoundException;
import com.buyerologie.topic.CommentService;
import com.buyerologie.topic.CourseService;
import com.buyerologie.topic.vo.Course;
import com.buyerologie.user.UserActionService;
import com.buyerologie.user.UserService;
import com.buyerologie.user.model.User;
import com.buyerologie.user.model.UserBroadcastRecord;
import com.buyerologie.utils.PageUtil;
import com.buyerologie.video.VideoService;
import com.buyerologie.video.vo.DetailedPlayList;
import com.buyerologie.video.vo.ListVideo;

@Controller
public class CourseDetailPageController {

    @Resource
    private UserService       userService;

    @Resource
    private VideoService      videoService;

    @Resource
    private CourseService     courseService;

    @Resource
    private CommentService    commentService;

    @Resource
    private UserActionService userActionService;

    private static final int  DEFAULT_COMMENT_PAGE      = 1;

    private static final int  DEFAULT_COMMENT_PAGE_SIZE = 3;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/course/video/{courseId}.html", method = RequestMethod.GET)
    public String courseDetail(Model model, @PathVariable int courseId)
                                                                       throws PageNotFoundException {

        Course course = courseService.getCourse(courseId);

        model.addAttribute("course", course);

        User user = userService.getCurrentUser();
        if (user != null) {
            course.setIsCollected(userActionService.isCollected(user.getId(), courseId));
        }

        model.addAttribute("playList", getPlayList(user.getId(), course.getVideoId()));

        userActionService.watchVideo(user.getId(), course.getVideoId());

        int totalNum = commentService.countComment(courseId);

        model.addAttribute("page", DEFAULT_COMMENT_PAGE);
        model.addAttribute("totalNum", totalNum);
        model
            .addAttribute("totalPage", PageUtil.calcPageTotal(totalNum, DEFAULT_COMMENT_PAGE_SIZE));
        model.addAttribute("pageName", "courseDetail");
        return "page/course/courseDetail";
    }

    private DetailedPlayList getPlayList(int userId, String videoId) {
        DetailedPlayList detailedPlayList = videoService.getDetailedPlayList(videoId);
        List<UserBroadcastRecord> broadcastRecords = userActionService.getBroadcastRecords(userId);
        if (broadcastRecords == null || broadcastRecords.isEmpty()) {
            return detailedPlayList;
        }
        for (ListVideo listVideo : detailedPlayList.getVideos()) {
            for (UserBroadcastRecord broadcastRecord : broadcastRecords) {
                if (broadcastRecord.getVideoId().equals(listVideo.getId())) {
                    listVideo.setLearned(true);
                    break;
                }
            }
        }
        return detailedPlayList;
    }
}
