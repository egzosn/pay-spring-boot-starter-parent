package com.egzosn.pay.spring.boot.core.provider;

import com.egzosn.pay.common.bean.CertStoreType;
import com.egzosn.pay.spring.boot.core.merchant.bean.CommonPaymentPlatformMerchantDetails;
import com.egzosn.pay.spring.boot.core.utils.SqlTools;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.egzosn.pay.spring.boot.core.utils.SqlTools.SEPARATED;


/**
 * JDBC支付账户(商户)存储器
 *
 * @author egan
 *         <pre>
 *                                 email egzosn@gmail.com
 *                                 date 2018-11-22 17:18:03
 *                           </pre>
 */
public class JdbcMerchantDetailsManager implements MerchantDetailsManager<CommonPaymentPlatformMerchantDetails> {


    private static final String TABLE = "merchant_details";
    private static final List<String> FIELDS = Arrays.asList("appid", "mch_id", "key_private", "key_private_cert_pwd", "key_public", "key_public_cert1", "cert_store_type", "notify_url", "return_url", "sign_type", "seller", "sub_app_id", "sub_mch_id", "input_charset", "pay_type", "is_test");
    private static final String SELECT_FIELDS = SqlTools.join(FIELDS, SEPARATED);
    private static final String ID = "details_id";

    private static final String DEFAULT_BASE_QUERY_SQL = SqlTools.getSelectSQL(ID + SEPARATED + SELECT_FIELDS, TABLE);
    private static final String DEFAULT_FIND_BY_ID_SQL = DEFAULT_BASE_QUERY_SQL + " where " + ID + "=?";
    private static final String DEFAULT_INSERT_SQL = "insert into " + TABLE + " (" + ID + SEPARATED + SELECT_FIELDS + ") values (" + SqlTools.forQuestionMarkSQL(FIELDS.size() + 1) + ")";
    private static final String DEFAULT_UPDATE_SQL = SqlTools.generateUpdateByRowIdString(TABLE, FIELDS, ID).toString();
    private static final String DEFAULT_DELETE_SQL = "delete from " + TABLE + " where " + ID + "=?";
    public static final String DEFAULT_EXISTS_SQL = "select " + ID + " from " + TABLE + " where " + ID + " = ?";

    private JdbcTemplate jdbcTemplate;

    private String findByIdSql = DEFAULT_FIND_BY_ID_SQL;
    private String insertSql = DEFAULT_INSERT_SQL;
    private String updateSql = DEFAULT_UPDATE_SQL;
    private String deleteSql = DEFAULT_DELETE_SQL;
    private String existsSql = DEFAULT_EXISTS_SQL;


    public JdbcMerchantDetailsManager(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcMerchantDetailsManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 创建商户
     *
     * @param merchant 商户信息
     */
    @Override
    public void createMerchant(CommonPaymentPlatformMerchantDetails merchant) {

        Assert.isTrue(!merchantExists(merchant.getDetailsId()), "商户信息已存在");
        Object[] args = new Object[]{merchant.getDetailsId(), merchant.getAppid(), merchant.getMchId(), merchant.getKeyPrivate(), merchant.getKeyPrivateCertPwd(), merchant.getKeyPublic(), merchant.getKeyPublicCert1(), merchant.getCertStoreType(), merchant.getNotifyUrl(), merchant.getReturnUrl(), merchant.getSignType(), merchant.getSeller(), merchant.getSubAppId(), merchant.getSubMchId(), merchant.getInputCharset(), merchant.getPayType(), merchant.isTest()};
        jdbcTemplate.update(insertSql, args);


    }

    /**
     * 更新商户
     *
     * @param merchant 商户信息
     */
    @Override
    public void updateMerchant(CommonPaymentPlatformMerchantDetails merchant) {
        Assert.isTrue(merchantExists(merchant.getDetailsId()), "商户信息不存在");
        Object[] args = new Object[]{merchant.getAppid(), merchant.getMchId(), merchant.getKeyPrivate(), merchant.getKeyPrivateCertPwd(), merchant.getKeyPublic(), merchant.getKeyPublicCert1(), merchant.getCertStoreType(), merchant.getNotifyUrl(), merchant.getReturnUrl(), merchant.getSignType(), merchant.getSeller(), merchant.getSubAppId(), merchant.getSubMchId(), merchant.getInputCharset(), merchant.getPayType(), merchant.isTest(), merchant.getDetailsId()};
        jdbcTemplate.update(updateSql, args);
    }

    /**
     * 删除商户
     *
     * @param id 商户id
     */
    @Override
    public void deleteMerchant(String id) {
        jdbcTemplate.update(deleteSql, id);
    }

    /**
     * 检查商户是否存在
     *
     * @param merchantId 商户id
     * @return 检查商户是否存在
     */
    @Override
    public boolean merchantExists(String merchantId) {
        List<String> ids = jdbcTemplate.queryForList(existsSql, String.class, merchantId);
        if (ids.size() > 0){
            throw new IncorrectResultSizeDataAccessException("出现重复的支付商户id", 1);
        }

        return ids.size() == 1;
    }

    /**
     * 通过支付商户id加载对应的商户信息列表
     *
     * @param merchantId 支付商户id
     * @return 商户信息列表
     */
    @Override
    public CommonPaymentPlatformMerchantDetails loadMerchantByMerchantId(String merchantId) {
       return jdbcTemplate.queryForObject(findByIdSql, new RowMapper<CommonPaymentPlatformMerchantDetails>() {
            @Override
            public CommonPaymentPlatformMerchantDetails mapRow(ResultSet rs, int i) throws SQLException {
                CommonPaymentPlatformMerchantDetails details = new CommonPaymentPlatformMerchantDetails();
                int index = 1;
                details.setDetailsId(rs.getString(index++));
                details.setAppid(rs.getString(index++));
                details.setMchId(rs.getString(index++));
                details.setKeyPrivate(rs.getString(index++));
                details.setKeyPrivateCertPwd(rs.getString(index++));
                details.setKeyPublic(rs.getString(index++));
                details.setKeyPublicCert1(rs.getString(index++));
                details.setCertStoreType(CertStoreType.valueOf(rs.getString(index++)));
                details.setNotifyUrl(rs.getString(index++));
                details.setReturnUrl(rs.getString(index++));
                details.setSignType(rs.getString(index++));
                details.setSeller(rs.getString(index++));
                details.setSubAppId(rs.getString(index++));
                details.setSubMchId(rs.getString(index++));
                details.setInputCharset(rs.getString(index++));
                details.setPayType(rs.getString(index++));
                details.setTest(rs.getBoolean(index++));
                return details;
            }
        }, merchantId);

    }

    public String getFindByIdSql() {
        return findByIdSql;
    }

    public void setFindByIdSql(String findByIdSql) {
        this.findByIdSql = findByIdSql;
    }

    public String getInsertSql() {
        return insertSql;
    }

    public void setInsertSql(String insertSql) {
        this.insertSql = insertSql;
    }

    public String getUpdateSql() {
        return updateSql;
    }

    public void setUpdateSql(String updateSql) {
        this.updateSql = updateSql;
    }

    public String getDeleteSql() {
        return deleteSql;
    }

    public void setDeleteSql(String deleteSql) {
        this.deleteSql = deleteSql;
    }

    public String getExistsSql() {
        return existsSql;
    }

    public void setExistsSql(String existsSql) {
        this.existsSql = existsSql;
    }
}
