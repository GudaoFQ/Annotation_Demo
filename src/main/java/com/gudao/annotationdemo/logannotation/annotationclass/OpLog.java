package com.gudao.annotationdemo.logannotation.annotationclass;

import com.gudao.annotationdemo.logannotation.annotationvo.OpType;

import java.lang.annotation.*;

/**
 *
 * 日志注解类的参数定义
 *
 * Author : GuDao
 * 2020-12-03
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpLog {

    /**
     * 业务操作类型，如新增、删除、修改
     *
     * @return {@link OpType}
     */
    public OpType opType() default OpType.QUERY;

    /**
     * 业务对象名称，如订单、库存、价格
     *
     * @return {@link String}
     */
    public String opItem();


    /**
     * 业务对象编号表达式，描述了如何获取订单号的表达式
     *
     * @return {@link String}
     */
    public String opItemIdExpression();
}
