package com.egzosn.pay.spring.boot.core.provider;

import com.egzosn.pay.spring.boot.core.merchant.bean.CommonPaymentPlatformMerchantDetails;
import com.egzosn.pay.spring.boot.core.utils.SqlTools;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static com.egzosn.pay.spring.boot.core.utils.SqlTools.SEPARATED;


/**
 * JDBC支付账户(商户)存储器
 *
 * @author egan
 *         <pre>
 *                 email egzosn@gmail.com
 *                 date 2018-11-22 17:18:03
 *           </pre>
 */
public class JdbcMerchantDetailsManager implements MerchantDetailsManager<CommonPaymentPlatformMerchantDetails> {



    private static final String TABLE = "merchant_details";
    private static final List<String> FIELDS = Arrays.asList("appid", "mch_id", "seller", "sub_app_id", "sub_mch_id", "key_private", "key_private_cert_pwd", "key_public", "key_public_cert1", "cert_store_type", "notify_url", "return_url", "sign_type", "input_charset", "pay_type", "is_test");
    private static final String SELECT_FIELDS = SqlTools.join(FIELDS, SEPARATED) ;
    private static final String ID = "details_id";

    private static final String BASE_QUERY = SqlTools.getSelectSQL( ID + SEPARATED + SELECT_FIELDS , TABLE);
    private static final String FIND_BY_ID = BASE_QUERY + " where " + ID + "=?";
    private static final String DEFAULT_INSERT = "insert into " + TABLE + " (" + ID + SEPARATED+ SELECT_FIELDS + ") values (" + SqlTools.forQuestionMarkSQL(FIELDS.size() + 1)+")";
    private static final String DEFAULT_UPDATE = SqlTools.generateUpdateByRowIdString(TABLE, FIELDS, ID ).toString();
    private static final String DEFAULT_DELETE = "delete from " + TABLE + " where " + ID + "=?";

   private JdbcTemplate jdbcTemplate;


    public JdbcMerchantDetailsManager(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    /**
     * 创建商户
     *
     * @param merchant 商户信息
     */
    @Override
    public void createMerchant(CommonPaymentPlatformMerchantDetails merchant) {

        Assert.isTrue(!merchantExists(merchant.getDetailsId()), "商户信息已存在");
        jdbcTemplate.update(DEFAULT_INSERT);


    }

    /**
     * 更新商户
     *
     * @param merchant 商户信息
     */
    @Override
    public void updateMerchant(CommonPaymentPlatformMerchantDetails merchant) {
        Assert.isTrue(merchantExists(merchant.getDetailsId()), "商户信息不存在");
//        merchantDetails.put(merchant.getDetailsId(), merchant);
    }

    /**
     * 删除商户
     *
     * @param id 商户id
     */
    @Override
    public void deleteMerchant(String id) {
      jdbcTemplate.update(DEFAULT_DELETE, id);
    }

    /**
     * 检查商户是否存在
     *
     * @param id 商户id
     * @return 检查商户是否存在
     */
    @Override
    public boolean merchantExists(String id) {
        return false;
    }

    /**
     * 通过支付商户id加载对应的商户信息列表
     *
     * @param merchantId 支付商户id
     * @return 商户信息列表
     */
    @Override
    public CommonPaymentPlatformMerchantDetails loadMerchantByMerchantId(String merchantId) {


        return null;
    }
}
