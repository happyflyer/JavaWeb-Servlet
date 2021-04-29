package org.example.java_web.servlet.response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用 getOutputStream() 输出二进制字符
 *
 * @author lifei
 */
@WebServlet("/download.do")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        // 设置内容类型
        response.setContentType("application/pdf");
        // 输入串流
        InputStream in = getServletContext().getResourceAsStream("WEB-INF/content.pdf");
        // 输出串流
        OutputStream out = response.getOutputStream();
        // 读取文件并输出到浏览器
        writeBytes(in, out);
    }

    private void writeBytes(InputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();
    }
}
