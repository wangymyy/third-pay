package com.lin.ym.thirdpay.factory.payType;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.lin.ym.thirdpay.enums.PayForEnum;
import com.lin.ym.thirdpay.enums.PayTypeEnum;
import com.lin.ym.thirdpay.factory.PayFactory;
import com.lin.ym.thirdpay.factory.PayFor;
import com.lin.ym.thirdpay.factory.PayType;
import com.lin.ym.thirdpay.util.AmountUtils;
import com.lin.ym.thirdpay.util.RequestToMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Component("aliPay")
@Slf4j
public class AliPay extends PayType {


    @Value("${APP_ID}")
    private String APP_ID;
    @Value("${APP_PRIVATE_KEY}")
    private String APP_PRIVATE_KEY;
    @Value("${ALIPAY_PUBLIC_KEY}")
    private String ALIPAY_PUBLIC_KEY;
    @Value("${ALIPAY_NOTIFY_URL}")
    private String ALIPAY_NOTIFY_URL;

    private String CHARSET = "UTF-8";
    public final static String TRADE_SUCCESS = "TRADE_SUCCESS";


    /**
     * 获取支付参数
     *
     * @param payInfo
     * @return
     * @throws Exception
     */
    @Override
    public Object getParamsByMap(Map<String, String> payInfo) throws Exception {

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipay.com/gateway.do",
                APP_ID, APP_PRIVATE_KEY, "json",
                CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,
        // 当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。
        //以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(payInfo.get("body"));
        model.setSubject(payInfo.get("subject"));
        model.setOutTradeNo(payInfo.get("orderNumber"));
        model.setTimeoutExpress("30m");
        model.setTotalAmount(AmountUtils.changeF2Y(payInfo.get("totalAmount")));
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(ALIPAY_NOTIFY_URL);
        model.setPassbackParams("");// 附加信息
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。

            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 处理支付结果
     *
     * @param request
     * @throws Exception
     */
    @Override
    public void processingNotifications(HttpServletRequest request) throws Exception {
        Map<String, String> params = resloveAliMessage(request);
        /**
         * 本地存储阿里通知信息 TODO
         */
        // noticeAliService.saveAliMessage(params);

        /**
         * 根据支付项目类型处理
         */
        String passbackParams = params.get("passback_params");

        JSONObject jsonObject = JSONObject.parseObject(passbackParams);

        String tradeStatus = params.get("trade_status");
        String totalAmount = params.get("total_amount");
        String outTradeNo = params.get("out_trade_no");
        Map<String, String> map = new HashMap<>(10);
        map.put("paymentNo", params.get("trade_no"));// 支付宝交易号

        map.put("outTradeNo", outTradeNo);// 订单号
        map.put("totalAmount", totalAmount);// 支付金额
        map.put("tradeStatus", tradeStatus); // 支付状态
        map.put("passback_params", passbackParams);
//        map.put("payType", String.valueOf(EnumPayType.支付宝.getIndex()));


        if (TRADE_SUCCESS.equals(tradeStatus)) {
            PayFor payFor = PayFactory.getPayFor((PayForEnum) jsonObject.get("payFor"));
            payFor.success(map, PayTypeEnum.ALIPAY);
        }
    }

    protected Map<String, String> resloveAliMessage(HttpServletRequest request) throws Exception {
        Map<String, String> params = RequestToMap.resloveAliMessage(request);

        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "utf-8", "RSA2");
        } catch (AlipayApiException e) {
            log.error("支付宝验证签名失败,失败代码{}，失败详情{}.", e.getErrCode(), e.getErrMsg());
            throw e;
        }
        if (!signVerified) {
            throw new Exception("支付宝验证签名失败!");
        }
        return params;
    }
}
