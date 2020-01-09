package com.egzosn.pay.spring.boot.core.builders;

import com.egzosn.pay.spring.boot.core.configurers.PayMessageConfigurer;
import com.egzosn.pay.spring.boot.core.merchant.MerchantDetailsService;
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
        setJdbcTemplate(new JdbcTemplate(source));
    }

    public JdbcMerchantDetailsServiceBuilder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcMerchantDetailsServiceBuilder() {
    }


    /**
     *  设置jdbc 模版
     * @param jdbcTemplate jdbcTemplate
     * @return 当前
     */
    public JdbcMerchantDetailsServiceBuilder template(JdbcTemplate jdbcTemplate){
        setJdbcTemplate(jdbcTemplate);
        return this;
    }


    /**
     *  设置数据源
     * @param source 数据源
     * @return 当前
     */
    public JdbcMerchantDetailsServiceBuilder dataSource(DataSource source){
        setJdbcTemplate(new JdbcTemplate(source));
        return this;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
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

        JdbcMerchantDetailsManager manager = new JdbcMerchantDetailsManager(jdbcTemplate);
        manager.setPayMessageConfigurer(configurer);
        return manager;
    }
}
