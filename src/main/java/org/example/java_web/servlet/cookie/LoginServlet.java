package org.example.java_web.servlet.cookie;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用 Cookie
 *
 * @author lifei
 */
@WebServlet("/cookie/login.do")
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
        String loginMode = "auto";
        if (trueUsername.equals(username) && truePassword.equals(password)) {
            String login = request.getParameter("login");
            if (loginMode.equals(login)) {
                Cookie cookie = new Cookie("user", username);
                cookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(cookie);
            }
            request.setAttribute("username", username);
            request.getRequestDispatcher("user.view").forward(request, response);
        } else {
            response.sendRedirect("../cookie_login.html");
        }
    }
}
