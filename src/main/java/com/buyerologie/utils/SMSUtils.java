package com.buyerologie.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.buyerologie.exception.sms.SMSException;
import com.buyerologie.exception.sms.SMSSendFailedException;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SMSUtils {

    private static final String URL                            = "https://eco.taobao.com/router/rest";

    private static final String APP_KEY                        = "23551487";

    private static final String SECRET                         = "86d6f29de19b82be7bd9cfea7117259f";

    private static final String SMS_TYPE                       = "normal";

    private static final String SIGN_NAME                      = "杭州波点大淑";

    private static final String SEND_REGISTER_SMS_TEMPLATE_ID  = "SMS_31315004";

    private static final String RESET_PASSWORD_SMS_TEMPLATE_ID = "SMS_31315002";

    private static final String VALIDATE_SMS_CONTENT           = "{\"code\":\'%s\',\"product\":\'buyerology\'}";

    private static final Logger logger                         = Logger.getLogger(SMSUtils.class);

    public static void main(String[] args) {
        try {
            sendRegisterSMS("15067145233", "123421");
        } catch (SMSException e) {
            logger.error("", e);
        }
    }

    public static void sendRegisterSMS(String phoneNumber, String code) throws SMSException {
        send(phoneNumber, String.format(VALIDATE_SMS_CONTENT, code), SEND_REGISTER_SMS_TEMPLATE_ID);
    }

    public static void sendResetPasswordSMS(String phoneNumber, String code) throws SMSException {
        send(phoneNumber, String.format(VALIDATE_SMS_CONTENT, code),
            RESET_PASSWORD_SMS_TEMPLATE_ID);
    }

    private static void send(String telNumber, String content,
                             String templateId) throws SMSSendFailedException {
        TaobaoClient client = new DefaultTaobaoClient(URL, APP_KEY, SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType(SMS_TYPE);
        req.setExtend("");
        req.setSmsFreeSignName(SIGN_NAME);
        req.setSmsParamString(content);
        req.setRecNum(telNumber);
        req.setSmsTemplateCode(templateId);
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            logger.error("", e);
        }
        if (rsp == null) {
            throw new SMSSendFailedException();
        }

        if (StringUtils.isNotBlank(rsp.getErrorCode())) {
            logger.error("短信发送失败" + rsp.getBody());
            throw new SMSSendFailedException();
        }
    }

}
