package com.egzosn.pay.spring.boot.core.provider;

import com.egzosn.pay.spring.boot.core.merchant.MerchantDetails;
import com.egzosn.pay.spring.boot.core.merchant.MerchantDetailsService;

/**
 * 商户列表管理器
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date  2019/4/2 14:44.
 *         </pre>
 */
public interface MerchantDetailsManager<T extends MerchantDetails> extends MerchantDetailsService {

    /**
     *  创建商户
     * @param merchant 商户信息
     */
    void createMerchant(T merchant);

    /**
     *  更新商户
     * @param merchant 商户信息
     */
    void updateMerchant(T merchant);
    /**
     *  删除商户
     * @param id 商户id
     */
    void deleteMerchant(String id);

    /**
     * 检查商户是否存在
     * @param id 商户id
     * @return 检查商户是否存在
     */
    boolean merchantExists(String id);


}
