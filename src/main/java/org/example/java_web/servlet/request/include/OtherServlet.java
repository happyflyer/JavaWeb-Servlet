package org.example.java_web.servlet.request.include;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用 RequestDispatcher 调派请求
 *
 * @author lifei
 */
@WebServlet("/other.view")
public class OtherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("Other do one ... ");
        // 这里不要关闭 out，否则页面将无法继续写入 some.view 后面的内容
    }
}
