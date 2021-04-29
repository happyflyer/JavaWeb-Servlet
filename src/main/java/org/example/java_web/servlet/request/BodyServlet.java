package org.example.java_web.servlet.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * getReader()、getInputStream() 读取 Body 内容
 *
 * @author lifei
 */
@WebServlet("/body.view")
public class BodyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>BodyServlet</title>");
        out.println("</head>");
        out.println("<body>");
        String body = readBody(request);
        out.println("<p>" + body + " </p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    private String readBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String input;
        StringBuilder requestBody = new StringBuilder();
        while ((input = reader.readLine()) != null) {
            requestBody.append(input).append("<br>");
        }
        return requestBody.toString();
    }
}
