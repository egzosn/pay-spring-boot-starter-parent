package com.egzosn.pay.spring.boot.common.config;

/**
 * 支付配置
 * @param <O>
 * @param <B>
 */
public interface PayConfigurer<O, B extends PayBuilder<O>> {

    void init(B var1) throws Exception;


    void configure(B var1) throws Exception;
}
