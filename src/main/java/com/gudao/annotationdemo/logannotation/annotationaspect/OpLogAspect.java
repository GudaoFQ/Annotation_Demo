package com.gudao.annotationdemo.logannotation.annotationaspect;

import com.gudao.annotationdemo.logannotation.annotationclass.OpLog;
import com.gudao.annotationdemo.logannotation.annotationvo.OpType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * OpLog的切面处理类，用于通过注解获取日志信息，进行日志记录
 * 此方法就是使用ExpressionParser来解析sple表达式【opLog.opItemIdExpression的参数】
 *
 * 在SpEL中访问List集合的元素：#list[index]
 * 在SpEL中访问Map集合的元素：#map[key]
 *
 * Author : GuDao
 * 2020-12-03
 */
@Aspect
@Component
public class OpLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpLogAspect.class);

    @Autowired
    private HttpServletRequest request;

    @Pointcut(value = "@annotation(com.gudao.annotationdemo.logannotation.annotationclass.OpLog)")
    private void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object log(ProceedingJoinPoint pjp) {

        Object response = new Object();

        // 获取注解切面中的方法
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        // 获取方法上的注解
        OpLog annotation = method.getAnnotation(OpLog.class);


        try {
            // 通过反射执行目标对象的连接点处的方法【执行OpLog注解下的方法】
            response = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        if (
            // 判断oplog中的opItemIdExpression是否为空，存在值返回true，否则返回false
                Optional.ofNullable(annotation.opItemIdExpression()).isPresent()
        ) {
            // ExpressionParser接口，负责解析（parse）表达式字符串。该接口的实例负责解析一个SpEL表达式，返回一个Expression对象。
            SpelExpressionParser parser = new SpelExpressionParser();
            // 解析annotation.opItemIdExpression()中的信息
            Expression expression = parser.parseExpression(annotation.opItemIdExpression());

            // 代表计算表达式值的上下文。当SpEL表达式中含有变量时，程序将需要使用该API来计算表达式的值。Expression实例代表一个表达式，它包含了如下方法用于计算，得到表达式的值。
            EvaluationContext context = new StandardEvaluationContext();

            // 获取ProceedingJoinPoint中的参数值【切面方法中的形参值】
            Object[] args = pjp.getArgs();

            // 获取运行时参数的名称
            LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
            String[] parameterNames = discoverer.getParameterNames(method);

            // 将参数绑定到context上下文对象中
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }

            // 将方法的resp当做变量放到context中，变量名称为该类名转化为小写字母开头的驼峰形式
            if (Optional.ofNullable(response).isPresent()) {
                context.setVariable(response.getClass().getSimpleName(), response);
            }

            // 解析表达式，获取结果
            String value = String.valueOf(expression.getValue(context));

            // 执行日志记录
            handle(annotation.opType(), annotation.opItem(), value);
        }

        return response;
    }

    private void handle(OpType opType, String opItem, String value) {
        // 通过日志打印输出
        LOGGER.info("opType = {}, opItem = {}, opItemId = {}",opType, opItem, value);
    }
}
