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
@WebServlet("/session/counter.do")
public class CounterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        int count = 0;
        String countStr = "count";
        HttpSession session = request.getSession();
        if (session.getAttribute(countStr) != null) {
            Integer c = (Integer) session.getAttribute(countStr);
            count = c + 1;
        }
        session.setAttribute(countStr, count);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>CounterServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>Servlet Count: " + count + " </p>");
        out.println("<a href='" + response.encodeURL("counter") + ".do'>递增</a>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
