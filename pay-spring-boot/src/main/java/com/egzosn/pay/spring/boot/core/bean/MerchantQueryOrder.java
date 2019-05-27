package com.egzosn.pay.spring.boot.core.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单辅助接口
 * @author: egan
 * email egzosn@gmail.com
 * date 2017/3/12 14:50
 */
public class MerchantQueryOrder {
    /**
     * 列表id
     */
    private String  detailsId;
    /**
     * 支付平台订单号
      */
    private String tradeNo;

    /**
     * 商户单号
     */
    private String outTradeNo;
    /**
     *     退款金额
     */
    private BigDecimal refundAmount;
    /**
     *     总金额
     */
    private BigDecimal totalAmount;
    /**
     *     账单时间：具体请查看对应支付平台
     */
    private Date billDate;
    /**
     *    账单时间：具体请查看对应支付平台
     */
    private String billType;
    /**
     *     支付平台订单号或者账单日期
     */
    private Object tradeNoOrBillDate;
    /**
     * 商户单号或者 账单类型
      */
    private String outTradeNoBillType;
    /**
     * 交易类型，交易方式，
     * 本字段与{@com.egzosn.pay.common.bean.PayOrder#transactionType}相同。
     *
     *  例如，网页支付，扫码付等等
     */
    private String wayTrade;

    public String getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(String detailsId) {
        this.detailsId = detailsId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public Object getTradeNoOrBillDate() {
        return tradeNoOrBillDate;
    }

    public void setTradeNoOrBillDate(Object tradeNoOrBillDate) {
        this.tradeNoOrBillDate = tradeNoOrBillDate;
    }

    public String getOutTradeNoBillType() {
        return outTradeNoBillType;
    }

    public void setOutTradeNoBillType(String outTradeNoBillType) {
        this.outTradeNoBillType = outTradeNoBillType;
    }

    public String getWayTrade() {
        return wayTrade;
    }

    public void setWayTrade(String wayTrade) {
        this.wayTrade = wayTrade;
    }
}
