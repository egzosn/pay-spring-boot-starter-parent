package com.egzosn.pay.spring.boot.provider.account;

import com.egzosn.pay.common.api.PayConfigStorage;

/**
 * 支付账户(商户)存储器
 *
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date 2018-11-21 15:22:06
 *         </pre>
 */
public interface AccountStore {


    /**
     *  根据支付账户id（商户表中的唯一标识） 获取支付账户配置
     *  @param payAccountId  支付账户id
     * @return 支付账户配置
     */
    PayConfigStorage getPayConfigStorage(String payAccountId);


}
