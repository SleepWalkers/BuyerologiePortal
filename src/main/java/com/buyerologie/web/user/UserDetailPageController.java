package com.buyerologie.web.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buyerologie.common.exception.PageNotFoundException;
import com.buyerologie.security.SecurityService;
import com.buyerologie.security.UserAuthCredentials;
import com.buyerologie.topic.CourseService;
import com.buyerologie.topic.NewsService;
import com.buyerologie.topic.enums.ImageType;
import com.buyerologie.topic.vo.ListNews;
import com.buyerologie.user.UserActionService;
import com.buyerologie.user.UserService;
import com.buyerologie.user.model.User;
import com.buyerologie.user.model.UserBroadcastRecord;
import com.buyerologie.user.model.VipDetail;
import com.buyerologie.video.VideoService;
import com.buyerologie.video.model.PlayList;
import com.buyerologie.video.vo.DetailedPlayList;
import com.buyerologie.video.vo.ListVideo;
import com.buyerologie.vip.VipService;

@Controller
public class UserDetailPageController {

    @Resource
    private VipService        vipService;

    @Resource
    private UserService       userService;

    @Resource
    private NewsService       newsService;

    @Resource
    private VideoService      videoService;

    @Resource
    private CourseService     courseService;

    @Resource
    private SecurityService   securityService;

    @Resource
    private UserActionService userActionService;

    private static final int  NEWS_CATE_ID   = 1;

    private static final int  COURSE_CATE_ID = 2;

    private static final long ONE_DAY        = 1 * 24 * 60 * 60 * 1000;

    @RequestMapping(value = "/account/usercenter.html", method = RequestMethod.GET)
    public String userDetail(Model model) throws PageNotFoundException {

        UserAuthCredentials userAuthCredentials = securityService.getCurrentUser();

        if (userAuthCredentials == null) {
            throw new PageNotFoundException();
        }
        User user = userService.getUser(userAuthCredentials.getId());
        model.addAttribute("user", user);

        VipDetail vipDetail = vipService.getLastVipDetail(user.getId());

        int restDays = 0;
        if(vipDetail != null){
	        model.addAttribute("vip", vipDetail);
	        if (!vipDetail.isExpired()) {
	            restDays = (int) (((vipDetail.getEndTime().getTime() - System.currentTimeMillis())) / ONE_DAY);
	            model.addAttribute("totalDays",
	                new DateTime(vipDetail.getEndTime().getTime()).getDayOfYear()
	                        - new DateTime(vipDetail.getStartTime().getTime()).getDayOfYear());
	        }
        }
        model.addAttribute("restDays", restDays);
        setUserCourse(user.getId(), model);

        return "page/user/userCenter";
    }

    private void setUserCourse(int userId, Model model) {
        List<DetailedPlayList> detailedPlayLists = videoService.getAllDetailedPlayLists();
        model.addAttribute("watchedPlayLists", getWatchedPlayLists(userId));
        model.addAttribute("collectedPlayLists", getCollectedPlayLists(userId));
        model.addAttribute("playLists", detailedPlayLists);
        model.addAttribute("pageName", "userCourse");
    }

    @RequestMapping(value = "/account/usermodify.html", method = RequestMethod.GET)
    public String usermodify(Model model) throws PageNotFoundException {

        UserAuthCredentials userAuthCredentials = securityService.getCurrentUser();

        if (userAuthCredentials == null) {
            throw new PageNotFoundException();
        }
        User user = userService.getUser(userAuthCredentials.getId());
        model.addAttribute("user", user);
        model.addAttribute("pageName", "userModifiy");
        return "page/user/userComplete";
    }

    @RequestMapping(value = "/account/favourite.html", method = RequestMethod.GET)
    public String userFavourite(Model model) throws PageNotFoundException {

        UserAuthCredentials userAuthCredentials = securityService.getCurrentUser();

        if (userAuthCredentials == null) {
            throw new PageNotFoundException();
        }
        User user = userService.getUser(userAuthCredentials.getId());
        model.addAttribute("user", user);

        List<Integer> newsIds = userActionService.getCollectedTopicId(user.getId(), NEWS_CATE_ID);

        List<ListNews> collectedNewses = newsService.getListNews(newsIds, ImageType.COLLECT);
        model.addAttribute("collectedNewses", collectedNewses);
        model.addAttribute("pageName", "userNews");
        return "page/user/userCenter";
    }

    private List<DetailedPlayList> getCollectedPlayLists(int userId) {
        List<Integer> courseIds = userActionService.getCollectedTopicId(userId, COURSE_CATE_ID);
        if (courseIds == null || courseIds.isEmpty()) {
            return null;
        }

        List<String> videoIds = courseService.getCourseVideoIds(courseIds);

        return getListVideo(videoIds);
    }

    private List<DetailedPlayList> getWatchedPlayLists(int userId) {
        List<UserBroadcastRecord> broadcastRecords = userActionService.getBroadcastRecords(userId);
        if (broadcastRecords == null || broadcastRecords.isEmpty()) {
            return null;
        }
        List<String> videoIds = new ArrayList<>();
        for (UserBroadcastRecord broadcastRecord : broadcastRecords) {
            videoIds.add(broadcastRecord.getVideoId());
        }
        return getListVideo(videoIds);
    }

    private List<DetailedPlayList> getListVideo(List<String> videoIds) {

        List<ListVideo> watchedVideos = videoService.getListVideos(videoIds);
        if (watchedVideos == null || watchedVideos.isEmpty()) {
            return null;
        }
        return getWatchedPlayLists(watchedVideos);
    }

    private List<DetailedPlayList> getWatchedPlayLists(List<ListVideo> videos) {
        Map<Long, List<ListVideo>> videoMap = buildMap(videos);
        if (videoMap == null || videoMap.isEmpty()) {
            return null;
        }
        Map<Long, PlayList> playListMap = buildPlayListMap();
        List<DetailedPlayList> watchedPlayList = new ArrayList<>();
        for (long playListId : videoMap.keySet()) {
            watchedPlayList.add(new DetailedPlayList(playListMap.get(playListId), videoMap
                .get(playListId)));
        }
        return watchedPlayList;
    }

    private Map<Long, PlayList> buildPlayListMap() {
        List<PlayList> playLists = videoService.getAll();
        Map<Long, PlayList> map = new HashMap<>();
        for (PlayList playList : playLists) {
            map.put(playList.getId(), playList);
        }
        return map;
    }

    private Map<Long, List<ListVideo>> buildMap(List<ListVideo> videos) {
        Map<Long, List<ListVideo>> map = new HashMap<>();
        for (ListVideo listVideo : videos) {
            if (map.containsKey(listVideo.getPlayListId())) {
                map.get(listVideo.getPlayListId()).add(listVideo);
            } else {
                List<ListVideo> listVideos = new ArrayList<>();
                listVideos.add(listVideo);
                map.put(listVideo.getPlayListId(), listVideos);
            }
        }
        return map;
    }
}
