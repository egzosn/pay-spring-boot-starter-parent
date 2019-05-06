package com.egzosn.pay.spring.boot.core.merchant;

/**
 * 商户不存在异常
 * @author egan
 *         <pre>
 *         email egzosn@gmail.com
 *         date  2019/4/6 16:43.
 *         </pre>
 */
public class MerchantNotFoundException extends RuntimeException {



    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param merchantId the detail merchant. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public MerchantNotFoundException(String merchantId) {
        super(merchantId);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param merchantId the detail merchant (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public MerchantNotFoundException(String merchantId, Throwable cause) {
        super(merchantId, cause);
    }

    /**
     * 获取商户id
     * @return 商户id
     */
    public String getMerchantId(){
        return getMessage();
    }


}
