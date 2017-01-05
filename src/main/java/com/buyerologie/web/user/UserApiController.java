package com.buyerologie.web.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.buyerologie.user.UserService;
import com.buyerologie.user.exception.UserException;
import com.buyerologie.user.exception.UserNotFoundException;
import com.buyerologie.user.model.User;
import com.buyerologie.utils.FileUploadUtils;

@Controller
public class UserApiController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/api/user/edit", method = { RequestMethod.POST, RequestMethod.GET })
    public String editUser(@RequestParam String nickname, @RequestParam String profession,
                           MultipartFile avatarFile) throws UserException {

        User user = userService.getCurrentUser();
        if (user == null) {
            throw new UserNotFoundException();
        }

        user.setNickname(nickname);
        user.setProfession(profession);

        if (avatarFile != null && avatarFile.getSize() > 0) {
            user.setAvatar(FileUploadUtils.uploadImage(avatarFile));
        }
        userService.edit(user);
        return "redirect:/account/usermodify.html";
    }
}
