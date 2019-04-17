package com.egzosn.pay.spring.boot.autoconfigue;

import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.provider.merchant.platform.*;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * 支付转载配置
 *
 * @author egan
 *         <pre>
 *                         email egzosn@gmail.com
 *                         date  2018/11/21.
 *                         </pre>
 */
@Configuration
@ImportAutoConfiguration({AliPaymentPlatform.class, FuiouPaymentPlatform.class, PayoneerPaymentPlatform.class, PaypalPaymentPlatform.class, UnionPaymentPlatform.class, WxPaymentPlatform.class, YoudianPaymentPlatform.class})
public class PayAutoConfiguration {


/*    @Bean(AliPaymentPlatform.platformName)
    @ConditionalOnMissingBean(AliPaymentPlatform.class)
    @ConditionalOnClass(name = {"com.egzosn.pay.ali.api.AliPayConfigStorage"})
    public PaymentPlatform aliPaymentPlatform() {
        return new AliPaymentPlatform();
    }*/
/*
    @Bean(FuiouPaymentPlatform.platformName)
    @ConditionalOnMissingBean(FuiouPaymentPlatform.class)
    @ConditionalOnClass({FuiouPayConfigStorage.class})
    public PaymentPlatform fuiouPaymentPlatform() {
        return new FuiouPaymentPlatform();
    }
*/

/*    @Bean(PayoneerPaymentPlatform.platformName)
    @ConditionalOnMissingBean(PayoneerPaymentPlatform.class)
    @ConditionalOnClass({PayoneerConfigStorage.class})
    public PaymentPlatform payoneerPaymentPlatform() {
        return new PayoneerPaymentPlatform();
    }*/

/*
    @Bean(PaypalPaymentPlatform.platformName)
    @ConditionalOnMissingBean(PaypalPaymentPlatform.class)
    @ConditionalOnClass({PayPalConfigStorage.class})
    public PaymentPlatform paypalPaymentPlatform() {
        return new PaypalPaymentPlatform();
    }
*/

/*    @Bean(UnionPaymentPlatform.platformName)
    @ConditionalOnMissingBean(UnionPaymentPlatform.class)
    @ConditionalOnClass({UnionPayConfigStorage.class})
    public PaymentPlatform unionPaymentPlatform() {
        return new UnionPaymentPlatform();
    }*/

/*    @Bean(WxPaymentPlatform.platformName)
    @ConditionalOnMissingBean(WxPaymentPlatform.class)
    @ConditionalOnClass({WxPayConfigStorage.class})
    public PaymentPlatform wxPaymentPlatform() {
        return new WxPaymentPlatform();
    }*/
/*
    @Bean(YoudianPaymentPlatform.platformName)
    @ConditionalOnMissingBean(YoudianPaymentPlatform.class)
    @ConditionalOnClass({WxYouDianPayConfigStorage.class})
    public PaymentPlatform youdianPaymentPlatform() {
        return new YoudianPaymentPlatform();
    }*/

    @Bean
    @Order
    public void/*List<PaymentPlatform>*/ loadPaymentPlatforms(List<PaymentPlatform> platforms){
        for (PaymentPlatform platform : platforms){
            PaymentPlatforms.loadPaymentPlatform(platform);
        }
//        return platforms;
    }
}
