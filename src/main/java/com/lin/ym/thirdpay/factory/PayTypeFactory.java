package com.lin.ym.thirdpay.factory;

import com.lin.ym.thirdpay.enums.PayTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PayTypeFactory {

    @Resource(name = "aliPay")
    PayType aliPay;

    @Resource(name = "weChat")
    PayType weChat;

    public PayType getPayType(int payType) {
        if (PayTypeEnum.ALIPAY.getCode() == payType) {
            return aliPay;
        } else if (PayTypeEnum.WECHAT.getCode() == payType) {
            return weChat;
        } else {
            return aliPay;
        }
    }


}
