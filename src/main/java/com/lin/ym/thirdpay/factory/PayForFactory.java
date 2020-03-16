package com.lin.ym.thirdpay.factory;

import com.lin.ym.thirdpay.enums.PayForEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PayForFactory {

    @Resource(name = "shopping")
    PayFor shopping;

    @Resource(name = "fee")
    PayFor fee;

    public PayFor getPayType(int payfor) {
        if (PayForEnum.SHOPPING.getCode() == payfor) {
            return shopping;
        } else if (PayForEnum.FEE.getCode() == payfor) {
            return fee;
        } else {
            return shopping;
        }
    }
}
