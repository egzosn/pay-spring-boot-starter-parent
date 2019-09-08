package com.egzosn.pay.spring.boot.core.builders;

import com.egzosn.pay.spring.boot.core.PayBuilder;
import com.egzosn.pay.spring.boot.core.configurers.PayMessageConfigurer;
import com.egzosn.pay.spring.boot.core.merchant.MerchantDetailsService;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 *
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date  2019/5/6 19:36.
 *         </pre>
 */
public class MerchantDetailsServiceBuilder implements PayBuilder<MerchantDetailsService> {
    protected PayMessageConfigurer configurer;

    public static final InMemoryMerchantDetailsServiceBuilder inMemory(){
        return new InMemoryMerchantDetailsServiceBuilder();
    }

    public static final JdbcMerchantDetailsServiceBuilder jdbc(){
        return new JdbcMerchantDetailsServiceBuilder();
    }
    public static final JdbcMerchantDetailsServiceBuilder jdbc(DataSource source){
        return new JdbcMerchantDetailsServiceBuilder(source);
    }
    public static final JdbcMerchantDetailsServiceBuilder jdbc(JdbcTemplate jdbcTemplate){
        return new JdbcMerchantDetailsServiceBuilder(jdbcTemplate);
    }

    /**
     * 构建对象并返回它或null。
     *
     * @return 如果实现允许，则要构建的对象或null。
     * @throws Exception 如果在构建对象时发生错误
     */
    @Override
    public MerchantDetailsService build() throws Exception {
        return performBuild();
    }

    /**
     * 开始构建
     * @return 商户列表服务
     */
    protected  MerchantDetailsService performBuild(){
        throw new UnsupportedOperationException("无法构建商家服务(需要使用inMemory()或jdbc())");
    }

    public void setConfigurer(PayMessageConfigurer configurer) {
        this.configurer = configurer;
    }
}
