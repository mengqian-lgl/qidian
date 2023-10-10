package com.lgl.qidian.util;

import com.alibaba.fastjson.JSON;
import com.lgl.qidian.entity.web_state.FastWebstate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @auther 刘广林
 */
public class ResponseUtils {

    public static void message(FastWebstate fastWebstate,
                               HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        String string = JSON.toJSONString(fastWebstate);
        writer.write(string);
        writer.flush();
        writer.close();
    }

    public static void message(int state, String message,HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("state",String.valueOf(state));
        hashMap.put("message",message);
        String jsonString = JSON.toJSONString(hashMap);
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }

}
