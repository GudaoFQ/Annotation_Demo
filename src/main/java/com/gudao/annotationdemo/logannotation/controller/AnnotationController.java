package com.gudao.annotationdemo.logannotation.controller;

import com.gudao.annotationdemo.logannotation.annotationclass.OpLog;
import com.gudao.annotationdemo.logannotation.annotationvo.OpType;
import com.gudao.annotationdemo.logannotation.classvo.Order;
import org.springframework.web.bind.annotation.*;

/**
 * Author : GuDao
 * 2020-12-07
 */
@RestController
@RequestMapping("/api/v1/annotation")
public class AnnotationController {

    /**
     * 直接获取请求中的id值
     *
     * @param id id
     * @return {@link String}
     */
    @PostMapping("/annotationTest")
    @OpLog(opType = OpType.QUERY, opItem = "查询", opItemIdExpression = "#id")
    public String annotationTest(@RequestParam("id") String id) {
        throw new RuntimeException("运行异常");
    }

    /**
     * 获取实体类中的id值
     *
     * @param order 订单实体
     * @return {@link Order}
     */
    @PostMapping("/annotationOrderTest")
    @OpLog(opType = OpType.QUERY, opItem = "查询", opItemIdExpression = "#order.id")
    public Order annotationOrderTest(@RequestBody Order order) {
        System.out.println(order.toString());
        return order;
    }
}
