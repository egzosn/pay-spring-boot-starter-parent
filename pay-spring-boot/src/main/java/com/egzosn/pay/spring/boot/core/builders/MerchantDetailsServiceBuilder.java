package com.egzosn.pay.spring.boot.core.builders;

import com.egzosn.pay.spring.boot.core.PayBuilder;
import com.egzosn.pay.spring.boot.core.merchant.MerchantDetailsService;

/**
 *
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date  2019/5/6 19:36.
 *         </pre>
 */
public abstract class MerchantDetailsServiceBuilder implements PayBuilder<MerchantDetailsService> {

    public static final InMemoryMerchantDetailsServiceBuilder inMemory(){
        return new InMemoryMerchantDetailsServiceBuilder();
    }

    /**
     * 构建对象并返回它或null。
     *
     * @return 如果实现允许，则要构建的对象或null。
     * @throws Exception 如果在构建对象时发生错误
     */
    @Override
    public MerchantDetailsService build() throws Exception {
        return performBuild();
    }

    /**
     * 开始构建
     * @return 商户列表服务
     */
    protected abstract MerchantDetailsService performBuild();
}
