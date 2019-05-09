package com.egzosn.pay.spring.boot.core.builders;

import com.egzosn.pay.spring.boot.core.merchant.MerchantDetailsService;
import com.egzosn.pay.spring.boot.core.provider.InMemoryMerchantDetailsManager;

import javax.sql.DataSource;

/**
 * 内存型商户列表服务构建器
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date  2019/5/6 19:36.
 *         </pre>
 */
public class JdbcMerchantDetailsServiceBuilder extends MerchantDetailsServiceBuilder {


    public JdbcMerchantDetailsServiceBuilder(DataSource source) {

    }

    public JdbcMerchantDetailsServiceBuilder() {
    }

    /**
     * 开始构建
     *
     * @return 商户列表服务
     */
    @Override
    protected MerchantDetailsService performBuild() {

        return null;
    }
}
