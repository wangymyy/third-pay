package com.lin.ym.thirdpay.enums;

public enum  PayTypeEnum {
    ALIPAY(1,"支付宝"),

    WECHAT(2,"微信");

    private Integer code;
    private String value;


    PayTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
