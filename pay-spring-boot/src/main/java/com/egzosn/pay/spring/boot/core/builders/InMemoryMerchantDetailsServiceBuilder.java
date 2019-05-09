package com.egzosn.pay.spring.boot.core.builders;

import com.egzosn.pay.spring.boot.core.merchant.MerchantDetails;
import com.egzosn.pay.spring.boot.core.merchant.MerchantDetailsService;
import com.egzosn.pay.spring.boot.core.merchant.bean.*;
import com.egzosn.pay.spring.boot.core.provider.InMemoryMerchantDetailsManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 内存型商户列表服务构建器
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date  2019/5/6 19:36.
 *         </pre>
 */
public class InMemoryMerchantDetailsServiceBuilder extends MerchantDetailsServiceBuilder {

    private Map<String, MerchantDetails> merchantDetails = new HashMap<String, MerchantDetails>();

    public void addMerchantDetails(MerchantDetails merchantDetail) {
        this.merchantDetails.put(merchantDetail.getDetailsId(), merchantDetail);
    }

    public AliMerchantDetails ali(){
        AliMerchantDetails details = new AliMerchantDetails(this);
        addMerchantDetails(details);
        return details;
    }

    public FuiouMerchantDetails fuiou(){
        FuiouMerchantDetails details = new FuiouMerchantDetails(this);
        addMerchantDetails(details);
        return details;
    }
    public PayoneerMerchantDetails payoneer(){
        PayoneerMerchantDetails details = new PayoneerMerchantDetails(this);
        addMerchantDetails(details);
        return details;
    }
    public PaypalMerchantDetails payPal(){
        PaypalMerchantDetails details = new PaypalMerchantDetails(this);
        addMerchantDetails(details);
        return details;
    }
    public UnionMerchantDetails union(){
        UnionMerchantDetails details = new UnionMerchantDetails(this);
        addMerchantDetails(details);
        return details;
    }
    public WxMerchantDetails wx(){
        WxMerchantDetails details = new WxMerchantDetails(this);
        addMerchantDetails(details);
        return details;
    }


    /**
     * 开始构建
     *
     * @return 商户列表服务
     */
    @Override
    protected MerchantDetailsService performBuild() {
        InMemoryMerchantDetailsManager merchantDetailsManager = new InMemoryMerchantDetailsManager(merchantDetails);
        return merchantDetailsManager;
    }
}
