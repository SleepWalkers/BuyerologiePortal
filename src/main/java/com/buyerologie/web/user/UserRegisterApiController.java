package com.buyerologie.web.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buyerologie.common.vo.JsonVO;
import com.buyerologie.exception.sms.SMSException;
import com.buyerologie.security.SecurityService;
import com.buyerologie.user.UserService;
import com.buyerologie.user.exception.UserException;
import com.buyerologie.user.exception.UserNotFoundException;
import com.buyerologie.user.model.User;
import com.buyerologie.utils.EncryptionUtil;
import com.buyerologie.utils.NumberUtil;
import com.buyerologie.utils.SMSUtils;
import com.buyerologie.utils.VerifyUtil;
import com.buyerologie.web.user.exception.PhoneNumberBlankException;
import com.buyerologie.web.user.exception.PhoneNumberRegisteredException;
import com.buyerologie.web.user.exception.PhoneNumberValidatedException;
import com.buyerologie.web.user.exception.RegisterException;
import com.buyerologie.web.user.exception.ValidateCodeDurationException;
import com.buyerologie.web.user.exception.ValidateCodeExpireException;
import com.buyerologie.web.user.exception.ValidateCodeWrongException;

@Controller
public class UserRegisterApiController {

    /*
     * 两次获取校验码的时间间隔(单位分钟)
     */
    private static final long   DURATION                   = 1;

    /*
     * 校验码的有效时间(单位分钟)
     */
    private static final long   TIMEOUT                    = 20;

    /*
     * 校验码长度
     */
    private static final int    CHECK_CODE_LENGTH          = 6;

    private static final String SMS_CODE_SESSION_KEY       = "SMSCheckCode";

    private static final String SMS_CODE_GENERATE_TIME_KEY = "SMSCheckCodeGenerateTime";

    @Resource
    private UserService         userService;

    @Resource
    private SecurityService     securityService;

    @ResponseBody
    @RequestMapping(value = "/api/user/getResetCheckCode", method = RequestMethod.POST)
    public String postSMSResetCode(HttpSession session,
                                   @RequestParam(required = true) String phone) throws SMSException,
                                                                                UserException {

        if (StringUtils.isBlank(phone)) {
            throw new PhoneNumberBlankException();
        }

        if (!VerifyUtil.checkMobile(phone)) {
            throw new PhoneNumberValidatedException();
        }

        if (!userService.foundUser(phone)) {
            throw new UserNotFoundException();
        }

        int checkCode = NumberUtil.getRandomIntByLength(CHECK_CODE_LENGTH);

        System.out.println(checkCode);
        //判断用户获取校验码是否过于频繁
        if (session.getAttribute(SMS_CODE_GENERATE_TIME_KEY) != null) {
            String generateTimeStr = session.getAttribute(SMS_CODE_GENERATE_TIME_KEY).toString();
            long generateTime = Long.parseLong(generateTimeStr);
            if (System.currentTimeMillis() - generateTime < DURATION * 60 * 1000) {
                throw new ValidateCodeDurationException();
            }
        }

        SMSUtils.sendResetPasswordSMS(phone, checkCode + "");

        session.setAttribute("SMSCheckCodeGenerateTime", System.currentTimeMillis());
        //加密校验码
        String encryptCheckCode = new EncryptionUtil().encryptToMD5(String.valueOf(checkCode));
        session.setAttribute("SMSCheckCode", encryptCheckCode);

        return new JsonVO(true).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/api/user/getCheckCode", method = RequestMethod.POST)
    public String postSMSCheckCode(HttpSession session,
                                   @RequestParam(required = true) String phone) throws RegisterException,
                                                                                SMSException {

        if (StringUtils.isBlank(phone)) {
            throw new PhoneNumberBlankException();
        }

        if (!VerifyUtil.checkMobile(phone)) {
            throw new PhoneNumberValidatedException();
        }

        if (userService.foundUser(phone)) {
            throw new PhoneNumberRegisteredException();
        }

        int checkCode = NumberUtil.getRandomIntByLength(CHECK_CODE_LENGTH);

        System.out.println(checkCode);
        //判断用户获取校验码是否过于频繁
        if (session.getAttribute(SMS_CODE_GENERATE_TIME_KEY) != null) {
            String generateTimeStr = session.getAttribute(SMS_CODE_GENERATE_TIME_KEY).toString();
            long generateTime = Long.parseLong(generateTimeStr);
            if (System.currentTimeMillis() - generateTime < DURATION * 60 * 1000) {
                throw new ValidateCodeDurationException();
            }
        }

        SMSUtils.sendRegisterSMS(phone, checkCode + "");

        session.setAttribute("SMSCheckCodeGenerateTime", System.currentTimeMillis());
        //加密校验码
        String encryptCheckCode = new EncryptionUtil().encryptToMD5(String.valueOf(checkCode));
        session.setAttribute("SMSCheckCode", encryptCheckCode);

        return new JsonVO(true).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/api/user/reset", method = RequestMethod.POST)
    public String resetPassword(HttpSession session, @RequestParam(required = true) String phone,
                                @RequestParam(required = true) String code,
                                @RequestParam(required = true) String password,
                                HttpServletRequest request,
                                HttpServletResponse response) throws UserException {

        validate(session, phone, code);

        User user = userService.getUser(phone);
        if (user == null) {
            throw new UserNotFoundException();
        }

        userService.setPassword(user.getId(), password);

        securityService.login(request, response, phone, password);
        JsonVO jsonVO = new JsonVO(true);
        return jsonVO.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/api/user/register", method = RequestMethod.POST)
    public String validatePhone(HttpSession session, @RequestParam(required = true) String phone,
                                @RequestParam(required = true) String code,
                                @RequestParam(required = true) String password,
                                @RequestParam(required = false) String redirectUrl,
                                HttpServletRequest request,
                                HttpServletResponse response) throws UserException {

        validate(session, phone, code);

        userService.register(new User(phone, password));
        securityService.login(request, response, phone, password);
        JsonVO jsonVO = new JsonVO(true);
        jsonVO.setIsRedirect(true);
        jsonVO.setRedirectURL(redirectUrl);
        return jsonVO.toString();
    }

    private void validate(HttpSession session, String phone, String code) throws UserException {
        Object checkCodeObj = session.getAttribute(SMS_CODE_SESSION_KEY);
        Object generateTimeObj = session.getAttribute(SMS_CODE_GENERATE_TIME_KEY);

        if (checkCodeObj == null || generateTimeObj == null) {
            throw new ValidateCodeWrongException();
        }
        String encryptCheckCode = checkCodeObj.toString();
        long generateTime = Long.parseLong(generateTimeObj.toString());

        if (System.currentTimeMillis() - generateTime > TIMEOUT * 60 * 1000) {
            throw new ValidateCodeExpireException();
        }

        if (!VerifyUtil.checkMobile(phone)) {
            throw new PhoneNumberValidatedException();
        }

        String encryptClientCode = new EncryptionUtil().encryptToMD5(code);
        if (!encryptCheckCode.equals(encryptClientCode)) {
            throw new ValidateCodeWrongException();
        }
    }
}
