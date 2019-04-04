package com.egzosn.pay.spring.boot.provider.merchant.platform;

import com.egzosn.pay.common.bean.TransactionType;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.wx.bean.WxTransactionType;

/**
 * 支付宝支付平台
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date  2019/4/4 14:35.
 *         </pre>
 */
public  class WxPaymentPlatform implements PaymentPlatform {

    private static final String platform = "wxPay";

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
        return platform;
    }

    @Override
    public TransactionType getTransactionType(String name) {
        return WxTransactionType.valueOf(name);
    }


}
