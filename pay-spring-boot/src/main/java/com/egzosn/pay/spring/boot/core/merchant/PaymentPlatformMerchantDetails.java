package com.egzosn.pay.spring.boot.core.merchant;

/**
 * 支付平台商户列表
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date 2019/4/2 17:28.
 *         </pre>
 */
public interface PaymentPlatformMerchantDetails extends MerchantDetails{

    /**
     * 获取支付平台
     * @return 支付平台
     */
    PaymentPlatform getPaymentPlatform();
}
