package org.example.java_web.servlet.request.forward;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用 RequestDispatcher 调派请求
 *
 * @author lifei
 */
@WebServlet("/request/hello.view")
public class HelloView extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String user = request.getParameter("user");
        String message = (String) request.getAttribute("message");
        String htmlTemplate = ""
                + "<html>"
                + "<head>"
                + "<title>%s</title>"
                + "</head>"
                + "<body>"
                + "<p>%s</p>"
                + "</body>"
                + "</html>";
        String html = String.format(htmlTemplate, user, message);
        response.getWriter().print(html);
    }
}
