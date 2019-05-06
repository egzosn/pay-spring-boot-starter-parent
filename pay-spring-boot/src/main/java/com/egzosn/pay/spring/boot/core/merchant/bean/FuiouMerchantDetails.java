package com.egzosn.pay.spring.boot.core.merchant.bean;

import com.egzosn.pay.fuiou.api.FuiouPayConfigStorage;
import com.egzosn.pay.spring.boot.core.PayConfigurerAdapter;
import com.egzosn.pay.spring.boot.core.builders.InMemoryMerchantDetailsServiceBuilder;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformMerchantDetails;
import com.egzosn.pay.spring.boot.provider.merchant.platform.FuiouPaymentPlatform;
import com.egzosn.pay.spring.boot.provider.merchant.platform.PaymentPlatforms;

/**
 * 富友商户信息列表
 *
 * @author egan
 *         <pre>
 *                 email egzosn@gmail.com
 *                 date   2019/4/6 14:30.
 *                 </pre>
 */
public class FuiouMerchantDetails extends FuiouPayConfigStorage implements PaymentPlatformMerchantDetails, PayConfigurerAdapter<InMemoryMerchantDetailsServiceBuilder> {

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

    public FuiouMerchantDetails(InMemoryMerchantDetailsServiceBuilder builder) {
        this();
        this.builder = builder;
    }

    public FuiouMerchantDetails() {
        setPayType(FuiouPaymentPlatform.platformName);
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


    public FuiouMerchantDetails detailsId(String detailsId) {
        this.detailsId = detailsId;
        return this;
    }

    public FuiouMerchantDetails notifyUrl(String notifyUrl) {
        setNotifyUrl(notifyUrl);
        return this;
    }

    public FuiouMerchantDetails returnUrl(String returnUrl) {
        setReturnUrl(returnUrl);
        return this;
    }

    public FuiouMerchantDetails signType(String signType) {
        setSignType(signType);
        return this;
    }

    public FuiouMerchantDetails inputCharset(String inputCharset) {
        setInputCharset("utf-8");
        return this;
    }

    public FuiouMerchantDetails test(boolean test) {
        setTest(test);
        return this;
    }

    public FuiouMerchantDetails mchntCd(String mchntCd) {
        setMchntCd(mchntCd);
        return this;
    }

    public FuiouMerchantDetails keyPrivate(String keyPrivate) {
        setKeyPrivate(keyPrivate);
        return this;
    }

    public FuiouMerchantDetails keyPublic(String keyPublic) {
        setKeyPublic(keyPublic);
        return this;
    }


}
