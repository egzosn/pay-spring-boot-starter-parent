package com.egzosn.pay.spring.boot.demo.config;

import com.egzosn.pay.spring.boot.core.PayServiceConfigurer;
import com.egzosn.pay.spring.boot.core.configurers.MerchantDetailsServiceConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 支付服务配置
 * @author egan
 *         email egzosn@gmail.com
 *         date 2019/5/26.19:25
 */
@Configuration
public class MerchantPayServiceConfigurer implements PayServiceConfigurer {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 商户配置
     *
     * @param merchants 商户配置
     * @throws Exception 异常
     */
    @Override
    public void configure(MerchantDetailsServiceConfigurer merchants) throws Exception {
        merchants.jdbc(jdbcTemplate);
    }
}
