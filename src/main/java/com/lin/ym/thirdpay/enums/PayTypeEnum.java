package com.lin.ym.thirdpay.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lin.ym.thirdpay.factory.payType.AliPay;
import com.lin.ym.thirdpay.factory.payType.WeChat;

public enum PayTypeEnum {
    ALIPAY(1, "支付宝", AliPay.class),

    WECHAT(2, "微信", WeChat.class);

    private Integer code;
    private String value;

    private Class payTypeClass;


    PayTypeEnum(Integer code, String value, Class payTypeClass) {
        this.code = code;
        this.value = value;
        this.payTypeClass = payTypeClass;
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

    public Class getPayTypeClass() {
        return payTypeClass;
    }

    public void setPayTypeClass(Class payTypeClass) {
        this.payTypeClass = payTypeClass;
    }

    @JsonCreator
    public static PayTypeEnum fromCode(Integer code) {
        for (PayTypeEnum payTypeEnum : PayTypeEnum.values()) {
            if (payTypeEnum.getCode() == code) {
                return payTypeEnum;
            }
        }
        throw new IllegalArgumentException("unknown code");
    }
}
