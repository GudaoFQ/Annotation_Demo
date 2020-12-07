package com.gudao.annotationdemo;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@Aspect
public class AnnotationDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnotationDemoApplication.class, args);
    }

}
