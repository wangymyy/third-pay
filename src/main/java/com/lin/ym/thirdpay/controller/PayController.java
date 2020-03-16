package com.lin.ym.thirdpay.controller;

import com.lin.ym.thirdpay.input.PayRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {


    @RequestMapping("/pay")
    public Object pay(@RequestBody PayRequest request) {

        return new Object();
    }
}
