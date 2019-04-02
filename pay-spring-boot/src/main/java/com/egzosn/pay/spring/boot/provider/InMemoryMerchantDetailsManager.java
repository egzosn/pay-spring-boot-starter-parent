package com.egzosn.pay.spring.boot.provider;

import com.egzosn.pay.spring.boot.core.merchant.MerchantDetails;
import com.egzosn.pay.spring.boot.core.merchant.MerchantNotFoundException;
import org.springframework.util.Assert;

import java.util.*;


/**
 * 内存型支付账户(商户)存储器
 *
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date 2018-11-22 17:18:03
 *         </pre>
 */
public class InMemoryMerchantDetailsManager<T extends MerchantDetails> implements MerchantDetailsManager<T> {

    Map<String, T> merchantDetails = new HashMap<String, T>();


    public InMemoryMerchantDetailsManager() {
    }

    public InMemoryMerchantDetailsManager(Collection<T> merchantDetails) {
        for (T merchant : merchantDetails) {
            createMerchant(merchant);
        }
    }

    public InMemoryMerchantDetailsManager(Properties merchants) {

    }



    /**
     * 创建商户
     *
     * @param merchant 商户信息
     */
    @Override
    public void createMerchant(T merchant) {

        Assert.isTrue(!merchantExists(merchant.getMerchantId()), "商户信息已存在");

        merchantDetails.put(merchant.getMerchantId(), merchant);

    }

    /**
     * 更新商户
     *
     * @param merchant 商户信息
     */
    @Override
    public void updateMerchant(T merchant) {
        Assert.isTrue(merchantExists(merchant.getMerchantId()), "商户信息不存在");
        merchantDetails.put(merchant.getMerchantId(), merchant);
    }

    /**
     * 删除商户
     *
     * @param id 商户id
     */
    @Override
    public void deleteMerchant(String id) {
        merchantDetails.remove(id);
    }

    /**
     * 检查商户是否存在
     * @param id 商户id
     * @return 检查商户是否存在
     */
    @Override
    public boolean merchantExists(String id) {
        return merchantDetails.containsKey(id);
    }

    /**
     * 通过支付商户id加载对应的商户信息列表
     *
     * @param merchantId 支付商户id
     * @return 商户信息列表
     */
    @Override
    public T loadMerchantByMerchantId(String merchantId) {

        T details = merchantDetails.get(merchantId);
        if (null == details){
            throw new MerchantNotFoundException(merchantId);
        }
        return details;
    }
}
