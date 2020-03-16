package com.lin.ym.thirdpay.factory.payFor;

import com.lin.ym.thirdpay.factory.PayFor;
import com.lin.ym.thirdpay.input.PayRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("shopping")
public class Shopping implements PayFor {
    /**
     * 获取支付信息供app端调用
     *
     * @param payRequest
     * @return PayInfo
     * @throws Exception
     */
    @Override
    public Map getPayInfo(PayRequest payRequest) throws Exception {
        Map<String, String> result = new HashMap<>();
        result.put("body", "body");
        result.put("subject", "subject");
        result.put("totalAmount", "0.01");
        result.put("orderNo", payRequest.getOrderNo());


        return result;
    }

    /**
     * 接收支付通知
     *
     * @param map
     * @throws Exception
     */
    @Override
    public void success(Map<String, String> map) throws Exception {

    }
}
