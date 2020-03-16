package com.lin.ym.thirdpay.input;

import lombok.Data;

import java.util.Map;

@Data
public class PayRequest {

    /**
     * buyer id
     */
    private String userId;

    /**
     * orderNo
     */
    private String orderNo;


    /**
     * 1.shopping 2.other
     */
    private Integer payFor;


    /**
     * payType 1.aliPay  2.WeChat
     */
    private Integer payType ;


    /**
     * attach
     */
    private Map<String,String> attach;



}
