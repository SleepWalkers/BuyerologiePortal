package com.buyerologie.web.user.exception;

public class ValidateCodeDurationException extends RegisterException {

    /** @author Sleepwalker 2016年12月7日 下午10:50:10 */
    private static final long   serialVersionUID  = 1243678741945954585L;

    private static final String DEFAULT_ERROR_MSG = "1分钟内请勿重复获取验证码";

    public ValidateCodeDurationException() {
        super(DEFAULT_ERROR_MSG);
    }

    public ValidateCodeDurationException(int errorCode, String msg) {
        super(errorCode, msg);
    }

    public ValidateCodeDurationException(String msg) {
        super(msg);
    }

}
