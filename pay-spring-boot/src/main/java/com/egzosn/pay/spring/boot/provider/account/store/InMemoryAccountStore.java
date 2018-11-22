package com.egzosn.pay.spring.boot.provider.account.store;


import com.egzosn.pay.common.api.PayConfigStorage;
import com.egzosn.pay.spring.boot.provider.account.AccountStore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 内存型支付账户(商户)存储器
 *
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date 2018-11-22 17:18:03
 *         </pre>
 */
public class InMemoryAccountStore implements AccountStore {

    Map<String, PayConfigStorage> accountStore = new ConcurrentHashMap<>();



    /**
     * 根据支付账户id（商户表中的唯一标识） 获取支付账户配置
     *
     * @param payAccountId 支付账户id
     * @return 支付账户配置
     */
    @Override
    public PayConfigStorage getPayConfigStorage(String payAccountId) {
        return accountStore.get(payAccountId);
    }

}
