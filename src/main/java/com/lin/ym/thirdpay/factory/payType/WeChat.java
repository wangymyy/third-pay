package com.lin.ym.thirdpay.factory.payType;

import com.lin.ym.thirdpay.factory.PayFactory;
import com.lin.ym.thirdpay.factory.PayType;
import com.lin.ym.thirdpay.factory.wx.PayCommonUtil;
import com.lin.ym.thirdpay.input.PayRequest;
import org.apache.commons.lang3.StringUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Component("weChat")
public class WeChat implements PayType {


    @Value(value = "${WX_UNIFIED_NOTIFY_URL}")
    private String notifyUrl;


    @Value(value = "${WX_APP_ID}")
    private String appId;


    @Value(value = "${WX_MCH_ID}")
    private String mchId;


    @Autowired
    private PayCommonUtil payCommonUtil;

    /**
     * 获取支付参数
     *
     * @param payRequest
     * @return
     * @throws Exception
     */
    @Override
    public Object getPayParams(PayRequest payRequest) throws Exception {

        Map<String, String> payInfo = PayFactory
                .getPayFor(payRequest.getPayFor())
                .getPayInfo(payRequest);
        return weixinPrePay(payInfo);

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


    private Map<String, String> weixinPrePay(Map<String, String> payInfo) throws Exception {
        String randomString = payCommonUtil.getRandomString(32);
        SortedMap<String, Object> parameterMap = new TreeMap<>();
        parameterMap.put("appid", appId);
        parameterMap.put("mch_id", mchId);

        parameterMap.put("nonce_str", randomString);
        parameterMap.put("body", payInfo.get("body"));
        parameterMap.put("out_trade_no", payInfo.get("orderNo"));
        parameterMap.put("fee_type", "CNY");
//        BigDecimal total = new BigDecimal(payInfo.get("totalAmount")).multiply(new BigDecimal(100));
//        java.text.DecimalFormat df = new java.text.DecimalFormat("0");
        parameterMap.put("total_fee", payInfo.get("totalAmount"));
//        parameterMap.put("spbill_create_ip", payInfo.getIpAddr());
        parameterMap.put("notify_url", notifyUrl);
        parameterMap.put("trade_type", "APP");

        if (StringUtils.isNoneEmpty(payInfo.get("attach"))) {
            parameterMap.put("attach", payInfo.get("attach"));
        }
        // trade_type为JSAPI是 openid为必填项 parameterMap.put("openid", openid);

        String sign = payCommonUtil.createSign("UTF-8", parameterMap);
        parameterMap.put("sign", sign);
        String requestXML = payCommonUtil.getRequestXml(parameterMap);
        System.out.println(requestXML);
        String result = payCommonUtil.httpsRequest(
                "https://api.mch.weixin.qq.com/pay/unifiedorder",
                "POST",
                requestXML);
        System.out.println(result);
        Map<String, String> map = null;
        try {
            map = payCommonUtil.doXMLParse(result);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (StringUtils.isNotEmpty(map.get("err_code"))) {
            throw new Exception(map.get("err_code_des"));
        }
        return map;
    }
}
