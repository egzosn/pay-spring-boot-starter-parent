package com.egzosn.pay.spring.boot.core.bean;

import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.common.bean.TransactionType;

import java.math.BigDecimal;

/**
 * 商户支付订单
 * @author egan
 *         <pre>
 *                 email egzosn@gmail.com
 *
 *                 date 2019/5/9 19:43.
 *                 </pre>
 */
public class MerchantPayOrder extends PayOrder {

    /**
     * 列表id
     */
    private String  detailsId;
    /**
     * 交易类型，交易方式，
     * 本字段与{@com.egzosn.pay.common.bean.PayOrder#transactionType}相同。
     *
     *  例如，网页支付，扫码付等等
     */
    private String wayTrade;

    public MerchantPayOrder() {
    }

    public MerchantPayOrder(String detailsId, String wayTrade) {
        this.detailsId = detailsId;
        this.wayTrade = wayTrade;
    }



    public MerchantPayOrder(String detailsId, String wayTrade, String subject, String body, BigDecimal price, String outTradeNo) {
        super(subject, body, price, outTradeNo);
        this.detailsId = detailsId;
        this.wayTrade = wayTrade;
    }

    public String getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(String detailsId) {
        this.detailsId = detailsId;
    }

    public String getWayTrade() {
        return wayTrade;
    }

    public void setWayTrade(String wayTrade) {
        this.wayTrade = wayTrade;
    }
}
