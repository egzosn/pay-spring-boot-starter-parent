package com.egzosn.pay.spring.boot.provider.merchant.platform;

import com.egzosn.pay.common.api.PayConfigStorage;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.TransactionType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.paypal.api.PayPalConfigStorage;
import com.egzosn.pay.paypal.api.PayPalPayService;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.union.bean.UnionTransactionType;

/**
 * 贝宝支付平台
 *
 * @author egan
 *         <pre>
 *                 email egzosn@gmail.com
 *                 date  2019/4/4 14:35.
 *                 </pre>
 */
public class PaypalPaymentPlatform implements PaymentPlatform {
    public static final String platformName = "paypalPay";

    public static final PaymentPlatform PLATFORM = new PaypalPaymentPlatform();

    private PaypalPaymentPlatform() {
    }


    /**
     * 获取商户平台
     *
     * @return 商户平台
     */
    @Override
    public String getPlatform() {
        return platformName;
    }

    /**
     * 获取支付平台对应的支付服务
     *
     * @param payConfigStorage 支付配置
     * @return 支付服务
     */
    @Override
    public PayService getPayService(PayConfigStorage payConfigStorage) {
        if (payConfigStorage instanceof PayPalConfigStorage) {
            return new PayPalPayService((PayPalConfigStorage) payConfigStorage);
        }
        PayPalConfigStorage configStorage = new PayPalConfigStorage();
        configStorage.setClientID(payConfigStorage.getPid());
        configStorage.setClientSecret(payConfigStorage.getKeyPrivate());
        configStorage.setReturnUrl(payConfigStorage.getReturnUrl());
        //取消按钮转跳地址,这里用异步通知地址的兼容的做法
        configStorage.setNotifyUrl(payConfigStorage.getNotifyUrl());
        configStorage.setSignType(payConfigStorage.getSignType());
        configStorage.setPayType(payConfigStorage.getPayType());
        configStorage.setMsgType(payConfigStorage.getMsgType());
        configStorage.setInputCharset(payConfigStorage.getInputCharset());
        configStorage.setTest(payConfigStorage.isTest());
        return new PayPalPayService(configStorage);
    }

    /**
     * 获取支付平台对应的支付服务
     *
     * @param payConfigStorage  支付配置
     * @param httpConfigStorage 网络配置
     * @return 支付服务
     */
    @Override
    public PayService getPayService(PayConfigStorage payConfigStorage, HttpConfigStorage httpConfigStorage) {
        PayService payService = getPayService(payConfigStorage);
        payService.setRequestTemplateConfigStorage(httpConfigStorage);
        return payService;
    }

    @Override
    public TransactionType getTransactionType(String name) {
        return UnionTransactionType.valueOf(name);
    }


}
