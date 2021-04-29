package org.example.java_web.servlet.request.include;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
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
@WebServlet("/some.view")
public class SomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("Some do one ... ");
        RequestDispatcher rd = request.getRequestDispatcher("other.view");
        rd.include(request, response);
        out.println("Some do two ... ");
        out.close();
    }
}
