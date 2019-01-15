package com.egzosn.pay.spring.boot.common.config;

public interface PayConfigurer<O, B extends PayBuilder<O>> {
    void init(B var1) throws Exception;

    void configure(B var1) throws Exception;
}
