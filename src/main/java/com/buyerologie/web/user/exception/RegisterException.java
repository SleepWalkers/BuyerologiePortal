package com.buyerologie.web.user.exception;

import com.buyerologie.user.exception.UserException;

public class RegisterException extends UserException {

    /** @author Sleepwalker 2016年12月7日 下午10:33:57 */
    private static final long serialVersionUID = 358728805294903445L;

    public RegisterException(int errorCode, String msg) {
        super(errorCode, msg);
    }

    public RegisterException(String msg) {
        super(msg);
    }
}
