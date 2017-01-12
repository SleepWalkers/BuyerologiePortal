package com.buyerologie.web.user;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.buyerologie.common.vo.JsonVO;
import com.buyerologie.user.UserService;
import com.buyerologie.user.exception.UserException;
import com.buyerologie.user.exception.UserNotFoundException;
import com.buyerologie.user.model.User;
import com.buyerologie.utils.FileUploadUtils;

@Controller
public class UserApiController {

    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/api/user/edit", method = { RequestMethod.POST, RequestMethod.GET })
    public String editUser(@RequestParam(required = false) String nickname,
                           @RequestParam(required = false) String profession,
                           @RequestParam(required = false) MultipartFile avatarFile)
                                                                                    throws UserException {

        User user = userService.getCurrentUser();
        if (user == null) {
            throw new UserNotFoundException();
        }

        if (StringUtils.isNotBlank(nickname)) {
            user.setNickname(nickname);
        }
        if (StringUtils.isNotBlank(profession)) {
            user.setProfession(profession);
        }

        if (avatarFile != null && avatarFile.getSize() > 0) {
            user.setAvatar(FileUploadUtils.uploadImage(avatarFile));
        }
        userService.edit(user);
        return new JsonVO(true).toString();
    }
}
