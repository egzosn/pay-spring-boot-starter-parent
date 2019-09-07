package com.egzosn.pay.spring.boot.core.provider;

import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.api.PayMessageInterceptor;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.PayMessage;
import com.egzosn.pay.spring.boot.core.configurers.PayMessageConfigurer;
import com.egzosn.pay.spring.boot.core.merchant.MerchantNotFoundException;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformMerchantDetails;
import com.egzosn.pay.spring.boot.core.utils.SpringContextUtil;
import org.springframework.util.Assert;

import java.util.*;


/**
 * 内存型支付账户(商户)存储器
 *
 * @author egan
 *         <pre>
 *                 email egzosn@gmail.com
 *                 date 2018-11-22 17:18:03
 *           </pre>
 */
public class InMemoryMerchantDetailsManager implements MerchantDetailsManager<PaymentPlatformMerchantDetails> {

    private Map<String, PaymentPlatformMerchantDetails> merchantDetails = new HashMap<String, PaymentPlatformMerchantDetails>();


    public InMemoryMerchantDetailsManager() {
    }

    public InMemoryMerchantDetailsManager(Collection<PaymentPlatformMerchantDetails> merchantDetails) {
        for (PaymentPlatformMerchantDetails merchant : merchantDetails) {
            createMerchant(merchant);
        }
    }

    public InMemoryMerchantDetailsManager(Map<String, PaymentPlatformMerchantDetails> merchantDetails) {
        this.merchantDetails = merchantDetails;
    }

    /**
     * 这个暂时还未定义，后期版本进行
     * @param merchants 属性配置
     */
    public InMemoryMerchantDetailsManager(Properties merchants) {

    }


    /**
     * 创建商户
     *
     * @param merchant 商户信息
     */
    @Override
    public void createMerchant(PaymentPlatformMerchantDetails merchant) {

        Assert.isTrue(!merchantExists(merchant.getDetailsId()), "商户信息已存在");
        InMemoryMerchantDetailsManager.setPayMessageConfigurer(merchant.getPayService(), merchant);
        merchantDetails.put(merchant.getDetailsId(), merchant);

    }
    protected static void setPayMessageConfigurer(PayService payService, PaymentPlatformMerchantDetails details){
        PayMessageConfigurer configurer = SpringContextUtil.getBean(PayMessageConfigurer.class);
        PayMessageHandler<PayMessage, PayService> handler = configurer.getHandler(details.getPaymentPlatform());
        if (null != handler){
            payService.setPayMessageHandler(handler);
        }

        List<PayMessageInterceptor<PayMessage, PayService>> interceptors = configurer.getInterceptor(details.getPaymentPlatform());
        if (null == interceptors || interceptors.isEmpty()){
            return;
        }
        for (PayMessageInterceptor<PayMessage, PayService> interceptor : interceptors){
            payService.addPayMessageInterceptor(interceptor);
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
     *
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
    public PaymentPlatformMerchantDetails loadMerchantByMerchantId(String merchantId) {

        PaymentPlatformMerchantDetails details = merchantDetails.get(merchantId);
        if (null == details) {
            throw new MerchantNotFoundException(merchantId);
        }
        return details;
    }
}
