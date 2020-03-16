package com.lin.ym.thirdpay.enums;

public enum PayForEnum {

    SHOPPING(1, "购物"),
    FEE(2, "物业费");

    private Integer code;
    private String value;


    PayForEnum(Integer code, String value) {
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
