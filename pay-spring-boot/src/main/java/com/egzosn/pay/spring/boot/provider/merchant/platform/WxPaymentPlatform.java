package com.egzosn.pay.spring.boot.provider.merchant.platform;

import com.egzosn.pay.common.api.PayConfigStorage;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.TransactionType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.wx.api.WxPayConfigStorage;
import com.egzosn.pay.wx.bean.WxTransactionType;

/**
 * 支付宝支付平台
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date  2019/4/4 14:35.
 *         </pre>
 */
public  class WxPaymentPlatform extends WxPayConfigStorage implements PaymentPlatform {

    private static final String platformName = "wxPay";

    public static final PaymentPlatform PLATFORM = new WxPaymentPlatform();

    private WxPaymentPlatform() {
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
        return null;
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
        return WxTransactionType.valueOf(name);
    }


}
