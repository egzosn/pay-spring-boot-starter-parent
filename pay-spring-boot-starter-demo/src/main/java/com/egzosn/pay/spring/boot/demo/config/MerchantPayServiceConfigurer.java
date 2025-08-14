package com.egzosn.pay.spring.boot.demo.config;

import com.egzosn.pay.common.bean.CertStoreType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.spring.boot.core.PayServiceConfigurer;
import com.egzosn.pay.spring.boot.core.configurers.MerchantDetailsServiceConfigurer;
import com.egzosn.pay.spring.boot.core.merchant.bean.UnionMerchantDetails;

import com.egzosn.pay.spring.boot.core.configurers.PayMessageConfigurer;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.AliPaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.PaymentPlatforms;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.WxPaymentPlatform;
import com.egzosn.pay.spring.boot.demo.config.handlers.AliPayMessageHandler;
import com.egzosn.pay.spring.boot.demo.config.handlers.WxPayMessageHandler;
import com.egzosn.pay.spring.boot.demo.config.interceptor.AliPayMessageInterceptor;
import com.egzosn.pay.spring.boot.core.configurers.PayMessageConfigurer;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.AliPaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.PaymentPlatforms;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.WxPaymentPlatform;
import com.egzosn.pay.spring.boot.demo.config.handlers.WxPayMessageHandler;
import com.egzosn.pay.spring.boot.demo.config.interceptor.AliPayMessageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 支付服务配置
 *
 * @author egan
 *         email egzosn@gmail.com
 *         date 2019/5/26.19:25
 */
@Configuration
public class MerchantPayServiceConfigurer implements PayServiceConfigurer {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AutowireCapableBeanFactory spring;
    @Autowired
    private AliPayMessageHandler aliPayMessageHandler;
    public static PublicKey loadPublicKeyFromString(String keyString) {
        try {
            keyString = keyString.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replaceAll("\\s+", "");
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(keyString)));
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException(e);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 商户配置
     *
     * @param merchants 商户配置
     */
    @Override
    public void configure(MerchantDetailsServiceConfigurer merchants)  {

//        数据库文件存放 /doc/sql目录下
//        merchants.jdbc()
//                //是否开启缓存，默认不开启,这里开启缓存
//                .cache(true)
//                .template(jdbcTemplate);
        //微信请求配置，详情参考https://gitee.com/egzosn/pay-java-parent项目中的使用
//        PublicKey publicKey = loadPublicKeyFromString("MIIEFDCCAvygAwIBAgIUcsLvDuUJXG2Cb/kJvjPWI6xi3kYwDQYJKoZIhvcNAQEL\n" +
//                "BQAwXjELMAkGA1UEBhMCQ04xEzARBgNVBAoTClRlbnBheS5jb20xHTAbBgNVBAsT\n" +
//                "FFRlbnBheS5jb20gQ0EgQ2VudGVyMRswGQYDVQQDExJUZW5wYXkuY29tIFJvb3Qg\n" +
//                "Q0EwHhcNMjUwMTA2MDY0MjI3WhcNMzAwMTA1MDY0MjI3WjBuMRgwFgYDVQQDDA9U\n" +
//                "ZW5wYXkuY29tIHNpZ24xEzARBgNVBAoMClRlbnBheS5jb20xHTAbBgNVBAsMFFRl\n" +
//                "bnBheS5jb20gQ0EgQ2VudGVyMQswCQYDVQQGEwJDTjERMA8GA1UEBwwIU2hlblpo\n" +
//                "ZW4wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCejehpOA5Q4yen2mjx\n" +
//                "imNGIwiQThXnt9NPm8ut+A9YON+sBS5bwApy1WZyClUnc0OdGKu0Ygw9jp0qTodN\n" +
//                "lfLJsR5yLnm4vQY43uYVIHl9dCTOD+ZqYLc+Ld/B+gtYMJbIhgUU142Y7lUIOsmI\n" +
//                "DbMLPchbxPuajqdav8tL2cnz9MtF5Gw+ZusJ9GBYBeu8/xyQFaq3jD/zWnbGHZ4K\n" +
//                "hS1LOXmcyTPwZNy9k4ckqcf1x/yy7eH92EsXnXAqhfH+dQcSgYZcK6JLXi2bWyqF\n" +
//                "UpQZ2DzmhJwQ09bN0a9g4Py1AOYSdj0ZBuaNvHRJVVQK1LLg6vUtfbMPGDwhbyp/\n" +
//                "S207AgMBAAGjgbkwgbYwCQYDVR0TBAIwADALBgNVHQ8EBAMCA/gwgZsGA1UdHwSB\n" +
//                "kzCBkDCBjaCBiqCBh4aBhGh0dHA6Ly9ldmNhLml0cnVzLmNvbS5jbi9wdWJsaWMv\n" +
//                "aXRydXNjcmw/Q0E9MUJENDIyMEU1MERCQzA0QjA2QUQzOTc1NDk4NDZDMDFDM0U4\n" +
//                "RUJEMiZzZz1IQUNDNDcxQjY1NDIyRTEyQjI3QTlEMzNBODdBRDFDREY1OTI2RTE0\n" +
//                "MDM3MTANBgkqhkiG9w0BAQsFAAOCAQEAk82I3ThSpikhHbtG8pTtJay8gJyIUf+n\n" +
//                "rn9G8AruSpDvlCxRMJ6QHXLABLx5ko/yG+gGv7P02M3AQbDskxMN430vHRsJ8tyY\n" +
//                "B8C2QE8AbVHzJ2/K6CirxDxGbclSwJCvdq++uebfnKbM7U5GnfyHprpjT9MLIx0G\n" +
//                "r+i1CXQ4SCWYt0am84kLwpjDIkPuzqWbIdmNVV9+3lbQilaB6/EQzQxsGeA0FxPV\n" +
//                "mGJC02hmaPgORKGLZM91Z3xnS/trLU3yF/VxMX5g7QGW9DlHOW2I8Ko/wiZuU3MM\n" +
//                "WKM0Vp8BB+817Vn8HZ52b53bc85Ffm8tOLlX5kMutV2k8QH7SR/8xg==");
        //内存Builder方式
        merchants.inMemory()
//                .ali()
//                .detailsId("1")
//                .appid("2016080400165436")
//                .keyPrivate("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKroe/8h5vC4L6T+B2WdXiVwGsMvUKgb2XsKix6VY3m2wcf6tyzpNRDCNykbIwGtaeo7FshN+qZxdXHLiIam9goYncBit/8ojfLGy2gLxO/PXfzGxYGs0KsDZ+ryVPPmE34ZZ8jiJpR0ygzCFl8pN3QJPJRGTJn5+FTT9EF/9zyZAgMBAAECgYAktngcYC35u7cQXDk+jMVyiVhWYU2ULxdSpPspgLGzrZyG1saOcTIi/XVX8Spd6+B6nmLQeF/FbU3rOeuD8U2clzul2Z2YMbJ0FYay9oVZFfp5gTEFpFRTVfzqUaZQBIjJe/xHL9kQVqc5xHlE/LVA27/Kx3dbC35Y7B4EVBDYAQJBAOhsX8ZreWLKPhXiXHTyLmNKhOHJc+0tFH7Ktise/0rNspojU7o9prOatKpNylp9v6kux7migcMRdVUWWiVe+4ECQQC8PqsuEz7B0yqirQchRg1DbHjh64bw9Kj82EN1/NzOUd53tP9tg+SO97EzsibK1F7tOcuwqsa7n2aY48mQ+y0ZAkBndA2xcRcnvOOjtAz5VO8G7R12rse181HjGfG6AeMadbKg30aeaGCyIxN1loiSfNR5xsPJwibGIBg81mUrqzqBAkB+K6rkaPXJR9XtzvdWb/N3235yPkDlw7Z4MiOVM3RzvR/VMDV7m8lXoeDde2zQyeMOMYy6ztwA6WgE1bhGOnQRAkEAouUBv1sVdSBlsexX15qphOmAevzYrpufKgJIRLFWQxroXMS7FTesj+f+FmGrpPCxIde1dqJ8lqYLTyJmbzMPYw==")
//                .keyPublic("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB")
//                .inputCharset("utf-8")
//                .notifyUrl("http://pay.egzosn.com/payBack1.json")
//                .returnUrl("http://pay.egzosn.com/payBack1.json")
//                .pid("2088102169916436")
//                .seller("2088102169916436")
//                .signType("RSA")
//                .test(true)
//                .and()
                .wxV3()
                .detailsId("2")
                .appId("wx5ce9f1a204c49979")
                .mchId("1703302548")
                .v3ApiKey("KDBX2tbrKi9eWFEZW4s3bxGpP30AtXtW")
                .keyPrivate("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCltaNjUKUcXEQe\n" +
                        "31O3ljZswpRgNMVNVdR2vQt0ymVKZwFcTNrOE28Tmn4UuWkb6HzCTtlOM2mOuX9T\n" +
                        "uE8rkkc+gqrZtTjchotsGu35YO3L2VvADxvd0SUw9rZIqh+/5MybBEpmOwR9p1pr\n" +
                        "bYSwxSWlwx4qt1fNX/uOrjYplcvEp00otlHv3SJh2CyK+vkWP30F18FVTpTf1mdY\n" +
                        "ehMF8aDr9Zlnsdm25Kig14Zly25dsuxiuoagLWIz0031v3byO0iuAxxUhl9ofUHh\n" +
                        "zt2ajshy/DS910N0PE/FCYKbMQEz951ZnAojWI97hXRYnigAEo1VJyB9GBxxPRIT\n" +
                        "fr4KUJGvAgMBAAECggEAVw5L3hs6AuI+L3HoS2SxBwZaaQiQNwbGbfL3F85U1He0\n" +
                        "8Ua3FNiE3Gmnnz+hBrZnBLXj1/+OjxrI17TWcu+QWjjPhTx9uO0+RfcH2qGfjB6O\n" +
                        "4yGM5EXCIi0aqqYls+yByVbBt19+Bn1rNVD+Ctgn5SPSr2JO0GiCgfzoG4TE3nSl\n" +
                        "v9YWhfzbWjrNgQR4npY5UQbL4D86ubZ6I81YnN+MGmxuTxS06fwjQyVH3AQbUp70\n" +
                        "LRmdjQUhsRS2uSmsMmfavLtfyq14ZV2I0KWNADzvGfcTNOtg+XCCohQyJUhEDhtH\n" +
                        "Si8bvsL+Blauu0o8tpFMA1hEIsxdwdvjWAie45yW4QKBgQDUyQTYc/oyJn7HfGLN\n" +
                        "EcUF9qCL0ymH1UepstrnIDiNTZ4n4rFCORlM5wpZe6kZzZqMRMnHEaipk4WWryAN\n" +
                        "z0UjXA+n3j/uoIybvpMjljMHvxzP+IfZwt/XK437bFeiNSNigk8zU7sEw0RD+y77\n" +
                        "Jn6ucmnFxBwpsN4zuFKY+ObhBwKBgQDHXReTD6HLZ12Pf91469D/qvD7jJ7R/1I7\n" +
                        "maPHQB7+VrSHZeZnhy9TOZyONEt3vd2GpYxhdvkUXL4j6FqjYn0tt72PNs/xQhL0\n" +
                        "V3EZsm+YYZ8GOPh24Ij6jPhWLEIvYGLYSBtR7Uq8VWN84A1AUxnw538I8L7qH+7D\n" +
                        "j0PTuSioGQKBgEeqoLgqb7UeZAraKQi6mGpGw5H0gANg4S8Vr0azAnkNEFyMrHkK\n" +
                        "dCwDkOfMj4rCRfBCZtdmajEbH549w4UDL10Wb6txXoUHi/QUvsf4mZ1dT9337gF4\n" +
                        "8h0tbTemKOnYDd/q1bQK2m1jOwXOfudV8srcfCWAxJ+CE4TF6wPcqR5XAoGAYAFs\n" +
                        "gN1eRN2aKKiitsCY/QYM1pZ5RRd3OkxamnE+e6y1cx6XPfPTznhH9BMq5JcCPv0q\n" +
                        "BSWN/UhMKG6Ud2nObl21Y2o56SywhAo35PAQ3YjlU9HLlTO7wyxvys+1b6sy7oSK\n" +
                        "44EqJFfaeSBMQVXDgEd63YtR5N1L1dEAEM1xzsECgYEAxMV8DnXRUpY2tTF0GHLL\n" +
                        "RJBz0OQNbQbpOsUQuMyAN+MGISdbjJISpVOBE1ngUwS1CNGIUaBKUZu5g53ZqF/M\n" +
                        "K3a0qjFJwzzkCaTo802CBcoATYKhQv+xP25s7UU+gS1Qhsa57+j/g0yoSNle02gE\n" +
                        "vOBgNU/g1THHdIVOY4uO64g=")
                .merchantSerialNumber("72C2EF0EE5095C6D826FF909BE33D623AC62DE46")
//                .keyPublic("MIIEFDCCAvygAwIBAgIUcsLvDuUJXG2Cb/kJvjPWI6xi3kYwDQYJKoZIhvcNAQEL\n" +
//                        "BQAwXjELMAkGA1UEBhMCQ04xEzARBgNVBAoTClRlbnBheS5jb20xHTAbBgNVBAsT\n" +
//                        "FFRlbnBheS5jb20gQ0EgQ2VudGVyMRswGQYDVQQDExJUZW5wYXkuY29tIFJvb3Qg\n" +
//                        "Q0EwHhcNMjUwMTA2MDY0MjI3WhcNMzAwMTA1MDY0MjI3WjBuMRgwFgYDVQQDDA9U\n" +
//                        "ZW5wYXkuY29tIHNpZ24xEzARBgNVBAoMClRlbnBheS5jb20xHTAbBgNVBAsMFFRl\n" +
//                        "bnBheS5jb20gQ0EgQ2VudGVyMQswCQYDVQQGEwJDTjERMA8GA1UEBwwIU2hlblpo\n" +
//                        "ZW4wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCejehpOA5Q4yen2mjx\n" +
//                        "imNGIwiQThXnt9NPm8ut+A9YON+sBS5bwApy1WZyClUnc0OdGKu0Ygw9jp0qTodN\n" +
//                        "lfLJsR5yLnm4vQY43uYVIHl9dCTOD+ZqYLc+Ld/B+gtYMJbIhgUU142Y7lUIOsmI\n" +
//                        "DbMLPchbxPuajqdav8tL2cnz9MtF5Gw+ZusJ9GBYBeu8/xyQFaq3jD/zWnbGHZ4K\n" +
//                        "hS1LOXmcyTPwZNy9k4ckqcf1x/yy7eH92EsXnXAqhfH+dQcSgYZcK6JLXi2bWyqF\n" +
//                        "UpQZ2DzmhJwQ09bN0a9g4Py1AOYSdj0ZBuaNvHRJVVQK1LLg6vUtfbMPGDwhbyp/\n" +
//                        "S207AgMBAAGjgbkwgbYwCQYDVR0TBAIwADALBgNVHQ8EBAMCA/gwgZsGA1UdHwSB\n" +
//                        "kzCBkDCBjaCBiqCBh4aBhGh0dHA6Ly9ldmNhLml0cnVzLmNvbS5jbi9wdWJsaWMv\n" +
//                        "aXRydXNjcmw/Q0E9MUJENDIyMEU1MERCQzA0QjA2QUQzOTc1NDk4NDZDMDFDM0U4\n" +
//                        "RUJEMiZzZz1IQUNDNDcxQjY1NDIyRTEyQjI3QTlEMzNBODdBRDFDREY1OTI2RTE0\n" +
//                        "MDM3MTANBgkqhkiG9w0BAQsFAAOCAQEAk82I3ThSpikhHbtG8pTtJay8gJyIUf+n\n" +
//                        "rn9G8AruSpDvlCxRMJ6QHXLABLx5ko/yG+gGv7P02M3AQbDskxMN430vHRsJ8tyY\n" +
//                        "B8C2QE8AbVHzJ2/K6CirxDxGbclSwJCvdq++uebfnKbM7U5GnfyHprpjT9MLIx0G\n" +
//                        "r+i1CXQ4SCWYt0am84kLwpjDIkPuzqWbIdmNVV9+3lbQilaB6/EQzQxsGeA0FxPV\n" +
//                        "mGJC02hmaPgORKGLZM91Z3xnS/trLU3yF/VxMX5g7QGW9DlHOW2I8Ko/wiZuU3MM\n" +
//                        "WKM0Vp8BB+817Vn8HZ52b53bc85Ffm8tOLlX5kMutV2k8QH7SR/8xg==")
//                .keyPublicId("72C2EF0EE5095C6D826FF909BE33D623AC62DE46")
                .notifyUrl("http://pay.egzosn.com/payBack2.json")
                .returnUrl("http://pay.egzosn.com/payBack2.json")
                .inputCharset("utf-8")
                .and()
        ;

      /*  //------------内存手动方式------------------
        UnionMerchantDetails unionMerchantDetails = new UnionMerchantDetails();
        unionMerchantDetails.detailsId("3");
        //内存方式的时候这个必须设置
        unionMerchantDetails.setCertSign(true);
        unionMerchantDetails.setMerId("700000000000001");
        //公钥，验签证书链格式： 中级证书路径;
        unionMerchantDetails.setAcpMiddleCert("D:/certs/acp_test_middle.cer");
        //公钥，根证书路径
        unionMerchantDetails.setAcpRootCert("D:/certs/acp_test_root.cer");
        //私钥, 私钥证书格式： 私钥证书路径
        unionMerchantDetails.setKeyPrivateCert("D:/certs/acp_test_sign.pfx");
        //私钥证书对应的密码
        unionMerchantDetails.setKeyPrivateCertPwd("000000");
        //证书的存储方式
        unionMerchantDetails.setCertStoreType(CertStoreType.PATH);
        unionMerchantDetails.setNotifyUrl("http://127.0.0.1/payBack4.json");
        // 无需同步回调可不填  app填这个就可以
        unionMerchantDetails.setReturnUrl("http://127.0.0.1/payBack4.json");
        unionMerchantDetails.setInputCharset("UTF-8");
        unionMerchantDetails.setSignType("RSA2");
        unionMerchantDetails.setTest(true);
        //手动加入商户容器中
        merchants.inMemory().addMerchantDetails(unionMerchantDetails);*/
    }
    /**
     * 商户配置
     *
     * @param configurer 支付消息配置
     */
    @Override
    public void configure(PayMessageConfigurer configurer) {
        PaymentPlatform aliPaymentPlatform = PaymentPlatforms.getPaymentPlatform(AliPaymentPlatform.PLATFORM_NAME);
        configurer.addHandler(aliPaymentPlatform, aliPayMessageHandler);
        configurer.addInterceptor(aliPaymentPlatform, spring.getBean(AliPayMessageInterceptor.class));
        configurer.addHandler(PaymentPlatforms.getPaymentPlatform(WxPaymentPlatform.PLATFORM_NAME), new WxPayMessageHandler());
    }
}
