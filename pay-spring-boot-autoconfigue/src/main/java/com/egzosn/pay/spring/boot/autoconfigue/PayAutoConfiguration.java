package com.egzosn.pay.spring.boot.autoconfigue;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.fuiou.api.FuiouPayConfigStorage;
import com.egzosn.pay.payoneer.api.PayoneerConfigStorage;
import com.egzosn.pay.paypal.api.PayPalConfigStorage;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.provider.merchant.platform.*;
import com.egzosn.pay.union.api.UnionPayConfigStorage;
import com.egzosn.pay.wx.api.WxPayConfigStorage;
import com.egzosn.pay.wx.youdian.api.WxYouDianPayConfigStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 支付转载配置
 *
 * @author egan
 *         <pre>
 *                 email egzosn@gmail.com
 *                 date  2018/11/21.
 *                 </pre>
 */
@Configuration
public class PayAutoConfiguration {

    @Bean(AliPaymentPlatform.platformName)
    @ConditionalOnClass({AliPayConfigStorage.class})
    public PaymentPlatform aliPaymentPlatform() {
        PaymentPlatform platform = AliPaymentPlatform.PLATFORM;
        PaymentPlatforms.loadPaymentPlatform(platform);
        return platform;
    }

    @Bean(FuiouPaymentPlatform.platformName)
    @ConditionalOnClass({FuiouPayConfigStorage.class})
    public PaymentPlatform fuiouPaymentPlatform() {
        PaymentPlatform platform = FuiouPaymentPlatform.PLATFORM;
        PaymentPlatforms.loadPaymentPlatform(platform);
        return platform;
    }

    @Bean(PayoneerPaymentPlatform.platformName)
    @ConditionalOnClass({PayoneerConfigStorage.class})
    public PaymentPlatform payoneerPaymentPlatform() {
        PaymentPlatform platform = PayoneerPaymentPlatform.PLATFORM;
        PaymentPlatforms.loadPaymentPlatform(platform);
        return platform;
    }

    @Bean(PaypalPaymentPlatform.platformName)
    @ConditionalOnClass({PayPalConfigStorage.class})
    public PaymentPlatform paypalPaymentPlatform() {
        PaymentPlatform platform = PaypalPaymentPlatform.PLATFORM;
        PaymentPlatforms.loadPaymentPlatform(platform);
        return platform;
    }

    @Bean(UnionPaymentPlatform.platformName)
    @ConditionalOnClass({UnionPayConfigStorage.class})
    public PaymentPlatform unionPaymentPlatform() {
        PaymentPlatform platform = UnionPaymentPlatform.PLATFORM;
        PaymentPlatforms.loadPaymentPlatform(platform);
        return platform;
    }

    @Bean(WxPaymentPlatform.platformName)
    @ConditionalOnClass({WxPayConfigStorage.class})
    public PaymentPlatform wxPaymentPlatform() {
        PaymentPlatform platform = WxPaymentPlatform.PLATFORM;
        PaymentPlatforms.loadPaymentPlatform(platform);
        return platform;
    }

    @Bean(YoudianPaymentPlatform.platformName)
    @ConditionalOnClass({WxYouDianPayConfigStorage.class})
    public PaymentPlatform youdianPaymentPlatform() {
        PaymentPlatform platform = YoudianPaymentPlatform.PLATFORM;
        PaymentPlatforms.loadPaymentPlatform(platform);
        return platform;
    }
}
