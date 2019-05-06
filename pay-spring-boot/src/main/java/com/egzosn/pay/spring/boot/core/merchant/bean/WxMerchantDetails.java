package com.egzosn.pay.spring.boot.core.merchant.bean;

import com.egzosn.pay.spring.boot.core.PayConfigurerAdapter;
import com.egzosn.pay.spring.boot.core.builders.InMemoryMerchantDetailsServiceBuilder;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformMerchantDetails;
import com.egzosn.pay.spring.boot.provider.merchant.platform.AliPaymentPlatform;
import com.egzosn.pay.spring.boot.provider.merchant.platform.PaymentPlatforms;
import com.egzosn.pay.wx.api.WxPayConfigStorage;

/**
 * 支付宝商户信息列表
 *
 * @author egan
 *         <pre>
 *                 email egzosn@gmail.com
 *                 date   2019/4/6 20:10.
 *                 </pre>
 */
public class WxMerchantDetails extends WxPayConfigStorage implements PaymentPlatformMerchantDetails, PayConfigurerAdapter<InMemoryMerchantDetailsServiceBuilder> {


    private String detailsId;

    private InMemoryMerchantDetailsServiceBuilder builder;

    /**
     * 外部调用者使用，链式的做法
     *
     * @return 返回对应外部调用者
     */
    @Override
    public InMemoryMerchantDetailsServiceBuilder and() {

        return builder;
    }

    public WxMerchantDetails(InMemoryMerchantDetailsServiceBuilder builder) {
        this();
        this.builder = builder;
    }

    public WxMerchantDetails() {
        setPayType(AliPaymentPlatform.platformName);
    }

    /**
     * 获取支付平台
     *
     * @return 支付平台
     */
    @Override
    public PaymentPlatform getPaymentPlatform() {
        return PaymentPlatforms.getPaymentPlatform(getPayType());
    }

    public void setDetailsId(String detailsId) {
        this.detailsId = detailsId;
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

    public WxMerchantDetails detailsId(String detailsId) {
        this.detailsId = detailsId;
        return this;
    }

    public WxMerchantDetails notifyUrl(String notifyUrl) {
        setNotifyUrl(notifyUrl);
        return this;
    }

    public WxMerchantDetails returnUrl(String returnUrl) {
        setReturnUrl(returnUrl);
        return this;
    }

    public WxMerchantDetails signType(String signType) {
        setSignType(signType);
        return this;
    }

    public WxMerchantDetails inputCharset(String inputCharset) {
        setInputCharset("utf-8");
        return this;
    }

    public WxMerchantDetails test(boolean test) {
        setTest(test);
        return this;
    }



    public WxMerchantDetails appid(String appid) {
        setAppid(appid);
        return this;
    }

    public WxMerchantDetails secretKey(String secretKey) {
        setSecretKey(secretKey);
        return this;
    }

    public WxMerchantDetails keyPublic(String keyPublic) {
        setKeyPublic(keyPublic);
        return this;
    }

    public WxMerchantDetails mchId(String mchId) {
        setMchId(mchId);
        return this;
    }

    public WxMerchantDetails subAppid(String subAppid) {
        setSubAppid(subAppid);
        return this;
    }

    public WxMerchantDetails subMchId(String subMchId) {
        setSubMchId(subMchId);
        return this;
    }




}
