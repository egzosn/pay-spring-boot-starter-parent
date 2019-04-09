package com.egzosn.pay.spring.boot.common.config;

/**
 *
 * @param <B>
 */
public interface PayBuilder<B> {

    B build() throws Exception;
}
