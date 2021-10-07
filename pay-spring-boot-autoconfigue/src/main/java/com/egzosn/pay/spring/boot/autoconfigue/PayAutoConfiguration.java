package com.egzosn.pay.spring.boot.autoconfigue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.egzosn.pay.spring.boot.core.MerchantPayServiceManager;
import com.egzosn.pay.spring.boot.core.PayServiceConfigurer;
import com.egzosn.pay.spring.boot.core.PayServiceManager;
import com.egzosn.pay.spring.boot.core.configurers.DefalutPayMessageConfigurer;
import com.egzosn.pay.spring.boot.core.configurers.MerchantDetailsServiceConfigurer;
import com.egzosn.pay.spring.boot.core.configurers.PayMessageConfigurer;
import com.egzosn.pay.spring.boot.core.merchant.MerchantDetailsService;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.AliPaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.FuiouPaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.PaymentPlatforms;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.PayoneerPaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.PaypalPaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.PaypalV2PaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.UnionPaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.WxPaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.WxV3CombinePaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.WxV3PaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.WxV3ProfitSharingPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.YoudianPaymentPlatform;

/**
 * 支付转载配置
 *
 * @author egan
 * <pre>
 *  email egzosn@gmail.com
 *  date  2018/11/21.
 *  </pre>
 */
@Configuration
@ImportAutoConfiguration({AliPaymentPlatform.class, FuiouPaymentPlatform.class, PayoneerPaymentPlatform.class, PaypalPaymentPlatform.class, UnionPaymentPlatform.class, WxPaymentPlatform.class, WxV3PaymentPlatform.class, WxV3ProfitSharingPlatform.class, WxV3CombinePaymentPlatform.class, PaypalV2PaymentPlatform.class, YoudianPaymentPlatform.class})
public class PayAutoConfiguration {


    @Autowired(required = false)
    @Order
    public void loadPaymentPlatforms(List<PaymentPlatform> platforms) {
        for (PaymentPlatform platform : platforms) {
            PaymentPlatforms.loadPaymentPlatform(platform);
        }
    }


    @Bean
    @ConditionalOnMissingBean(MerchantDetailsServiceConfigurer.class)
    @ConditionalOnBean(PayServiceConfigurer.class)
    public MerchantDetailsServiceConfigurer detailsServiceConfigurer() {
        return new MerchantDetailsServiceConfigurer();
    }

    @Bean
    @ConditionalOnMissingBean(MerchantDetailsService.class)
    @ConditionalOnBean(PayServiceConfigurer.class)
    protected MerchantDetailsService configure(PayServiceConfigurer configurer, MerchantDetailsServiceConfigurer merchantDetails, PayMessageConfigurer payMessageConfigurer) {
        configurer.configure(merchantDetails);
        configurer.configure(payMessageConfigurer);
        MerchantDetailsService detailsService = merchantDetails.getBuilder().build();
        return detailsService;
    }

    @Bean
    @Order
    @ConditionalOnBean(MerchantDetailsService.class)
    @ConditionalOnMissingBean(PayServiceManager.class)
    public PayServiceManager payServiceManager() {
        return new MerchantPayServiceManager();
    }


    @Bean
    @ConditionalOnMissingBean(PayMessageConfigurer.class)
    public PayMessageConfigurer messageHandlerConfigurer() {
        return new DefalutPayMessageConfigurer();
    }


}
