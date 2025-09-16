
CREATE DATABASE merchant_pay;

USE `merchant_pay`;


DROP TABLE IF EXISTS `merchant_details`;

CREATE TABLE `merchant_details` (
  `details_id` char(32) NOT NULL COMMENT '列表id',
  `pay_type` varchar(32) NOT NULL COMMENT '支付类型(支付渠道) 详情查看com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform对应子类，aliPay 支付宝， wxPay微信..等等',
  `appid` varchar(32) DEFAULT NULL COMMENT '应用id',
  `mch_id` varchar(32) DEFAULT NULL COMMENT '商户id，商户号，合作伙伴id等等',
  `cert_store_type` varchar(16) DEFAULT NULL COMMENT '当前面私钥公钥为证书类型的时候，这里必填，可选值:PATH,STR,INPUT_STREAM,CLASS_PATH,URL',
  `key_private` mediumtext COMMENT '私钥或私钥证书',
  `key_public` mediumtext COMMENT '公钥或公钥证书',
  `key_cert` varchar(20480) DEFAULT NULL COMMENT 'key证书,附加证书使用，如SSL证书，或者银联根级证书方面',
  `key_cert_pwd` varchar(32) DEFAULT NULL COMMENT '私钥证书或key证书的密码',
  `notify_url` varchar(256) DEFAULT NULL COMMENT '异步回调',
  `return_url` varchar(256) DEFAULT NULL COMMENT '同步回调地址，大部分用于付款成功后页面转跳',
  `sign_type` varchar(16) NOT NULL COMMENT '签名方式,目前已实现多种签名方式详情查看com.egzosn.pay.common.util.sign.encrypt。MD5,RSA等等',
  `seller` varchar(32) DEFAULT NULL COMMENT '收款账号，暂时只有支付宝部分使用，可根据开发者自行使用',
  `sub_app_id` varchar(32) DEFAULT NULL COMMENT '子appid',
  `sub_mch_id` varchar(32) DEFAULT NULL COMMENT '子商户id',
  `input_charset` varchar(16) NOT NULL COMMENT '编码类型，大部分为utf-8',
  `is_test` tinyint(1) NOT NULL COMMENT '是否为测试环境: 0 否，1 测试环境',
  PRIMARY KEY (`details_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




insert  into `merchant_details`(`details_id`,`pay_type`,`appid`,`mch_id`,`cert_store_type`,`key_private`,`key_public`,`key_cert`,`key_cert_pwd`,`notify_url`,`return_url`,`sign_type`,`seller`,`sub_app_id`,`sub_mch_id`,`input_charset`,`is_test`) values
 ('1','aliPay','2016080400165436','2088102169916436',NULL ,'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKroe/8h5vC4L6T+B2WdXiVwGsMvUKgb2XsKix6VY3m2wcf6tyzpNRDCNykbIwGtaeo7FshN+qZxdXHLiIam9goYncBit/8ojfLGy2gLxO/PXfzGxYGs0KsDZ+ryVPPmE34ZZ8jiJpR0ygzCFl8pN3QJPJRGTJn5+FTT9EF/9zyZAgMBAAECgYAktngcYC35u7cQXDk+jMVyiVhWYU2ULxdSpPspgLGzrZyG1saOcTIi/XVX8Spd6+B6nmLQeF/FbU3rOeuD8U2clzul2Z2YMbJ0FYay9oVZFfp5gTEFpFRTVfzqUaZQBIjJe/xHL9kQVqc5xHlE/LVA27/Kx3dbC35Y7B4EVBDYAQJBAOhsX8ZreWLKPhXiXHTyLmNKhOHJc+0tFH7Ktise/0rNspojU7o9prOatKpNylp9v6kux7migcMRdVUWWiVe+4ECQQC8PqsuEz7B0yqirQchRg1DbHjh64bw9Kj82EN1/NzOUd53tP9tg+SO97EzsibK1F7tOcuwqsa7n2aY48mQ+y0ZAkBndA2xcRcnvOOjtAz5VO8G7R12rse181HjGfG6AeMadbKg30aeaGCyIxN1loiSfNR5xsPJwibGIBg81mUrqzqBAkB+K6rkaPXJR9XtzvdWb/N3235yPkDlw7Z4MiOVM3RzvR/VMDV7m8lXoeDde2zQyeMOMYy6ztwA6WgE1bhGOnQRAkEAouUBv1sVdSBlsexX15qphOmAevzYrpufKgJIRLFWQxroXMS7FTesj+f+FmGrpPCxIde1dqJ8lqYLTyJmbzMPYw==','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB',NULL,NULL,'http://pay.egzosn.com/payBack1.json','http://pay.egzosn.com/payBack1.json','RSA','2088102169916436',NULL,NULL,'UTF-8',1)
 -- 这里支付证书加载方式，需要配合定制化的代码 详情pay-spring-boot-starter-demo\src\main\java\com\egzosn\pay\spring\boot\demo\config\MyAliPaymentPlatform.java
 ('11','aliPay','2016080400165436','2088102169916436','CLASS_PATH','MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCw7MD2Cwv/jnXssFjXnGx3JlGF57gJa2aYbJRV8MnNiPVpX4Ha+8ZjnQDhvkrWH4hHmzcujOr213HqloMpUSYBzCPiXGVRUUvdimejcHHTod7nI4g6nztzzfey/TXNDHmp7vY3pOIcjB0Zn0pkNAz2tKAFkqb4raHOqTB0QA0zD24Cn+26J2UJyYRcgeH0GtSQuUrm7yaGsuKakh+qtgWF6R71n5PMGOTQ5LH3i0WVHfCBkNGgJC6yC96HR4D7cosoyKD0+lp8UB/NVUWl7Tt/KLOgFUwh0GKSYFfv56O/VBV2+xqCGE4PlZESfVuOqz5vjjxzw3xDAUJrV8hSX/AJAgMBAAECggEBAKE0d3U4B4yo/2XUIH8EdgfykCFUSum6RFbpyBauORHfksyaSzV+ZvtomN8XhhSn0oJ8OMFfgM+86nz2+zdwSxMkMCYWTfLUAi4v59KRqAVO3kz4oS3Y3FDeAK3D7XuRvGFL7GgzAhtEx1cLPrsiehVn6s5pG15GxsIIgq/JlL1J88wn1zENLrVHmD6z/JpXvfb/RS1yR+5lyoohp4g0Ph9jJ3bCyUbRpK0QkPEzgAuWL0K2ITCL7PYHNAplI8d2xHHOLF9Qdjyx+ZrQ/RxtqzfyWzhqjsmp2qlgNCxWlt3woS9UhDB+nRvjEoWTJmIOszAMYuj8wGlX+3Ui3ALOdQECgYEA25EqnFPFinUnzgNvB6NYmh5STmZun6s4bUOLqwefKtEvrOtRwTu7sB7NIf37fizG3/MJUWHxiLy2/3ub4d2JxdDNBtJoEqnp6QB12qglCNa4CajdjtJa1dR81F9QvytsqEkmPYXFPPyviB0FcSIDAGMb3IbwvIfzBPY9WY8dJnECgYEAzkg3yKEFBZ8BU0WQ+3hyfKUoAhBEnxouxRSTBcXxwstJRiqaGTVe5aoJGQI+0xS7Z6q07XDtN2t97s6DnRLWbljsX6B64itzNhXRyzjdD3iZDU/KSw7khjhXf8XOZaj9eXmACDiUnkEn1xsM8bLiRGqB8y5f3aMY/RpuACGXnxkCgYEAx/zwT9Vpr1RIfjfYcJ+Su0X0994K0roUukj0tUJK8qf4gcsQ+y1aJe/YLib1ZBaKyj7G9O5+HmqtUAUZld/AdoJZzOXmz2EeYhD+R7wxh1xz4rCBpW3qOKvDS3jJxmZaIOoHv6/RWFxb0WGFrGcrTrX3EaWDLmWxr4pNlP5qsbECgYATllntrBR8/ycyEAX/SuWcHlaZM5BAh0zvm8+GGdCmDYWMqxjs0duL9URd4o+ynWJaKqR5c2KjA4r2tRdcP+Cqo7j2L5fbiAKtnQ7JvEGJaYsm72+nBuf+MrVkRZUepBhFg5r7rNu31zoAO+pTvQetNWvXeozRz93ckrjlPEtYaQKBgQDFwbV92rlRMLjZzlY+o0knoeJBjPQmPdiBTpGNimdy9L4c2Ure7affjcUiYhkKqrK5k5SScJTATgyQ7JF346FdtUtZ/6Kkj1RwJmmprPrDa9CATLoTle7g9OVd4sHT2ITHZMzPaF3ILvzcwJ70AD1xcxCQb+/7sDPmw7Mc8gOA7Q==','ali/alipayCertPublicKey_RSA2.crt','ali/appCertPublicKey_2016080400165436.crt,ali/alipayRootCert.crt',NULL,'http://pay.egzosn.com/payBack1.json','http://pay.egzosn.com/payBack1.json','RSA2','2088102169916436',NULL,NULL,'UTF-8',1)
,('2','unionPay',NULL,'700000000000001','PATH','D:/certs/acp_test_sign.pfx','D:/certs/acp_test_middle.cer','D:/certs/acp_test_root.cer','000000','http://pay.egzosn.com/payBack3.json','http://pay.egzosn.com/payBack3.json','RSA2',NULL,NULL,NULL,'UTF-8',1)
,( '3','wxPay','公众账号ID','合作者id（商户号）','URL','密钥','转账公钥，转账时必填','http://www.egzosn.com/certs/apiclient_cert.p12','默认为商户号','http://www.pay.egzosn.com/payBack3.json','http://www.pay.egzosn.com/payBack3.json','MD5',NULL,NULL,NULL,'UTF-8','1')
,( '4','wxV3Pay','公众账号ID','合作者id（商户号）','URL','密钥','转账公钥，转账时必填','http://www.egzosn.com/certs/apiclient_cert.p12','默认为商户号','http://www.pay.egzosn.com/payBack3.json','http://www.pay.egzosn.com/payBack3.json','MD5',NULL,NULL,NULL,'UTF-8','1')
,( '5','wxV3ProfitSharing','公众账号ID','合作者id（商户号）','URL','V3密钥','转账公钥，转账时必填','http://www.egzosn.com/certs/apiclient_cert.p12','默认为商户号','http://www.pay.egzosn.com/payBack3.json','http://www.pay.egzosn.com/payBack3.json','MD5',NULL,NULL,NULL,'UTF-8','1')
,( '6','wxV3ProfitSharing','公众账号ID','合作者id（商户号）','URL','V3密钥','转账公钥，转账时必填','http://www.egzosn.com/certs/apiclient_cert.p12','默认为商户号','http://www.pay.egzosn.com/payBack3.json','http://www.pay.egzosn.com/payBack3.json','MD5',NULL,NULL,NULL,'UTF-8','1')
,( '7','wxV3CombinePay','公众账号ID','合作者id（商户号）','URL','V3密钥','转账公钥，转账时必填','http://www.egzosn.com/certs/apiclient_cert.p12','默认为商户号','http://www.pay.egzosn.com/payBack3.json','http://www.pay.egzosn.com/payBack3.json','MD5',NULL,NULL,NULL,'UTF-8','1')
;
