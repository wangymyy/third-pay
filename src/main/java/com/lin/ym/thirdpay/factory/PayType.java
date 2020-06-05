package com.lin.ym.thirdpay.factory;

import com.lin.ym.thirdpay.input.PayRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 支付方式
 */
public abstract class PayType {


    /**
     * 获取支付参数
     *
     * @param payRequest
     * @return
     * @throws Exception
     */
    public Object getPayParams(PayRequest payRequest) throws Exception {

        Map<String, String> payInfo = PayFactory
                .getPayFor(payRequest.getPayFor())
                .getPayInfo(payRequest);

        return getParamsByMap(payInfo);
    }

   public abstract Object getParamsByMap(Map<String, String> payInfo) throws Exception;


    /**
     * 处理支付结果
     *
     * @param request
     * @throws Exception
     */
    public abstract void processingNotifications(HttpServletRequest request) throws Exception;
}
