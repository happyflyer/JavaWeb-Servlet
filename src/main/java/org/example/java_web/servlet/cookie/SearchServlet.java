package org.example.java_web.servlet.cookie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用 URL 重写
 *
 * @author lifei
 */
@WebServlet("/cookie/search.do")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>SearchServlet</title>");
        out.println("</head>");
        out.println("<body>");
        int perPage = 10;
        int totalPages = 20;
        String page = request.getParameter("page");
        if (page == null) {
            page = "1";
        }
        int count = Integer.parseInt(page);
        int begin = 10 * count - 9;
        int end = 10 * count;
        out.println("<p>从 " + begin + " 到  " + end + " 的搜索结果</p>");
        out.println("<ul>");
        String line = "<li>搜索结果: %d</li>";
        for (int i = 0; i < perPage; i++) {
            out.println(String.format(line, begin + i));
        }
        out.println("</ul>");
        for (int i = 1; i <= totalPages; i++) {
            if (i == count) {
                out.print(i);
                continue;
            }
            out.println("<a href='search.do?page=" + i + "'>" + i + "</a>");
        }
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
