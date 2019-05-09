package com.egzosn.pay.spring.boot.core.configurers;


import com.egzosn.pay.spring.boot.core.PayConfigurerAdapter;
import com.egzosn.pay.spring.boot.core.builders.InMemoryMerchantDetailsServiceBuilder;
import com.egzosn.pay.spring.boot.core.builders.JdbcMerchantDetailsServiceBuilder;
import com.egzosn.pay.spring.boot.core.builders.MerchantDetailsServiceBuilder;

import javax.sql.DataSource;

/**
 * 商户列表服务配置
 *
 * @author egan
 *         <pre>
 *                 email egzosn@gmail.com
 *
 *                 date 2019/5/8 19:54.
 *                 </pre>
 */
public class MerchantDetailsServiceConfigurer implements PayConfigurerAdapter<MerchantDetailsServiceBuilder> {

    private MerchantDetailsServiceBuilder builder;

    public void setBuilder(MerchantDetailsServiceBuilder builder) {
        this.builder = builder;
    }

    public InMemoryMerchantDetailsServiceBuilder inMemory() {

        InMemoryMerchantDetailsServiceBuilder builder = MerchantDetailsServiceBuilder.inMemory();
        setBuilder(builder);
        return builder;
    }

    public JdbcMerchantDetailsServiceBuilder jdbc() {
        JdbcMerchantDetailsServiceBuilder builder = MerchantDetailsServiceBuilder.jdbc();
        setBuilder(builder);
        return builder;
    }

    public JdbcMerchantDetailsServiceBuilder jdbc(DataSource source) {
        JdbcMerchantDetailsServiceBuilder builder = MerchantDetailsServiceBuilder.jdbc(source);
        setBuilder(builder);
        return builder;
    }


    /**
     * 外部调用者使用，链式的做法
     *
     * @return 返回对应外部调用者
     */
    @Override
    public MerchantDetailsServiceBuilder and() {
        return getBuilder();
    }

    /**
     * 获取构建器
     *
     * @return 构建器
     */
    @Override
    public MerchantDetailsServiceBuilder getBuilder() {
        return builder;
    }
}
