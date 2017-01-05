package com.buyerologie.exception.sms;

import com.buyerologie.common.exception.BizException;

public class SMSException extends BizException {

    /** @author Sleepwalker 2016年12月1日 下午3:24:38 */
    private static final long serialVersionUID = 1981945067568541907L;

    public SMSException(int errorCode, String msg) {
        super(errorCode, msg);
    }

    public SMSException(String msg) {
        super(msg);
    }
}
