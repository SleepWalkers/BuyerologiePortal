package com.buyerologie.exception.sms;

public class SMSSendFailedException extends SMSException {

    /** @author Sleepwalker 2016年12月1日 下午3:26:54 */
    private static final long   serialVersionUID = -2153199940157554298L;

    private static final String DEFAULT_MSG      = "短信发送失败";

    public SMSSendFailedException(int errorCode, String msg) {
        super(errorCode, msg);
    }

    public SMSSendFailedException(String msg) {
        super(msg);
    }

    public SMSSendFailedException() {
        super(DEFAULT_MSG);
    }

}
