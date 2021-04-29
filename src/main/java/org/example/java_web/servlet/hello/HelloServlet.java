package org.example.java_web.servlet.hello;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 第一个 Servlet 程序
 *
 * @author lifei
 */
@WebServlet("/hello.view")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // 设置响应内容类型器
        response.setContentType("text/html;charset=UTF-8");
        // 取得响应输出对象
        PrintWriter out = response.getWriter();
        // 取得 “请求参数”
        String name = request.getParameter("name");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>HelloServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>hello, " + name + "! </p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
