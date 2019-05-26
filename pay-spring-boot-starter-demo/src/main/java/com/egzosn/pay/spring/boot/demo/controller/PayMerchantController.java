package com.egzosn.pay.spring.boot.demo.controller;

import com.egzosn.pay.spring.boot.core.MerchantPayServiceManager;
import com.egzosn.pay.spring.boot.core.bean.MerchantPayOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author egan
 *         email egzosn@gmail.com
 *         date 2019/5/26.20:10
 */
@RequestMapping("pay")
@Controller
public class PayMerchantController {

    @Autowired
    MerchantPayServiceManager manager;


    @ResponseBody
    @RequestMapping("toPay")
    public String toPay(MerchantPayOrder payOrder){
        return manager.toPay(payOrder);
    }

}
