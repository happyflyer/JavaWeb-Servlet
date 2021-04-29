package org.example.java_web.servlet.response;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用 sendRedirect()、sendError()
 *
 * @author lifei
 */
@WebServlet("/redirect.do")
public class RedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.sendRedirect("https://www.baidu.com");
        // String msg = "bad request";
        // response.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
    }
}
