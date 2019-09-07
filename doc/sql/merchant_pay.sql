/*
SQLyog 企业版 - MySQL GUI v8.14
MySQL - 5.5.20 : Database - merchant_pay
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`merchant_pay` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `merchant_pay`;

/*Table structure for table `merchant_details` */

DROP TABLE IF EXISTS `merchant_details`;

CREATE TABLE `merchant_details` (
  `details_id` char(32) NOT NULL COMMENT '列表id',
  `pay_type` varchar(16) NOT NULL COMMENT '支付类型(支付渠道) 详情查看com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform对应子类，aliPay 支付宝， wxPay微信..等等',
  `appid` varchar(32) DEFAULT NULL COMMENT '应用id',
  `mch_id` varchar(32) DEFAULT NULL COMMENT '商户id，商户号，合作伙伴id等等',
  `cert_store_type` varchar(16) DEFAULT NULL COMMENT '当前面私钥公钥为证书类型的时候，这里必填，可选值:PATH,STR,INPUT_STREAM',
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

/*Data for the table `merchant_details` */



insert  into `merchant_details`(`details_id`,`pay_type`,`appid`,`mch_id`,`cert_store_type`,`key_private`,`key_public`,`key_cert`,`key_cert_pwd`,`notify_url`,`return_url`,`sign_type`,`seller`,`sub_app_id`,`sub_mch_id`,`input_charset`,`is_test`) values
 ('1','aliPay','2016080400165436','2088102169916436','INPUT_STREAM','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKroe/8h5vC4L6T+B2WdXiVwGsMvUKgb2XsKix6VY3m2wcf6tyzpNRDCNykbIwGtaeo7FshN+qZxdXHLiIam9goYncBit/8ojfLGy2gLxO/PXfzGxYGs0KsDZ+ryVPPmE34ZZ8jiJpR0ygzCFl8pN3QJPJRGTJn5+FTT9EF/9zyZAgMBAAECgYAktngcYC35u7cQXDk+jMVyiVhWYU2ULxdSpPspgLGzrZyG1saOcTIi/XVX8Spd6+B6nmLQeF/FbU3rOeuD8U2clzul2Z2YMbJ0FYay9oVZFfp5gTEFpFRTVfzqUaZQBIjJe/xHL9kQVqc5xHlE/LVA27/Kx3dbC35Y7B4EVBDYAQJBAOhsX8ZreWLKPhXiXHTyLmNKhOHJc+0tFH7Ktise/0rNspojU7o9prOatKpNylp9v6kux7migcMRdVUWWiVe+4ECQQC8PqsuEz7B0yqirQchRg1DbHjh64bw9Kj82EN1/NzOUd53tP9tg+SO97EzsibK1F7tOcuwqsa7n2aY48mQ+y0ZAkBndA2xcRcnvOOjtAz5VO8G7R12rse181HjGfG6AeMadbKg30aeaGCyIxN1loiSfNR5xsPJwibGIBg81mUrqzqBAkB+K6rkaPXJR9XtzvdWb/N3235yPkDlw7Z4MiOVM3RzvR/VMDV7m8lXoeDde2zQyeMOMYy6ztwA6WgE1bhGOnQRAkEAouUBv1sVdSBlsexX15qphOmAevzYrpufKgJIRLFWQxroXMS7FTesj+f+FmGrpPCxIde1dqJ8lqYLTyJmbzMPYw==','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB',NULL,NULL,'http://pay.egzosn.com/payBack1.json','http://pay.egzosn.com/payBack1.json','RSA','2088102169916436',NULL,NULL,'UTF-8',1)
,('2','unionPay','wx3344f4aed352deae','1469188802','PATH','密钥','转账公钥，转账时必填','D:/certs/微信退款https证书文件','1469188802','http://pay.egzosn.com/payBack2.json','http://pay.egzosn.com/payBack2.json','MD5',NULL,NULL,NULL,'UTF-8',1);
,('3','unionPay',NULL,'700000000000001','PATH','D:/certs/acp_test_sign.pfx','D:/certs/acp_test_middle.cer','D:/certs/acp_test_root.cer','000000','http://pay.egzosn.com/payBack3.json','http://pay.egzosn.com/payBack3.json','RSA2',NULL,NULL,NULL,'UTF-8',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
