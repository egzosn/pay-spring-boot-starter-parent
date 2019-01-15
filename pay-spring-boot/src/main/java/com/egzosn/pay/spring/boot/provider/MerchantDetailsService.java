package com.egzosn.pay.spring.boot.provider;

import com.egzosn.pay.common.api.PayConfigStorage;
import com.egzosn.pay.spring.boot.provider.account.MerchantDetails;

/**
 *  提供给客户端获取商户列表信息的服务
 *
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date 2018-12-29 10:53:08
 *         </pre>
 */
public interface MerchantDetailsService {

    MerchantDetails loadMerchantByPayMerchantId(String payMerchantAccId);

}
