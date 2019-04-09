package com.egzosn.pay.spring.boot.common.config.builders;

import com.egzosn.pay.spring.boot.common.config.PayBuilder;
import com.egzosn.pay.spring.boot.common.config.PayConfigurerAdapter;
import com.egzosn.pay.spring.boot.core.merchant.MerchantDetailsService;

/**
 *
 * @author egan
* <pre>
*         email: egzosn@gmail.com
 *        date:2018-12-29 10:53:25
* </pre>
 */
public class MerchantDetailsServiceBuider<B extends MerchantDetailsServiceBuider<B>> extends
        PayConfigurerAdapter<MerchantDetailsService, B> implements PayBuilder<MerchantDetailsService> {
    @Override
    public MerchantDetailsService build() throws Exception {
        return null;
    }
}
