package com.lin.ym.thirdpay.controller;

import com.lin.ym.thirdpay.enums.PayTypeEnum;
import com.lin.ym.thirdpay.factory.PayFactory;
import com.lin.ym.thirdpay.input.PayRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
public class PayController {


    @RequestMapping("/pay")
    public Object pay(@RequestBody PayRequest request) throws Exception {

        return PayFactory.getPayType(request.getPayType()).getPayParams(request);

    }

    @RequestMapping("/alipay/notice")
    public void alipay(HttpServletRequest request, HttpServletResponse response) throws Exception {

        notice(request, response, PayTypeEnum.ALIPAY);

    }



    @RequestMapping("/wechat/notice")
    public void wechat(HttpServletRequest request, HttpServletResponse response) throws Exception {
        notice(request, response, PayTypeEnum.WECHAT);

    }
    private void notice(HttpServletRequest request, HttpServletResponse response, PayTypeEnum payTypeEnum) {
        String result = "success";
        try {
            PayFactory.getPayType(payTypeEnum).processingNotifications(request);

        } catch (Exception Ex) {
            Ex.printStackTrace();
            result = "fail";
        }

        printResult(response, result);
    }

    protected void printResult(HttpServletResponse response, String result) {
        try {
            PrintWriter print = response.getWriter();

            print.write(result);
            print.flush();
            print.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
