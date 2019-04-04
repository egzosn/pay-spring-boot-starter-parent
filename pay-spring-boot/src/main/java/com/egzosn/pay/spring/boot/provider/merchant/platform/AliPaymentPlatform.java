package com.egzosn.pay.spring.boot.provider.merchant.platform;

import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.bean.TransactionType;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;

/**
 * 支付宝支付平台
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date  2019/4/4 14:35.
 *         </pre>
 */
public  class AliPaymentPlatform implements PaymentPlatform {

    private static final String platformName = "aliPay";

    public static final PaymentPlatform PLATFORM = new AliPaymentPlatform();

    private AliPaymentPlatform() {
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

    @Override
    public TransactionType getTransactionType(String name) {
        return AliTransactionType.valueOf(name);
    }


}
