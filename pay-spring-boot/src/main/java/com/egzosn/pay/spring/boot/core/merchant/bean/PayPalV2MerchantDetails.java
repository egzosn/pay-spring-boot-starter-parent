package com.egzosn.pay.spring.boot.core.merchant.bean;

import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.paypal.api.PayPalConfigStorage;
import com.egzosn.pay.spring.boot.core.PayConfigurerAdapter;
import com.egzosn.pay.spring.boot.core.builders.InMemoryMerchantDetailsServiceBuilder;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformMerchantDetails;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformServiceAdapter;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.PaymentPlatforms;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.PaypalV2PaymentPlatform;

/**
 * 贝宝商户信息列表
 *
 * @author egan
 * <pre>
 *                 email egzosn@gmail.com
 *                 date   2019/4/6 14:30.
 *                 </pre>
 */
public class PayPalV2MerchantDetails extends PayPalConfigStorage implements PaymentPlatformMerchantDetails, PaymentPlatformServiceAdapter, PayConfigurerAdapter<InMemoryMerchantDetailsServiceBuilder> {

    private String detailsId;

    /**
     * 商户对应的支付服务
     */
    private volatile PayService payService;
    /**
     * 商户平台
     */
    private PaymentPlatform platform;

    private InMemoryMerchantDetailsServiceBuilder builder;
    /**
     * HTTP请求配置
     */
    private HttpConfigStorage httpConfigStorage;

    /**
     * 外部调用者使用，链式的做法
     *
     * @return 返回对应外部调用者
     */
    @Override
    public InMemoryMerchantDetailsServiceBuilder and() {
        initService();
        return getBuilder();
    }

    /**
     * 获取构建器
     *
     * @return 构建器
     */
    @Override
    public InMemoryMerchantDetailsServiceBuilder getBuilder() {
        return builder;
    }

    public PayPalV2MerchantDetails(InMemoryMerchantDetailsServiceBuilder builder) {
        this();
        this.builder = builder;
    }

    public PayPalV2MerchantDetails() {
        String platformName = PaypalV2PaymentPlatform.platformName;
        setPayType(platformName);
        platform = PaymentPlatforms.getPaymentPlatform(platformName);
    }

    /**
     * 获取支付平台
     *
     * @return 支付平台
     */
    @Override
    public PaymentPlatform getPaymentPlatform() {
        return platform;
    }

    /**
     * 初始化服务
     *
     * @return 支付商户服务适配器
     */
    @Override
    public PaymentPlatformServiceAdapter initService() {
        if (null == payService) {
            payService = platform.getPayService(this, getHttpConfigStorage());
        }
        return this;
    }

    /**
     * 获取支付平台对应的支付服务
     *
     * @return 支付服务
     */
    @Override
    public PayService getPayService() {
        return payService;
    }

    /**
     * 获取HTTP请求配置
     *
     * @return HTTP请求配置
     */
    @Override
    public HttpConfigStorage getHttpConfigStorage() {
        return httpConfigStorage;
    }

    public PayPalV2MerchantDetails httpConfigStorage(HttpConfigStorage httpConfigStorage) {
        this.httpConfigStorage = httpConfigStorage;
        return this;
    }

    /**
     * 获取支付商户id
     *
     * @return 支付商户id
     */
    @Override
    public String getDetailsId() {
        return detailsId;
    }

    public PayPalV2MerchantDetails detailsId(String detailsId) {
        this.detailsId = detailsId;
        return this;
    }

    public PayPalV2MerchantDetails signType(String signType) {
        setSignType(signType);
        return this;
    }

    public PayPalV2MerchantDetails inputCharset(String inputCharset) {
        setInputCharset(inputCharset);
        return this;
    }

    public PayPalV2MerchantDetails test(boolean test) {
        setTest(test);
        return this;
    }

    public PayPalV2MerchantDetails clientID(String clientID) {
        setClientID(clientID);
        return this;
    }

    public PayPalV2MerchantDetails clientSecret(String clientSecret) {
        setClientSecret(clientSecret);
        return this;
    }

    public PayPalV2MerchantDetails cancelUrl(String cancelUrl) {
        setNotifyUrl(cancelUrl);
        return this;
    }

    public PayPalV2MerchantDetails returnUrl(String returnUrl) {
        setReturnUrl(returnUrl);
        return this;
    }
}
