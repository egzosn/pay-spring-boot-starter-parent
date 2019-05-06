package com.egzosn.pay.spring.boot.core;

/**
 * 用于构建对象的接口
 *
 * @param <O> 正在构建的对象的类型

 * @author egan
 *         <pre>
 *              email egzosn@gmail.com
 *
 *              date 2019/05/06 19:27.
 *         </pre>
 */
public interface PayBuilder<O> {


    /**
     * 构建对象并返回它或null。
     *
     * @return 如果实现允许，则要构建的对象或null。
     * @throws Exception 如果在构建对象时发生错误
     */
    O build() throws Exception;
}