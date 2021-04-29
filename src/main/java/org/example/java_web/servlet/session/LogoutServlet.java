package org.example.java_web.servlet.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 使用 HttpSession
 *
 * @author lifei
 */
@WebServlet("/session/logout.view")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        session.invalidate();
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>LogoutServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>" + username + " 已注销</p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
