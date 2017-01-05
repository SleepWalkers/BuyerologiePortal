package com.buyerologie.web.user.exception;

public class PhoneNumberValidatedException extends RegisterException {

    /** @author Sleepwalker 2016年12月7日 下午10:50:10 */
    private static final long   serialVersionUID  = 1243678741945954585L;

    private static final String DEFAULT_ERROR_MSG = "手机号格式不正确";

    public PhoneNumberValidatedException() {
        super(DEFAULT_ERROR_MSG);
    }

    public PhoneNumberValidatedException(int errorCode, String msg) {
        super(errorCode, msg);
    }

    public PhoneNumberValidatedException(String msg) {
        super(msg);
    }

}
