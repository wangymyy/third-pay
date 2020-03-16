package com.lin.ym.thirdpay.factory.payFor;

import com.lin.ym.thirdpay.factory.PayFor;
import com.lin.ym.thirdpay.input.PayRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("fee")
public class Fee implements PayFor {
    /**
     * 获取支付信息供app端调用
     *
     * @param payRequest
     * @return PayInfo
     * @throws Exception
     */
    @Override
    public Map<String, String> getPayInfo(PayRequest payRequest) throws Exception {
        return null;
    }

    /**
     * 支付成功更新状态
     *
     * @param map
     * @throws Exception
     */
    @Override
    public void success(Map<String, String> map) throws Exception {

    }
}
