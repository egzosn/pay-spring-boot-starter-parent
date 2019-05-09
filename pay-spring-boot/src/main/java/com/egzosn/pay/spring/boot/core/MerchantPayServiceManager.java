package com.egzosn.pay.spring.boot.core;

import com.egzosn.pay.common.api.PayMessageInterceptor;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.MethodType;
import com.egzosn.pay.spring.boot.core.bean.MerchantPayOrder;
import com.egzosn.pay.spring.boot.core.configurers.MerchantDetailsServiceConfigurer;
import com.egzosn.pay.spring.boot.core.merchant.MerchantDetailsService;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformMerchantDetails;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 商户支付服务
 *
 * @author egan
 *         <pre>
 *                              email egzosn@gmail.com
 *
 *                              date 2019/5/8 19:43.
 *                         </pre>
 */
public class MerchantPayServiceManager {

    @Autowired
    private PayServiceConfigurer configurer;

    private MerchantDetailsService<PaymentPlatformMerchantDetails> detailsService;

    @Autowired
    public void configure(MerchantDetailsServiceConfigurer merchantDetails) {
        try {
            configurer.configure(merchantDetails);
            detailsService = merchantDetails.getBuilder().build();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 跳到支付页面
     * 针对实时支付,即时付款
     * @param payOrder    商户支付订单信息
     * @return 跳到支付页面
     */
    public String toPay(MerchantPayOrder payOrder) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(payOrder.getDetailsId());
        payOrder.setTransactionType(details.getPaymentPlatform().getTransactionType(payOrder.getWayTrade()));
        PayService payService = details.getPayService();
        Map orderInfo = payService.orderInfo(payOrder);
        return payService.buildRequest(orderInfo, MethodType.POST);
    }

    /**
     * 获取支付预订单信息
     * @param payOrder    商户支付订单信息
     * @return 支付预订单信息
     */
    public Map<String, Object> getOrderInfo(MerchantPayOrder payOrder) {
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(payOrder.getDetailsId());
        payOrder.setTransactionType(details.getPaymentPlatform().getTransactionType(payOrder.getWayTrade()));
        PayService payService = details.getPayService();
        Map orderInfo = payService.orderInfo(payOrder);
        return orderInfo;
    }



    /**
     * 支付回调地址
     * 方式二
     *
     * @param detailsId 商户列表id
     * @param parameterMap 请求参数
     * @param is           请求流
     * @return 支付是否成功
     * @throws IOException IOException
     * 拦截器相关增加， 详情查看{@link com.egzosn.pay.common.api.PayService#addPayMessageInterceptor(PayMessageInterceptor)}
     * <p>
     * 业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看{@link com.egzosn.pay.common.api.PayService#setPayMessageHandler(com.egzosn.pay.common.api.PayMessageHandler)}
     * </p>
     * 如果未设置 {@link com.egzosn.pay.common.api.PayMessageHandler} 那么会使用默认的 {@link com.egzosn.pay.common.api.DefaultPayMessageHandler}
     */
    public String payBack(String detailsId, Map<String, String[]> parameterMap, InputStream is) throws IOException {
        //业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看com.egzosn.pay.common.api.PayService.setPayMessageHandler()
        PaymentPlatformMerchantDetails details = detailsService.loadMerchantByMerchantId(detailsId);
        PayService payService = details.getPayService();
        return payService.payBack(parameterMap, is).toMessage();
    }
}
