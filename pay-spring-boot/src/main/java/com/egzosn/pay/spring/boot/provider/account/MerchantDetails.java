package com.egzosn.pay.spring.boot.provider.account;

import com.egzosn.pay.common.api.PayConfigStorage;

import java.io.Serializable;

/**
 *
 * 商户信息列表
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date 2018/12/29 11:03.
 *         </pre>
 */
public interface MerchantDetails  extends PayConfigStorage, Serializable {


    String getPayMerchantAccId();
}
