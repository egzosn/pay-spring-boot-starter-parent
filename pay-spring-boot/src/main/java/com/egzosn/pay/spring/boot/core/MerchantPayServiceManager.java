package com.egzosn.pay.spring.boot.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;

import com.egzosn.pay.common.api.DefaultPayMessageHandler;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.api.PayMessageInterceptor;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.api.TransferService;
import com.egzosn.pay.common.bean.AssistOrder;
import com.egzosn.pay.common.bean.DefaultNoticeRequest;
import com.egzosn.pay.common.bean.MethodType;
import com.egzosn.pay.common.bean.NoticeParams;
import com.egzosn.pay.common.bean.NoticeRequest;
import com.egzosn.pay.common.bean.PayMessage;
import com.egzosn.pay.common.bean.RefundOrder;
import com.egzosn.pay.common.bean.RefundResult;
import com.egzosn.pay.common.bean.TransferOrder;
import com.egzosn.pay.spring.boot.core.bean.MerchantPayOrder;
import com.egzosn.pay.spring.boot.core.bean.MerchantQueryOrder;
import com.egzosn.pay.spring.boot.core.merchant.MerchantDetailsService;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformMerchantDetails;

/**
 * 商户支付服务
 *
 * @author egan
 * <pre>
 * email egzosn@gmail.com
 *
 * date 2019/5/8 19:43.
 * </pre>
 */
public class MerchantPayServiceManager implements PayServiceManager {

    @Autowired
    private MerchantDetailsService<PaymentPlatformMerchantDetails> detailsService;

    /**
     * 回调校验
     *
     * @param detailsId 商户列表id
     * @param params    回调回来的参数集
     * @return 签名校验 true通过
     */
    @Override
    public boolean verify(String detailsId, Map<String, Object> params) {

        return this.verify(detailsId, new NoticeParams(params));
    }

    /**
     * 回调校验
     *
     * @param detailsId 商户列表id
     * @param params    回调回来的参数集
     * @return 签名校验 true通过
     */
    @Override
    public boolean verify(String detailsId, NoticeParams params) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(detailsId);
        return details.getPayService().verify(params);
    }

    /**
     * 将请求参数或者请求流转化为 Map
     *
     * @param detailsId    商户列表id
     * @param parameterMap 请求参数
     * @param is           请求流
     * @return 获得回调的请求参数
     * @see #getNoticeParams(String, NoticeRequest)
     */
    @Deprecated
    @Override
    public Map<String, Object> getParameter2Map(String detailsId, Map<String, String[]> parameterMap, InputStream is) {
        return getNoticeParams(detailsId, new DefaultNoticeRequest(parameterMap, is)).getBody();
    }

    /**
     * 将请求参数或者请求流转化为 Map
     *
     * @param detailsId 商户列表id
     * @param request   通知请求
     * @return 获得回调的请求参数
     */
    @Override
    public NoticeParams getNoticeParams(String detailsId, NoticeRequest request) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(detailsId);
        return details.getPayService().getNoticeParams(request);
    }

    /**
     * 跳到支付页面
     * 针对实时支付,即时付款
     *
     * @param payOrder 商户支付订单信息
     * @return 跳到支付页面
     */
    @Override
    public String toPay(MerchantPayOrder payOrder) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(payOrder.getDetailsId());
        payOrder.setTransactionType(details.getPaymentPlatform().getTransactionType(payOrder.getWayTrade()));
        PayService payService = details.getPayService();
        Map<String, Object> orderInfo = payService.orderInfo(payOrder);
        return payService.buildRequest(orderInfo, MethodType.POST);
    }

    /**
     * 获取支付预订单信息
     *
     * @param payOrder 商户支付订单信息
     * @return 支付预订单信息
     */
    @Override
    public Map<String, Object> app(MerchantPayOrder payOrder) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(payOrder.getDetailsId());
        payOrder.setTransactionType(details.getPaymentPlatform().getTransactionType(payOrder.getWayTrade()));
        PayService payService = details.getPayService();
        return payService.app(payOrder);
    }

    /**
     * 获取支付预订单信息
     *
     * @param payOrder 商户支付订单信息
     * @return 支付预订单信息
     */
    @Override
    public Map<String, Object> getOrderInfo(MerchantPayOrder payOrder) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(payOrder.getDetailsId());
        payOrder.setTransactionType(details.getPaymentPlatform().getTransactionType(payOrder.getWayTrade()));
        PayService payService = details.getPayService();
        return payService.orderInfo(payOrder);
    }

    /**
     * 刷卡付,pos主动扫码付款(条码付)
     * 刷脸付
     *
     * @param payOrder 商户支付订单信息
     * @return 支付结果
     */
    @Override
    public Map<String, Object> microPay(MerchantPayOrder payOrder) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(payOrder.getDetailsId());
        payOrder.setTransactionType(details.getPaymentPlatform().getTransactionType(payOrder.getWayTrade()));
        //支付结果
        return details.getPayService().microPay(payOrder);
    }

    /**
     * 获取二维码图像
     * 二维码支付
     *
     * @param payOrder 商户支付订单信息
     * @return 二维码图像
     * @throws IOException IOException
     */
    @Override
    public byte[] toQrPay(MerchantPayOrder payOrder) throws IOException {
        //获取对应的支付账户操作工具（可根据账户id）
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(payOrder.getDetailsId());
        payOrder.setTransactionType(details.getPaymentPlatform().getTransactionType(payOrder.getWayTrade()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(details.getPayService().genQrPay(payOrder), "JPEG", baos);
        return baos.toByteArray();
    }

    /**
     * 获取二维码信息
     * 二维码支付
     *
     * @param payOrder 商户支付订单信息
     * @return 二维码信息
     */
    @Override
    public String getQrPay(MerchantPayOrder payOrder) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(payOrder.getDetailsId());
        payOrder.setTransactionType(details.getPaymentPlatform().getTransactionType(payOrder.getWayTrade()));
        return details.getPayService().getQrPay(payOrder);
    }

    /**
     * 支付回调地址
     * 方式二
     *
     * @param detailsId    商户列表id
     * @param parameterMap 请求参数
     * @param is           请求流
     * @return 支付是否成功
     * @throws IOException IOException
     *                     拦截器相关增加， 详情查看{@link com.egzosn.pay.common.api.PayService#addPayMessageInterceptor(PayMessageInterceptor)}
     *                     <p>
     *                     业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看{@link com.egzosn.pay.common.api.PayService#setPayMessageHandler(com.egzosn.pay.common.api.PayMessageHandler)}
     *                     </p>
     *                     如果未设置 {@link com.egzosn.pay.common.api.PayMessageHandler} 那么会使用默认的 {@link com.egzosn.pay.common.api.DefaultPayMessageHandler}
     *                     方法过时，替代方法{@link #payBack(String, NoticeRequest)}
     */
    @Deprecated
    @Override
    public String payBack(String detailsId, Map<String, String[]> parameterMap, InputStream is) throws IOException {
        return this.payBack(detailsId, new DefaultNoticeRequest(parameterMap, is));
    }

    /**
     * 支付回调地址
     * 方式二
     *
     * @param detailsId 商户列表id
     * @param request   请求参数
     * @return 支付是否成功
     * 拦截器相关增加， 详情查看{@link PayService#addPayMessageInterceptor(PayMessageInterceptor)}
     * <p>
     * 业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看{@link PayService#setPayMessageHandler(PayMessageHandler)}
     * </p>
     * 如果未设置 {@link PayMessageHandler} 那么会使用默认的 {@link DefaultPayMessageHandler}
     */
    @Override
    public String payBack(String detailsId, NoticeRequest request) {
        //业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看com.egzosn.pay.common.api.PayService.setPayMessageHandler()
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(detailsId);
        PayService payService = details.getPayService();
        return payService.payBack(request).toMessage();
    }


    /**
     * 查询
     *
     * @param order 订单的请求体
     * @return 返回查询回来的结果集，支付方原值返回
     */
    @Override
    public Map<String, Object> query(MerchantQueryOrder order) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(order.getDetailsId());
        order.setTransactionType(details.getPaymentPlatform().getTransactionType(order.getWayTrade()));
        return details.getPayService().query(order);
    }

    /**
     * 交易关闭接口
     *
     * @param order 订单的请求体
     * @return 返回支付方交易关闭后的结果
     */
    @Override
    public Map<String, Object> close(MerchantQueryOrder order) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(order.getDetailsId());
        order.setTransactionType(details.getPaymentPlatform().getTransactionType(order.getWayTrade()));
        return details.getPayService().close(order);
    }


    /**
     * 申请退款接口
     *
     * @param detailsId 列表id
     * @param order     订单的请求体
     * @return 返回支付方申请退款后的结果
     */
    @Override
    public RefundResult refund(String detailsId, RefundOrder order) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(detailsId);

        return details.getPayService().refund(order);
    }

    /**
     * 查询退款
     *
     * @param detailsId 列表id
     * @param order     订单的请求体
     * @return 返回支付方查询退款后的结果
     */
    @Override
    public Map<String, Object> refundQuery(String detailsId, RefundOrder order) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(detailsId);
        return details.getPayService().refundquery(order);
    }

    /**
     * 下载对账单
     *
     * @param order 订单的请求体
     * @return 返回支付方下载对账单的结果
     */
    @Override
    public Map<String, Object> downloadBill(MerchantQueryOrder order) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(order.getDetailsId());

        return details.getPayService().downloadBill(order.getBillDate(), order.getBillType());
    }


    /**
     * 转账
     *
     * @param detailsId 列表id
     * @param order     转账订单
     * @return 对应的转账结果
     */
    @Override
    public Map<String, Object> transfer(String detailsId, TransferOrder order) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(detailsId);

        return details.getPayService().transfer(order);
    }

    /**
     * 转账查询
     *
     * @param detailsId 列表id
     * @param outNo     商户转账订单号
     * @param tradeNo   支付平台转账订单号
     * @return 对应的转账订单
     */
    @Override
    public Map<String, Object> transferQuery(String detailsId, String outNo, String tradeNo) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(detailsId);
        return details.getPayService().transferQuery(outNo, tradeNo);
    }

    /**
     * 转账查询
     *
     * @param detailsId   列表id
     * @param assistOrder 辅助交易订单
     * @return 对应的转账订单
     */
    @Override
    public Map<String, Object> transferQuery(String detailsId, AssistOrder assistOrder) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(detailsId);
        PayService payService = details.getPayService();
        if (payService instanceof TransferService) {
            return ((TransferService) payService).transferQuery(assistOrder);
        }
        //todo: 暂时兼容旧版本
        return details.getPayService().transferQuery(assistOrder.getOutTradeNo(), assistOrder.getTradeNo());
    }

    /**
     * 创建消息
     *
     * @param detailsId 列表id
     * @param message   支付平台返回的消息
     * @return 支付消息对象
     */
    @Override
    public PayMessage createMessage(String detailsId, Map<String, Object> message) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(detailsId);
        return details.getPayService().createMessage(message);
    }

    /**
     * 获取payService具体调用类引用
     *
     * @param detailsId       列表id
     * @param payServiceClass payService类
     * @return 具体调用类引用
     */
    @Override
    public <T extends PayService> T cast(String detailsId, Class<T> payServiceClass) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(detailsId);
        return (T) details.getPayService();
    }
}
