package org.example.java_web.servlet.request.upload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * getPart()、getParts() 取得上传文件
 * Tomcat 中必须设置 MultipartConfig 标注才能使用 getPart() 相关的 API
 *
 * @author lifei
 */
@MultipartConfig
@WebServlet("/upload2.do")
public class Upload2Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        // 使用 getPart() 取得 Part 对象
        Part part = request.getPart("filename");
        String fileName = getFilename(part);
        writeTo("d:/" + fileName, part);
    }

    private String getFilename(Part part) { // 取得上传文件名
        String header = part.getHeader("Content-Disposition");
        return header.substring(header.indexOf("filename=\"") + 10, header.lastIndexOf("\""));
    }

    private void writeTo(String fileName, Part part) throws IOException {
        InputStream in = part.getInputStream();
        OutputStream out = new FileOutputStream(fileName);
        byte[] buffer = new byte[1024];
        int length = -1;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();
    }
}
