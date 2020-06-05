package com.lin.ym.thirdpay.factory;


import com.lin.ym.thirdpay.enums.PayTypeEnum;
import com.lin.ym.thirdpay.input.PayRequest;

import java.util.Map;

public interface PayFor {
    /**
     * 获取支付信息供app端调用
     *
     * @param payRequest
     * @return PayInfo
     * @throws Exception
     */
    Map<String, String> getPayInfo(PayRequest payRequest) throws Exception;


    /**
     * 支付成功更新状态
     *
     * @param map
     * @throws Exception
     */
    void success(Map<String, String> map, PayTypeEnum payTypeEnum) throws Exception;


}
