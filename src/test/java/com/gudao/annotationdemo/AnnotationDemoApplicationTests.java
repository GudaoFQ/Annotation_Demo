package com.gudao.annotationdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class AnnotationDemoApplicationTests {

    @Test
    void contextLoads() {
        Rest rest = new Rest();
        //rest.setName("test");
        boolean optionalS = Optional.ofNullable(rest)
                .map(x -> x.getName()).isPresent();
        System.out.println(optionalS);
    }



}

class Rest{
    String name;

    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }
}