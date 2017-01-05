package com.buyerologie.web.user.exception;

public class PhoneNumberRegisteredException extends RegisterException {

    /** @author Sleepwalker 2016年12月7日 下午10:51:12 */
    private static final long   serialVersionUID  = -9003118682711022895L;

    private static final String DEFAULT_ERROR_MSG = "该手机号已被注册";

    public PhoneNumberRegisteredException() {
        super(DEFAULT_ERROR_MSG);
    }

    public PhoneNumberRegisteredException(int errorCode, String msg) {
        super(errorCode, msg);
    }

    public PhoneNumberRegisteredException(String msg) {
        super(msg);
    }

}
