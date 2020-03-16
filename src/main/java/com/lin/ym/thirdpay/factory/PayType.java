package com.lin.ym.thirdpay.factory;

import com.lin.ym.thirdpay.input.PayRequest;

import javax.servlet.http.HttpServletRequest;

public interface PayType {


    /**
     * 获取支付参数
     *
     * @param payRequest
     * @return
     * @throws Exception
     */
    Object getPayParams(PayRequest payRequest) throws Exception;


    /**
     * 处理支付结果
     *
     * @param request
     * @throws Exception
     */
    void processingNotifications(HttpServletRequest request) throws Exception;
}
