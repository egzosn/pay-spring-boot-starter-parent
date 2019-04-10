package com.egzosn.pay.spring.boot.provider.merchant.platform;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.api.PayConfigStorage;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.TransactionType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;

/**
 * 支付宝支付平台
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date  2019/4/4 14:35.
 *         </pre>
 */
public  class YoudianPaymentPlatform implements PaymentPlatform {

    private static final String platformName = "youdianPay";

    public static final PaymentPlatform PLATFORM = new YoudianPaymentPlatform();

    private YoudianPaymentPlatform() {
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
        if ( payConfigStorage instanceof AliPayConfigStorage ){
            return new AliPayService((AliPayConfigStorage) payConfigStorage);
        }
        AliPayConfigStorage configStorage = new AliPayConfigStorage();
        configStorage.setInputCharset(payConfigStorage.getInputCharset());
        configStorage.setAppId(payConfigStorage.getAppid());
        configStorage.setPid(payConfigStorage.getPid());
        configStorage.setAttach(payConfigStorage.getAttach());
        configStorage.setSeller(payConfigStorage.getSeller());
        configStorage.setKeyPrivate(payConfigStorage.getKeyPrivate());
        configStorage.setKeyPublic(payConfigStorage.getKeyPublic());
        configStorage.setNotifyUrl(payConfigStorage.getNotifyUrl());
        configStorage.setReturnUrl(payConfigStorage.getReturnUrl());
        configStorage.setMsgType(payConfigStorage.getMsgType());
        configStorage.setPayType(payConfigStorage.getPayType());
        configStorage.setTest(payConfigStorage.isTest());
        configStorage.setSignType(payConfigStorage.getSignType());
        return new AliPayService(configStorage);
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
        return AliTransactionType.valueOf(name);
    }


}
