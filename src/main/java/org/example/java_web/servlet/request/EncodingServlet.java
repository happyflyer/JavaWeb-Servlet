package org.example.java_web.servlet.request;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求参数编码处理
 *
 * @author lifei
 */
@WebServlet("/encoding.view")
public class EncodingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>EncodingServlet</title>");
        out.println("</head>");
        out.println("<body>");
        String name = request.getParameter("name1");
        out.println("<p>" + name + "</p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>EncodingServlet</title>");
        out.println("</head>");
        out.println("<body>");
        String name = request.getParameter("name2");
        out.println("<p>" + name + "</p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
