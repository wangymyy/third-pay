package com.lin.ym.thirdpay.input;

import com.lin.ym.thirdpay.enums.PayForEnum;
import com.lin.ym.thirdpay.enums.PayTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class PayRequest implements Serializable {
    private static final long serialVersionUID = 1L;


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
    private PayForEnum payFor;


    /**
     * payType 1.aliPay  2.WeChat
     */
    private PayTypeEnum payType;


    /**
     * attach
     */
    private Map<String, String> attach;


}
