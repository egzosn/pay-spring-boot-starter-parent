package com.egzosn.pay.spring.boot.core.merchant;

import com.egzosn.pay.common.bean.BasePayType;

/**
 * 支付平台
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date  2019/4/2 17:20.
 *         </pre>
 */
public interface PaymentPlatform extends BasePayType {

    /**
     * 获取商户平台
     * @return 商户平台
     */
    String getPlatform();

}
