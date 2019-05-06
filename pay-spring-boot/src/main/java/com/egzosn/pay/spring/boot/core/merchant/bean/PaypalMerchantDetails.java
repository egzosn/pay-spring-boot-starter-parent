package com.egzosn.pay.spring.boot.core.merchant.bean;

import com.egzosn.pay.paypal.api.PayPalConfigStorage;
import com.egzosn.pay.spring.boot.core.PayConfigurerAdapter;
import com.egzosn.pay.spring.boot.core.builders.InMemoryMerchantDetailsServiceBuilder;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformMerchantDetails;
import com.egzosn.pay.spring.boot.provider.merchant.platform.PaymentPlatforms;
import com.egzosn.pay.spring.boot.provider.merchant.platform.PaypalPaymentPlatform;

/**
 * 贝宝商户信息列表
 *
 * @author egan
 *         <pre>
 *                 email egzosn@gmail.com
 *                 date   2019/4/6 14:30.
 *                 </pre>
 */
public class PaypalMerchantDetails extends PayPalConfigStorage implements PaymentPlatformMerchantDetails, PayConfigurerAdapter<InMemoryMerchantDetailsServiceBuilder> {

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

    public PaypalMerchantDetails(InMemoryMerchantDetailsServiceBuilder builder) {
        this();
        this.builder = builder;
    }

    public PaypalMerchantDetails() {
        setPayType(PaypalPaymentPlatform.platformName);
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
}
