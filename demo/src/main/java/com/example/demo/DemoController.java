package com.example.demo;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

@RestController
@Log4j2
public class DemoController {
    @Autowired
    private HttpServletRequest request;

    @GetMapping("/feign-test")
    public String test() {
        return "这是一个测试信息";
    }


    @GetMapping("getInfo")
    public String getInfo(@RequestHeader("Authorization") String token) {
        System.out.println(token);
        return token;
    }

    @GetMapping("getHeader")
    public String getHeader() {
        System.out.println("*******************************************");
        Enumeration<String> e =request.getHeaderNames();
        while (e.hasMoreElements()){
            String s = e.nextElement();
            System.out.println(s+":"+request.getHeader(s));
        }
        return "hello";
    }


    @PostMapping("info")
    public ResponseEntity<Boolean> info() {
        Map<String, String[]> parameters = request.getParameterMap();

        parameters.forEach((key, values) -> {
            System.out.print(key + ": ");
            for (int i = 0; i < values.length; i++) {
                System.out.print(i + " - " + values[i] + " ");
            }
            System.out.println();
        });

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
