package com.buyerologie.web.user.exception;

public class ValidateCodeWrongException extends RegisterException {

    /** @author Sleepwalker 2016年12月7日 下午10:50:10 */
    private static final long   serialVersionUID  = 1243678741945954585L;

    private static final String DEFAULT_ERROR_MSG = "验证码错误，请重新输入";

    public ValidateCodeWrongException() {
        super(DEFAULT_ERROR_MSG);
    }

    public ValidateCodeWrongException(int errorCode, String msg) {
        super(errorCode, msg);
    }

    public ValidateCodeWrongException(String msg) {
        super(msg);
    }

}
