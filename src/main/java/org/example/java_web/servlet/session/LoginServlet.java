package org.example.java_web.servlet.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 使用 HttpSession
 *
 * @author lifei
 */
@WebServlet("/session/login.do")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String trueUsername = "zhangsan";
        String truePassword = "123456";
        if (trueUsername.equals(username) && truePassword.equals(password)) {
            request.getSession().setAttribute("username", username);
            request.getRequestDispatcher("user.view").forward(request, response);
        } else {
            response.sendRedirect("../session_login.html");
        }
    }
}
