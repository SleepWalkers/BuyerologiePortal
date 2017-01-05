package com.buyerologie.web.user.exception;

public class ValidateCodeExpireException extends RegisterException {

    /** @author Sleepwalker 2016年12月7日 下午10:50:10 */
    private static final long   serialVersionUID  = 1243678741945954585L;

    private static final String DEFAULT_ERROR_MSG = "验证码已过期，请重新获取";

    public ValidateCodeExpireException() {
        super(DEFAULT_ERROR_MSG);
    }

    public ValidateCodeExpireException(int errorCode, String msg) {
        super(errorCode, msg);
    }

    public ValidateCodeExpireException(String msg) {
        super(msg);
    }

}
