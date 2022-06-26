package com.egzosn.pay.spring.boot.demo.config;

import org.springframework.stereotype.Component;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.api.PayConfigStorage;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.TransactionType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.spring.boot.core.merchant.bean.CommonPaymentPlatformMerchantDetails;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.AliPaymentPlatform;

/**
 * 支付宝支付平台,自定义支付宝平台支持证书方式
 * 所有支付平台都可以使用这种定制的方式进行覆盖
 * 使用案例
 * @author egan
 * <pre>
 *                 email egzosn@gmail.com
 *                 date  2019/4/4 14:35.
 *                 </pre>
 */
//@Component(AliPaymentPlatform.PLATFORM_NAME)
public class MyAliPaymentPlatform extends AliPaymentPlatform {


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

    /**
     * 设置证书公钥信息
     * 普通公钥方式与证书公钥方式为两者取其一的方式
     *
     * @param aliPayConfigStorage 支付宝配置信息
     * @param payConfigStorage    配置信息
     */
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
