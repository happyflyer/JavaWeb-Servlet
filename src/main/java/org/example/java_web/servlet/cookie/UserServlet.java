package org.example.java_web.servlet.cookie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用 Cookie
 *
 * @author lifei
 */
@WebServlet("/cookie/user.view")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String usernameKey = "username";
        if (request.getAttribute(usernameKey) == null) {
            response.sendRedirect("cookie_login.html");
        }
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>UserServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>" + request.getAttribute(usernameKey) + "</p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
