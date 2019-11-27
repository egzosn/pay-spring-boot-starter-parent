#### 项目介绍
pay-spring-boot-starter 是一个基于spring-boot实现自动化配置的支付对接， **让你真正做到一行代码实现支付聚合**， 让你可以不用理解支付怎么对接，只需要专注你的业务 
 
全能第三方支付对接pay-spring-boot-starter开发工具包.优雅的轻量级支付模块集成支付对接支付整合（微信,支付宝,银联,友店,富友,跨境支付paypal,payoneer(P卡派安盈)易极付）app,扫码,网页支付刷卡付条码付刷脸付转账服务商模式、支持多种支付类型多支付账户，支付与业务完全剥离，简单几行代码即可实现支付，简单快速完成支付模块的开发
 
项目正在开发中，请持续关注……

#### 本项目在以下代码托管网站
* 码云：https://gitee.com/egzosn/pay-spring-boot-starter-parent
* GitHub：https://github.com/egzosn/pay-spring-boot-starter-parent

#### 软件架构
spring-boot  pay-java-parent

#### 本项目基础实现 pay-java-parent 全能第三方支付对接Java开发工具包.优雅的轻量级支付模块集成支付对接支付整合（微信,支付宝,银联,友店,富友,跨境支付paypal,payoneer(P卡派安盈)易极付）app,扫码,网页支付刷卡付条码付刷脸付转账服务商模式、支持多种支付类型多支付账户，支付与业务完全剥离，简单几行代码即可实现支付，简单快速完成支付模块的开发，可轻松嵌入到任何系统里 目前仅是一个开发工具包（即SDK）
* 码云：https://gitee.com/egzosn/pay-java-parent
* GitHub：https://github.com/egzosn/pay-java-parent


#### 使用教程

1. 引入 pay-spring-boot-starter
```xml
    <dependency>
        <groupId>com.egzosn</groupId>
        <artifactId>pay-spring-boot-starter</artifactId>
        <version>0.0.1</version>
    </dependency>
```
2. 引入 你需要对接的基于`pay-java-parent`支付开发包,具体支付模块 "{module-name}" 为具体的支付渠道的模块名 pay-java-ali，pay-java-wx等

```xml
    <dependency>
        <groupId>com.egzosn</groupId>
        <artifactId>{module-name}</artifactId>
        <version>2.12.8</version>
    </dependency>

```
3. 编写一个基于com.egzosn.pay.spring.boot.core.PayServiceConfigurer的子类 
```
    3.1.  并将其加入spring容器中
    3.2.  对PayServiceConfigurer的子类所实现的方法进行配置数据来源，目前提供两种方式`jdbc`与`inMemory`

```

    
4. 使用： 在你需要用到的类中注入 `com.egzosn.pay.spring.boot.core.MerchantPayServiceManager`  这个类是一个支付相关的操作类，拿到该类的引用即可对任意支付进行操作

#### 使用案例详情查看[pay-spring-boot-starter-demo](pay-spring-boot-starter-demo?dir=1&filepath=pay-spring-boot-starter-demo)



#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


