package com.buyerologie.domain;

import com.buyerologie.utils.ClassLoaderUtil;

public class Domain {

    private static volatile Domain instance;

    private String                 domain;

    private Domain() {
        this.domain = ClassLoaderUtil.getProperties("app.properties").getProperty("domain");
    }

    /**
     * 域名 http://cloudpay.liulianginn.com
     */
    public static Domain getInstance() {
        if (instance == null) {
            synchronized (Domain.class) {
                if (instance == null) {
                    instance = new Domain();
                }
            }
        }
        return instance;
    }

    public String getDomain() {
        return domain;
    }
}