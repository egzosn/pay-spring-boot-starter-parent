package com.egzosn.pay.spring.boot.core.builders;

import com.egzosn.pay.spring.boot.core.merchant.MerchantDetailsService;
import com.egzosn.pay.spring.boot.core.provider.InMemoryMerchantDetailsManager;
import com.egzosn.pay.spring.boot.core.provider.JdbcMerchantDetailsManager;
import org.springframework.jdbc.core.JdbcTemplate;

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

    private JdbcTemplate jdbcTemplate;
    public JdbcMerchantDetailsServiceBuilder(DataSource source) {
        jdbcTemplate = new JdbcTemplate(source);
    }

    public JdbcMerchantDetailsServiceBuilder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcMerchantDetailsServiceBuilder() {
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        if (null != this.jdbcTemplate){
            return;
        }
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 开始构建
     *
     * @return 商户列表服务·
     */
    @Override
    protected MerchantDetailsService performBuild() {

        return new JdbcMerchantDetailsManager(jdbcTemplate);
    }
}
