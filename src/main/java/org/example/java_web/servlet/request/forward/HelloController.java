package org.example.java_web.servlet.request.forward;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用 RequestDispatcher 调派请求
 * 请求范围属性
 * HttpServletRequest.setAttribute()
 * HttpServletRequest.getAttribute()
 * HttpServletRequest.getAttributeNames()
 * HttpServletRequest.removeAttribute()
 *
 * @author lifei
 */
@WebServlet("/request/hello.do")
public class HelloController extends HttpServlet {
    private final HelloModel helloModel = new HelloModel();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String name = request.getParameter("user");
        String message = helloModel.doHello(name);
        request.setAttribute("message", message);
        request.getRequestDispatcher("hello.view").forward(request, response);
    }
}
