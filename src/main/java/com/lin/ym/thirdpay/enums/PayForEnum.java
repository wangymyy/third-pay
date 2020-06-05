package com.lin.ym.thirdpay.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lin.ym.thirdpay.factory.payFor.Fee;
import com.lin.ym.thirdpay.factory.payFor.Shopping;

public enum PayForEnum {

    SHOPPING(1, "购物", Shopping.class),
    FEE(2, "物业费", Fee.class);

    private Integer code;
    private String value;
    private Class payForClass;


    PayForEnum(Integer code, String value, Class payForClass) {
        this.code = code;
        this.value = value;
        this.payForClass = payForClass;
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


    public Class getPayForClass() {
        return payForClass;
    }

    public void setPayForClass(Class payForClass) {
        this.payForClass = payForClass;
    }

    @JsonCreator
    public static PayForEnum fromCode(Integer code) {
        for (PayForEnum payForEnum : PayForEnum.values()) {
            if (payForEnum.getCode() == code) {
                return payForEnum;
            }
        }
        throw new IllegalArgumentException("unknown code");
    }
}
