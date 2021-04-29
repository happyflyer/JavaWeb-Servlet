package org.example.java_web.servlet.hello;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * URL 模式设置
 * requestURI = contextURI + servletURI + pathInfo
 *
 * @author lifei
 */
@WebServlet("/path.view")
public class PathServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>PathServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>request.getRequestURI(): " + request.getRequestURI() + "</p>");
        out.println("<p>request.getContextPath(): " + request.getContextPath() + "</p>");
        out.println("<p>request.getServletPath(): " + request.getServletPath() + "</p>");
        out.println("<p>request.getPathInfo(): " + request.getPathInfo() + "</p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
