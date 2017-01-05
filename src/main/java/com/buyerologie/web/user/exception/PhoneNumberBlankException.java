package com.buyerologie.web.user.exception;

public class PhoneNumberBlankException extends RegisterException {

    /** @author Sleepwalker 2016年12月7日 下午10:36:59 */
    private static final long   serialVersionUID  = 1326389087348062891L;
    private static final String DEFAULT_ERROR_MSG = "手机号不能为空";

    public PhoneNumberBlankException() {
        super(DEFAULT_ERROR_MSG);
    }

    public PhoneNumberBlankException(int errorCode, String msg) {
        super(errorCode, msg);
    }

    public PhoneNumberBlankException(String msg) {
        super(msg);
    }

}
