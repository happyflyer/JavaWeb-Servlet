package org.example.java_web.servlet.request.forward;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用 RequestDispatcher 调派请求
 *
 * @author lifei
 */
public class HelloModel {
    private final Map<String, String> messages = new HashMap<>();

    public HelloModel() {
        messages.put("zhangsan", "hello");
        messages.put("lisi", "welcome");
        messages.put("wangwu", "hi");
    }

    public String doHello(String user) {
        String massage = messages.get(user);
        return massage + ", " + user + "!";
    }
}
