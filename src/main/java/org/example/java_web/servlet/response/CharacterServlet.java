package org.example.java_web.servlet.response;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用 getWriter() 输出字符
 *
 * @author lifei
 */
@WebServlet("/character.view")
public class CharacterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>CharacterServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("联系人：<a href='mailto:" + request.getParameter("email") + "'>" + request.getParameter("user") + "</a>");
        out.println("<ul>");
        String key = "type";
        for (String type : request.getParameterValues(key)) {
            out.println("<li>" + type + "</li>");
        }
        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
