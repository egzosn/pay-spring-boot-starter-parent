package com.egzosn.pay.spring.boot.demo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.egzosn.pay.common.api.PayMessageInterceptor;
import com.egzosn.pay.common.bean.AssistOrder;
import com.egzosn.pay.common.bean.RefundOrder;
import com.egzosn.pay.common.bean.RefundResult;
import com.egzosn.pay.common.bean.TransferOrder;
import com.egzosn.pay.common.util.MapGen;
import com.egzosn.pay.spring.boot.core.PayServiceManager;
import com.egzosn.pay.spring.boot.core.bean.MerchantPayOrder;
import com.egzosn.pay.spring.boot.core.bean.MerchantQueryOrder;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformMerchantDetails;
import com.egzosn.pay.spring.boot.core.provider.MerchantDetailsManager;
import com.egzosn.pay.web.support.HttpRequestNoticeParams;

/**
 * @author egan
 * email egzosn@gmail.com
 * date 2019/5/26.20:10
 */

@RequestMapping("pay")
@Controller
public class PayMerchantController {

    @Autowired
    private PayServiceManager manager;
    @Autowired
    private MerchantDetailsManager<PaymentPlatformMerchantDetails> merchantDetailsManager;


    @GetMapping("merchantExists")
    public Map<String, Object> merchantExists() {
        return new MapGen<String, Object>("exist", merchantDetailsManager.merchantExists("1")).getAttr();
    }

    /**
     * 网页支付
     *
     * @param detailsId 列表id
     * @param wayTrade  交易方式
     * @return 网页
     */
    @ResponseBody
    @RequestMapping(value = "toPay.html", produces = "text/html;charset=UTF-8")
    public String toPay(String detailsId, String wayTrade, BigDecimal price) {
        MerchantPayOrder payOrder = new MerchantPayOrder(detailsId, wayTrade, "订单title", "摘要", null == price ? new BigDecimal(0.01) : price, UUID.randomUUID().toString().replace("-", ""));
        return manager.toPay(payOrder);
    }


    /**
     * 公众号支付
     *
     * @param detailsId 账户id
     * @param openid    openid
     * @param price     金额
     * @return 返回jsapi所需参数
     */
    @RequestMapping(value = "jsapi")
    public Map<String, Object> jsapi(String detailsId, String openid, BigDecimal price) {
        //获取对应的支付账户操作工具（可根据账户id）
        MerchantPayOrder payOrder = new MerchantPayOrder(detailsId, "JSAPI", "订单title", "摘要", null == price ? new BigDecimal(0.01) : price, UUID.randomUUID().toString().replace("-", ""));
        payOrder.setOpenid(openid);
        return manager.getOrderInfo(payOrder);
    }

    /**
     * 获取支付预订单信息
     *
     * @param detailsId 支付账户id
     * @param price     金额
     * @return 支付预订单信息
     */
    @RequestMapping("app")
    public Map<String, Object> getOrderInfo(String detailsId, BigDecimal price) {
        //获取对应的支付账户操作工具（可根据账户id）
        MerchantPayOrder payOrder = new MerchantPayOrder(detailsId, "APP", "订单title", "摘要", null == price ? new BigDecimal(0.01) : price, UUID.randomUUID().toString().replace("-", ""));
        return manager.app(payOrder);
    }


    /**
     * 二维码
     *
     * @param detailsId 列表id
     * @param wayTrade  交易方式
     * @return 二维码
     */
    @ResponseBody
    @RequestMapping(value = "toQrPay.jpg", produces = "image/jpeg;charset=UTF-8")
    public byte[] toQrPay(String detailsId, String wayTrade, BigDecimal price) throws IOException {
        MerchantPayOrder payOrder = new MerchantPayOrder(detailsId, wayTrade, "订单title", "摘要", null == price ? new BigDecimal(0.01) : price, UUID.randomUUID().toString().replace("-", ""));
        return manager.toQrPay(payOrder);
    }

    /**
     * 二维码信息
     *
     * @param detailsId 列表id
     * @param wayTrade  交易方式
     * @return 二维码信息
     */
    @ResponseBody
    @RequestMapping(value = "getQrPay.json")
    public String getQrPay(String detailsId, String wayTrade, BigDecimal price) {
        MerchantPayOrder payOrder = new MerchantPayOrder(detailsId, wayTrade, "订单title", "摘要", null == price ? new BigDecimal(0.01) : price, UUID.randomUUID().toString().replace("-", ""));
        return manager.getQrPay(payOrder);
    }


    /**
     * 刷卡付,pos主动扫码付款(条码付)
     *
     * @param detailsId       账户id
     * @param transactionType 交易类型， 这个针对于每一个 支付类型的对应的几种交易方式
     * @param authCode        授权码，条码等
     * @param price           金额
     * @return 支付结果
     */
    @RequestMapping(value = "microPay")
    public Map<String, Object> microPay(String detailsId, String transactionType, BigDecimal price, String authCode) {
        //获取对应的支付账户操作工具（可根据账户id）
        MerchantPayOrder payOrder = new MerchantPayOrder(detailsId, transactionType, "订单title", "摘要", null == price ? new BigDecimal(0.01) : price, UUID.randomUUID().toString().replace("-", ""));
        //设置授权码，条码等
        payOrder.setAuthCode(authCode);
        return manager.microPay(payOrder);
    }

    /**
     * 支付回调地址
     *
     * @param request   请求
     * @param detailsId 列表id
     * @return 支付是否成功
     * 拦截器相关增加， 详情查看{@link com.egzosn.pay.common.api.PayService#addPayMessageInterceptor(PayMessageInterceptor)}
     * <p>
     * 业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看{@link com.egzosn.pay.common.api.PayService#setPayMessageHandler(com.egzosn.pay.common.api.PayMessageHandler)}
     * </p>
     * 如果未设置 {@link com.egzosn.pay.common.api.PayMessageHandler} 那么会使用默认的 {@link com.egzosn.pay.common.api.DefaultPayMessageHandler}
     */
    @RequestMapping(value = "payBack/{deatilsId}.json")
    public String payBack(HttpServletRequest request, @PathVariable String detailsId) {
        //业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看com.egzosn.pay.common.api.PayService.setPayMessageHandler()
        return manager.payBack(detailsId, new HttpRequestNoticeParams(request));
    }

    /**
     * 支付回调地址
     *
     * @param request   请求
     * @param detailsId 列表id
     * @return 支付是否成功
     * @throws IOException IOException
     *                     拦截器相关增加， 详情查看{@link com.egzosn.pay.common.api.PayService#addPayMessageInterceptor(PayMessageInterceptor)}
     *                     <p>
     *                     业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看{@link com.egzosn.pay.common.api.PayService#setPayMessageHandler(com.egzosn.pay.common.api.PayMessageHandler)}
     *                     </p>
     *                     如果未设置 {@link com.egzosn.pay.common.api.PayMessageHandler} 那么会使用默认的 {@link com.egzosn.pay.common.api.DefaultPayMessageHandler}
     */
    @Deprecated
    @RequestMapping(value = "payBackOld{detailsId}.json")
    public String payBackOld(HttpServletRequest request, @PathVariable String detailsId) throws IOException {
        //业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看com.egzosn.pay.common.api.PayService.setPayMessageHandler()
        return manager.payBack(detailsId, request.getParameterMap(), request.getInputStream());
    }

    /**
     * 查询
     * 入参根据实际情况来填写，
     * 必填参数 detailsId
     * 普通查询 tradeNo,outTradeNo
     * <b>具体详情查看pay-java-demo内对应demo</b>
     *
     * @param order 订单的请求体
     * @return 返回查询回来的结果集，支付方原值返回
     */
    @RequestMapping("query")
    public Map<String, Object> query(MerchantQueryOrder order) {

        return manager.query(order);
    }


    /**
     * 交易关闭接口
     *
     * @param detailsId  id
     * @param tradeNo    tradeNo
     * @param outTradeNo outTradeNo
     * @return 返回支付方交易关闭后的结果
     */
    @RequestMapping("close")
    public Map<String, Object> close(String detailsId, String tradeNo, String outTradeNo) {
        final MerchantQueryOrder order = new MerchantQueryOrder();
        order.setDetailsId(detailsId);
        order.setTradeNo(tradeNo);
        order.setOutTradeNo(outTradeNo);
        return manager.close(order);
    }

    /**
     * 申请退款接口
     *
     * @param detailsId 账户id
     * @param order     订单的请求体
     * @return 返回支付方申请退款后的结果
     */
    @RequestMapping("refund")
    public RefundResult refund(String detailsId, RefundOrder order) {

        return manager.refund(detailsId, order);
    }

    /**
     * 查询退款
     *
     * @param order 订单的请求体
     * @return 返回支付方查询退款后的结果
     */
    @RequestMapping("refundquery")
    public Map<String, Object> refundQuery(String detailsId, RefundOrder order) {
        return manager.refundQuery(detailsId, order);
    }

    /**
     * 下载对账单
     * 必填参数 detailsId
     * 普通查询 billDate, billType
     * <b>具体详情查看pay-java-demo内对应demo</b>
     *
     * @param order 订单的请求体
     * @return 返回支付方下载对账单的结果
     */
    @RequestMapping("downloadBill")
    public Object downloadBill(MerchantQueryOrder order) {
        return manager.downloadBill(order);

    }


    /**
     * 转账
     *
     * @param order 转账订单
     * @return 对应的转账结果
     * 具体参数看具体支付平台，案例pay-java-parent/pay-java-demo/com.egzosn.pay.demo.controller
     */
    @RequestMapping("transfer/{detailsId}")
    public Map<String, Object> transfer(@PathVariable String detailsId, TransferOrder order) {
        return manager.transfer(detailsId, order);
    }
    /**
     * 转账
     *
     * @param order 转账订单
     * @return 对应的转账结果
     * 具体参数看具体支付平台，
     */
    @RequestMapping("transferQuery/{detailsId}")
    public Map<String, Object> transferQuery(@PathVariable String detailsId, AssistOrder order) {
        return manager.transferQuery(detailsId, order);
    }

}
