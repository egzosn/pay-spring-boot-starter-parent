package com.egzosn.pay.spring.boot.core.merchant.bean;

import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.CertStoreType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.spring.boot.core.PayConfigurerAdapter;
import com.egzosn.pay.spring.boot.core.builders.InMemoryMerchantDetailsServiceBuilder;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformMerchantDetails;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformServiceAdapter;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.PaymentPlatforms;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.WxV3PaymentPlatform;
import com.egzosn.pay.wx.v3.api.WxPayConfigStorage;

/**
 * 支付宝商户信息列表
 *
 * @author egan
 * <pre>
 *                 email egzosn@gmail.com
 *                 date   2019/4/6 20:10.
 *                 </pre>
 */
public class WxV3MerchantDetails extends WxPayConfigStorage implements PaymentPlatformMerchantDetails, PaymentPlatformServiceAdapter, PayConfigurerAdapter<InMemoryMerchantDetailsServiceBuilder> {


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

    public WxV3MerchantDetails(InMemoryMerchantDetailsServiceBuilder builder) {
        this();
        this.builder = builder;
    }

    public WxV3MerchantDetails() {
        String platformName = WxV3PaymentPlatform.platformName;
        initPaymentPlatforms(platformName);
    }


    public WxV3MerchantDetails initPaymentPlatforms(String platformName) {
        setPayType(platformName);
        platform = PaymentPlatforms.getPaymentPlatform(platformName);
        return this;
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

    public WxV3MerchantDetails httpConfigStorage(HttpConfigStorage httpConfigStorage) {
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

    public WxV3MerchantDetails detailsId(String detailsId) {
        this.detailsId = detailsId;
        return this;
    }

    public WxV3MerchantDetails notifyUrl(String notifyUrl) {
        setNotifyUrl(notifyUrl);
        return this;
    }

    public WxV3MerchantDetails returnUrl(String returnUrl) {
        setReturnUrl(returnUrl);
        return this;
    }

    public WxV3MerchantDetails signType(String signType) {
        setSignType(signType);
        return this;
    }

    public WxV3MerchantDetails inputCharset(String inputCharset) {
        setInputCharset(inputCharset);
        return this;
    }

    public WxV3MerchantDetails test(boolean test) {
        setTest(test);
        return this;
    }


    public WxV3MerchantDetails appId(String appId) {
        setAppId(appId);
        return this;
    }

    public WxV3MerchantDetails v3ApiKey(String v3ApiKey) {
        setV3ApiKey(v3ApiKey);
        return this;
    }

    public WxV3MerchantDetails apiKey(String apiKey) {
        setApiKey(apiKey);
        return this;
    }

    public WxV3MerchantDetails keyPublic(String keyPublic) {
        setKeyPublic(keyPublic);
        return this;
    }

    public WxV3MerchantDetails mchId(String mchId) {
        setMchId(mchId);
        return this;
    }

    public WxV3MerchantDetails subAppId(String subAppId) {
        setSubAppId(subAppId);
        return this;
    }

    public WxV3MerchantDetails subMchId(String subMchId) {
        setSubMchId(subMchId);
        return this;
    }


    public WxV3MerchantDetails apiClientKeyP12(Object apiClientKeyP12) {
        setApiClientKeyP12(apiClientKeyP12);
        return this;
    }

    public WxV3MerchantDetails certStoreType(CertStoreType certStoreType) {
        setCertStoreType(certStoreType);
        return this;
    }


}
