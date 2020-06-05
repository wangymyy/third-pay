package com.lin.ym.thirdpay.factory;

import com.lin.ym.thirdpay.enums.PayForEnum;
import com.lin.ym.thirdpay.enums.PayTypeEnum;
import com.lin.ym.thirdpay.util.SpringContextHolder;


public class PayFactory {


    public static PayFor getPayFor(PayForEnum payForEnum) {

        return (PayFor) SpringContextHolder.getBean(payForEnum.getPayForClass());
    }

    public static PayType getPayType(PayTypeEnum payTypeEnum) {

        return (PayType) SpringContextHolder.getBean(payTypeEnum.getPayTypeClass());
    }

}
