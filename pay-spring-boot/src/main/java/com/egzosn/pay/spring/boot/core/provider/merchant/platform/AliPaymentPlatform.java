package com.egzosn.pay.spring.boot.core.provider.merchant.platform;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.api.PayConfigStorage;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.TransactionType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.merchant.bean.CommonPaymentPlatformMerchantDetails;

/**
 * 支付宝支付平台
 *
 * @author egan
 *         <pre>
 *                 email egzosn@gmail.com
 *                 date  2019/4/4 14:35.
 *                 </pre>
 */
@Configuration(AliPaymentPlatform.PLATFORM_NAME)
@ConditionalOnMissingBean(AliPaymentPlatform.class)
@ConditionalOnClass(name = {"com.egzosn.pay.ali.api.AliPayConfigStorage"})
public class AliPaymentPlatform implements PaymentPlatform {

    public static final String PLATFORM_NAME = "aliPay";
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
        if (payConfigStorage instanceof AliPayConfigStorage) {
            return new AliPayService((AliPayConfigStorage) payConfigStorage);
        }
        AliPayConfigStorage configStorage = new AliPayConfigStorage();
        configStorage.setInputCharset(payConfigStorage.getInputCharset());
        configStorage.setAppId(payConfigStorage.getAppId());
        configStorage.setPid(payConfigStorage.getPid());
        configStorage.setAttach(payConfigStorage.getAttach());
        configStorage.setSeller(payConfigStorage.getSeller());
        configStorage.setKeyPrivate(payConfigStorage.getKeyPrivate());
        configStorage.setKeyPublic(payConfigStorage.getKeyPublic());
        configStorage.setNotifyUrl(payConfigStorage.getNotifyUrl());
        configStorage.setReturnUrl(payConfigStorage.getReturnUrl());
        configStorage.setPayType(payConfigStorage.getPayType());
        configStorage.setTest(payConfigStorage.isTest());
        configStorage.setSignType(payConfigStorage.getSignType());
        if (payConfigStorage instanceof CommonPaymentPlatformMerchantDetails) {
            final CommonPaymentPlatformMerchantDetails commonPaymentPlatformMerchantDetails = (CommonPaymentPlatformMerchantDetails) payConfigStorage;
            configStorage.setAppAuthToken(commonPaymentPlatformMerchantDetails.getSubAppId());
            certKeyPublic(configStorage, commonPaymentPlatformMerchantDetails);
        }

        return new AliPayService(configStorage);
    }
    private static void certKeyPublic(AliPayConfigStorage aliPayConfigStorage, CommonPaymentPlatformMerchantDetails payConfigStorage) {
        final String keyPublicCert = payConfigStorage.getKeyPublicCert();
        //这里通过兼容的方式去处理，匹配尾缀如果为证书文件的话就当证书处理
        if (!keyPublicCert.endsWith(".crt")) {
            return;
        }
        //设置为证书方式
        aliPayConfigStorage.setCertSign(true);
        //设置证书存储方式，这里为路径
        aliPayConfigStorage.setCertStoreType(payConfigStorage.getCertStoreType());
        String[] keyCert = payConfigStorage.getKeyCert().toString().split(",");
        aliPayConfigStorage.setMerchantCert(keyCert[0]);
        aliPayConfigStorage.setAliPayRootCert(keyCert[1]);
        aliPayConfigStorage.setAliPayCert(payConfigStorage.getKeyPublicCert());
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
        return AliTransactionType.valueOf(name);
    }


}
