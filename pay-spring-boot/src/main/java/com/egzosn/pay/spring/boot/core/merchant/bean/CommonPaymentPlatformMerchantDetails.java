package com.egzosn.pay.spring.boot.core.merchant.bean;

import com.egzosn.pay.common.api.BasePayConfigStorage;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformMerchantDetails;
import com.egzosn.pay.spring.boot.provider.merchant.platform.PaymentPlatforms;

/**
 * 公共的支付商户信息列表
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date   2019/4/9 19:39.
 *         </pre>
 */
public class CommonPaymentPlatformMerchantDetails extends BasePayConfigStorage implements PaymentPlatformMerchantDetails {

    private String detailsId;
    private String appid;
    private  String mchId;
    private  String seller;
    private String subAppid;
    private String subMchId;

    /**
     * 获取支付平台
     *
     * @return 支付平台
     */
    @Override
    public PaymentPlatform getPaymentPlatform() {
        return PaymentPlatforms.getPaymentPlatform(this.getPayType());
    }


    public void setDetailsId(String detailsId) {
        this.detailsId = detailsId;
    }

    /**
     * 获取支付商户id
     *
     * @return 支付商户id
     */
    @Override
    public String getDetailsId() {
        return detailsId;
    }



    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    @Override
    public String getAppid() {
        return appid;
    }

    @Override
    public String getPid() {
        return mchId;
    }

    @Override
    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getSubAppid() {
        return subAppid;
    }

    public void setSubAppid(String subAppid) {
        this.subAppid = subAppid;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }
}
