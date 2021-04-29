package org.example.java_web.servlet.request.upload;

import java.io.IOException;

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
@WebServlet("/upload3.do")
public class Upload3Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Part part = request.getPart("filename");
        String fileName = getFilename(part);
        part.write("d:/" + fileName);
    }

    private String getFilename(Part part) { // 取得上传文件名
        String header = part.getHeader("Content-Disposition");
        return header.substring(header.indexOf("filename=\"") + 10, header.lastIndexOf("\""));
    }
}
