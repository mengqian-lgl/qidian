package com.lgl.qidian.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @auther 刘广林
 */
@Service("serviceTest")
public class Test {
    public void test() throws IOException {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        PrintWriter writer = response.getWriter();
        writer.write("测试是不是同一个线程的请求");
        writer.flush();
        System.out.println("123");
        writer.close();

    }
}
