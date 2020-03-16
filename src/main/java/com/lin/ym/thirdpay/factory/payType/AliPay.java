package com.lin.ym.thirdpay.factory.payType;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.lin.ym.thirdpay.factory.PayForFactory;
import com.lin.ym.thirdpay.factory.PayType;
import com.lin.ym.thirdpay.input.PayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Component("aliPay")
public class AliPay implements PayType {


    @Value("${APP_ID}")
    private String APP_ID;
    @Value("${APP_PRIVATE_KEY}")
    private String APP_PRIVATE_KEY;
    @Value("${ALIPAY_PUBLIC_KEY}")
    private String ALIPAY_PUBLIC_KEY;
    @Value("${ALIPAY_NOTIFY_URL}")
    private String ALIPAY_NOTIFY_URL;

    private String CHARSET = "UTF-8";


    @Autowired
    PayForFactory payForFactory;

    /**
     * 获取支付参数
     *
     * @param payRequest
     * @return
     * @throws Exception
     */
    @Override
    public String getPayParams(PayRequest payRequest) throws Exception {

        Map<String, String> payInfo = payForFactory
                .getPayType(payRequest.getPayType())
                .getPayInfo(payRequest);
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
        model.setOutTradeNo(payRequest.getOrderNo());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(payInfo.get("totalAmount"));
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

    }
}
