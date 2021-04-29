package org.example.java_web.servlet.request;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理请求参数与标头
 * HttpServletRequest.getParameter()
 * HttpServletRequest.getParameterValues()
 * HttpServletRequest.getParameterMap()
 * HttpServletRequest.getHeader()
 * HttpServletRequest.getHeaders()
 * HttpServletRequest.getHeaderNames()
 *
 * @author lifei
 */
@WebServlet("/header.view")
public class HeaderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        // 请求头信息中所有字段
        out.println("<html>");
        out.println("<head>");
        out.println("<title>HeaderServlet</title>");
        out.println("</head>");
        out.println("<body>");
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            out.println("<p>" + name + ": " + request.getHeader(name) + "</p>");
        }
        out.println("<p>host: " + request.getHeader("host") + "</p>");
        Enumeration<String> uas = request.getHeaders("user-agent");
        while (uas.hasMoreElements()) {
            String ua = uas.nextElement();
            out.println("<p>user-agent: " + ua + "</p>");
        }
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
