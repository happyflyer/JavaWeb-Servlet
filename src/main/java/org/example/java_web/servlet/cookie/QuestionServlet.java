package org.example.java_web.servlet.cookie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用隐藏域
 *
 * @author lifei
 */
@WebServlet("/cookie/question.view")
public class QuestionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>QuestionServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<form action='question.view' method='post'>");
        String page = request.getParameter("page");
        String nextPage = "下一页";
        String finish = "完成";
        if (page == null) {
            out.println("问题一：<input type='text' name='p1q1'><br>");
            out.println("问题一：<input type='text' name='p1q2'><br>");
            out.println("<input type='submit' name='page' value='下一页'>");
        } else if (nextPage.equals(page)) {
            String plq1 = request.getParameter("p1q1");
            String plq2 = request.getParameter("p1q2");
            out.println("问题三：<input type='text' name='p2q1'><br>");
            out.println("<input type='hidden' name='p1q1' value='" + plq1 + "'>");
            out.println("<input type='hidden' name='p1q2' value='" + plq2 + "'>");
            out.println("<input type='submit' name='page' value='完成'>");
        } else if (finish.equals(page)) {
            out.println(request.getParameter("p1q1") + "<br>");
            out.println(request.getParameter("p1q2") + "<br>");
            out.println(request.getParameter("p2q1") + "<br>");
        }
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
