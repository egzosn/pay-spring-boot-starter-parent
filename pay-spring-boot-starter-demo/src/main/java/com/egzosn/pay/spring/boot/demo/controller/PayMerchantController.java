package com.egzosn.pay.spring.boot.demo.controller;

import com.egzosn.pay.spring.boot.core.MerchantPayServiceManager;
import com.egzosn.pay.spring.boot.core.bean.MerchantPayOrder;

import org.apache.commons.logging.impl.SLF4JLocationAwareLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author egan
 *         email egzosn@gmail.com
 *         date 2019/5/26.20:10
 */
@RequestMapping("pay")
@Controller
public class PayMerchantController {

    @Autowired
    private MerchantPayServiceManager manager;


    /**
     * 网页支付
     * @param detailsId 列表id
     * @param wayTrade 交易方式
     * @return 网页
     */
    @ResponseBody
    @RequestMapping(value = "toPay.html", produces = "text/html;charset=UTF-8")
    public String toPay(String detailsId, String wayTrade, BigDecimal price) {
        MerchantPayOrder payOrder = new MerchantPayOrder(detailsId, wayTrade, "订单title", "摘要", null == price ? new BigDecimal(0.01) : price, UUID.randomUUID().toString().replace("-", ""));
        return manager.toPay(payOrder);
    }
    /**
     * 网页支付
     * @param detailsId 列表id
     * @param wayTrade 交易方式
     * @return 网页
     */
    @ResponseBody
    @RequestMapping(value = "toQrPay.jpg")
    public byte[] toQrPay(String detailsId, String wayTrade, BigDecimal price) throws IOException {
        MerchantPayOrder payOrder = new MerchantPayOrder(detailsId, wayTrade, "订单title", "摘要", null == price ? new BigDecimal(0.01) : price, UUID.randomUUID().toString().replace("-", ""));
        return manager.toQrPay(payOrder);
    }

}
