package com.egzosn.pay.spring.boot.autoconfigue;

import com.egzosn.pay.spring.boot.core.MerchantPayServiceManager;
import com.egzosn.pay.spring.boot.core.configurers.DefalutPayMessageConfigurer;
import com.egzosn.pay.spring.boot.core.configurers.MerchantDetailsServiceConfigurer;
import com.egzosn.pay.spring.boot.core.configurers.PayMessageConfigurer;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
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


    @Autowired
    @Order
    public void loadPaymentPlatforms(List<PaymentPlatform> platforms){
        for (PaymentPlatform platform : platforms){
            PaymentPlatforms.loadPaymentPlatform(platform);
        }
    }


    @Bean
    @ConditionalOnMissingBean(MerchantDetailsServiceConfigurer.class)
    public MerchantDetailsServiceConfigurer detailsServiceConfigurer(){
        return new MerchantDetailsServiceConfigurer();
    }


    @Bean
    @ConditionalOnMissingBean(MerchantPayServiceManager.class)
    public MerchantPayServiceManager payServiceManager(){
        return new MerchantPayServiceManager();
    }


    @Bean
    @ConditionalOnMissingBean(PayMessageConfigurer.class)
    public PayMessageConfigurer messageHandlerConfigurer(){
        return new DefalutPayMessageConfigurer();
    }


}
