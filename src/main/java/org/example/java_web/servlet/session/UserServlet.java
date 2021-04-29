package org.example.java_web.servlet.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用 HttpSession
 *
 * @author lifei
 */
@WebServlet("/session/user.view")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String usernameKey = "username";
        if (request.getSession().getAttribute(usernameKey) == null) {
            response.sendRedirect("../session_login.html");
        }
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>UserServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>" + request.getSession().getAttribute(usernameKey) + "</p>");
        out.println("<a href='logout.view'>注销</a>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
