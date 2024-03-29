package com.egzosn.pay.spring.boot.core.provider.merchant.platform;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

import com.egzosn.pay.common.api.PayConfigStorage;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.TransactionType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.common.util.str.StringUtils;
import com.egzosn.pay.payoneer.api.PayoneerConfigStorage;
import com.egzosn.pay.payoneer.api.PayoneerPayService;
import com.egzosn.pay.payoneer.bean.PayoneerTransactionType;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;

/**
 * P卡(派安盈)支付平台
 *
 * @author egan
 * <pre>
 *                 email egzosn@gmail.com
 *                 date  2019/4/4 14:35.
 *                 </pre>
 */
@Configuration(PayoneerPaymentPlatform.PLATFORM_NAME)
@ConditionalOnMissingBean(PayoneerPaymentPlatform.class)
@ConditionalOnClass(name = "com.egzosn.pay.payoneer.api.PayoneerConfigStorage")
public class PayoneerPaymentPlatform implements PaymentPlatform {


    public static final String PLATFORM_NAME = "payoneerPay";
    @Deprecated
    public static final String platformName = PLATFORM_NAME;

    /**
     * 获取商户平台
     *
     * @return 商户平台
     */
    @Override
    public String getPlatform() {
        return PLATFORM_NAME;
    }

    /**
     * 获取支付平台对应的支付服务
     *
     * @param payConfigStorage 支付配置
     * @return 支付服务
     */
    @Override
    public PayService getPayService(PayConfigStorage payConfigStorage) {
        if (payConfigStorage instanceof PayoneerConfigStorage) {
            return new PayoneerPayService((PayoneerConfigStorage) payConfigStorage);
        }
        PayoneerConfigStorage configStorage = new PayoneerConfigStorage();
        configStorage.setProgramId(payConfigStorage.getPid());
        configStorage.setUserName(payConfigStorage.getSeller());
        configStorage.setApiPassword(payConfigStorage.getKeyPrivate());
        configStorage.setNotifyUrl(payConfigStorage.getNotifyUrl());
        configStorage.setReturnUrl(payConfigStorage.getReturnUrl());
        configStorage.setSignType(payConfigStorage.getSignType());
        configStorage.setPayType(payConfigStorage.getPayType());
        configStorage.setInputCharset(payConfigStorage.getInputCharset());
        configStorage.setTest(payConfigStorage.isTest());
        return new PayoneerPayService(configStorage);
    }

    /**
     * 获取支付平台对应的支付服务
     *
     * @param payConfigStorage  支付配置
     * @param httpConfigStorage 网络配置
     * @return 支付服务
     */
    @Override
    public PayService getPayService(PayConfigStorage payConfigStorage, HttpConfigStorage httpConfigStorage) {
        PayService payService = getPayService(payConfigStorage);
        payService.setRequestTemplateConfigStorage(httpConfigStorage);
        return payService;
    }

    @Override
    public TransactionType getTransactionType(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return PayoneerTransactionType.valueOf(name);
    }


}
