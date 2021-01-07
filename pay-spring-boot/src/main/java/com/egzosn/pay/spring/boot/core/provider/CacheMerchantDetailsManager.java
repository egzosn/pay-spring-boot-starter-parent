package com.egzosn.pay.spring.boot.core.provider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.egzosn.pay.spring.boot.core.configurers.PayMessageConfigurer;
import com.egzosn.pay.spring.boot.core.merchant.MerchantNotFoundException;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformMerchantDetails;

/**
 * 缓存支付账户(商户)存储器
 *
 * @author egan
 *         <pre>
 * email egzosn@gmail.com
 * date 2021/1/7
 * </pre>
 */
public class CacheMerchantDetailsManager implements MerchantDetailsManager<PaymentPlatformMerchantDetails> {

    private Map<String, PaymentPlatformMerchantDetails> merchantDetails = new HashMap<String, PaymentPlatformMerchantDetails>();

    private MerchantDetailsManager<PaymentPlatformMerchantDetails> delegate;

    public CacheMerchantDetailsManager(MerchantDetailsManager<PaymentPlatformMerchantDetails> delegate) {
        this.delegate = delegate;
    }

    /**
     * 创建商户
     *
     * @param merchant 商户信息
     */
    @Override
    public void createMerchant(PaymentPlatformMerchantDetails merchant) {
        Assert.isTrue(!merchantExists(merchant.getDetailsId()), "商户信息已存在");
        merchantDetails.put(merchant.getDetailsId(), merchant);
        delegate.createMerchant(merchant);
    }

    /**
     * 创建商户
     *
     * @param merchants 商户信息
     */
    @Override
    public void createMerchant(Collection<PaymentPlatformMerchantDetails> merchants) {
        for (PaymentPlatformMerchantDetails merchant : merchants) {
            this.createMerchant(merchant);
        }
    }

    /**
     * 更新商户
     *
     * @param merchant 商户信息
     */
    @Override
    public void updateMerchant(PaymentPlatformMerchantDetails merchant) {
        Assert.isTrue(merchantExists(merchant.getDetailsId()), "商户信息不存在");
        merchantDetails.put(merchant.getDetailsId(), merchant);
        delegate.updateMerchant(merchant);


    }

    /**
     * 删除商户
     *
     * @param id 商户id
     */
    @Override
    public void deleteMerchant(String id) {
        merchantDetails.remove(id);
        delegate.deleteMerchant(id);
    }

    /**
     * 检查商户是否存在
     *
     * @param id 商户id
     * @return 检查商户是否存在
     */
    @Override
    public boolean merchantExists(String id) {
        boolean exist = merchantDetails.containsKey(id);
        if (exist) {
            return exist;
        }
        return delegate.merchantExists(id);

    }

    /**
     * 设置支付消息配置中心
     *
     * @param configurer 配置
     */
    @Override
    public void setPayMessageConfigurer(PayMessageConfigurer configurer) {
        delegate.setPayMessageConfigurer(configurer);
    }

    /**
     * 通过支付商户id加载对应的商户信息列表
     *
     * @param merchantId 支付商户id
     * @return 商户信息列表
     */
    @Override
    public PaymentPlatformMerchantDetails loadMerchantByMerchantId(String merchantId) {
        PaymentPlatformMerchantDetails details = merchantDetails.get(merchantId);
        if (null != details) {
            return details;
        }
        details = delegate.loadMerchantByMerchantId(merchantId);
        if (null == details){
            throw new MerchantNotFoundException(merchantId);
        }
        merchantDetails.put(details.getDetailsId(), details);
        return details;
    }
}
