package com.egzosn.pay.spring.boot.core.merchant.bean;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformMerchantDetails;
import com.egzosn.pay.spring.boot.provider.merchant.platform.AliPaymentPlatform;

/**
 * 支付宝商户信息列表
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date   2019/4/4 14:30.
 *         </pre>
 */
public class AliMerchantDetails extends AliPayConfigStorage implements  PaymentPlatformMerchantDetails {

    private String detailsId;

    /**
     * 获取支付平台
     *
     * @return 支付平台
     */
    @Override
    public PaymentPlatform getPaymentPlatform() {
        return AliPaymentPlatform.PLATFORM;
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
