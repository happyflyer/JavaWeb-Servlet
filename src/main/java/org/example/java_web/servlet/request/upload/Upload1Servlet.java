package org.example.java_web.servlet.request.upload;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * getReader()、getInputStream() 读取 Body 内容
 *
 * @author lifei
 */
@WebServlet("/upload1.do")
public class Upload1Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // 读取请求的 body
        byte[] body = readBody(request);
        // 取得所有 body 内容的字符串显示
        String textBody = new String(body, StandardCharsets.ISO_8859_1);
        // 取得上传的文件名称
        String fileName = getFileName(textBody);
        // 取得文件开始和结束位置
        Position p = getFilePosition(request, textBody);
        // 输出至文件
        writeTo("d:/" + fileName, body, p);
    }

    private class Position {
        int begin;
        int end;

        Position(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }
    }

    private byte[] readBody(HttpServletRequest request) throws IOException {
        int formDataLength = request.getContentLength();
        // 获取 ServletInputStream 对象
        DataInputStream dataStream = new DataInputStream(request.getInputStream());
        byte[] body = new byte[formDataLength];
        int totalBytes = 0;
        while (totalBytes < formDataLength) {
            int bytes = dataStream.read(body, totalBytes, formDataLength);
            totalBytes += bytes;
        }
        return body;
    }

    private Position getFilePosition(HttpServletRequest request, String textBody) {
        // 取得文件区段边界位置
        String contentType = request.getContentType();
        String boundaryText = contentType.substring(contentType.lastIndexOf("=") + 1);
        // 取得实际上传文件的起始位置和结束位置
        int pos = textBody.indexOf("filename\"");
        pos = textBody.indexOf("\n", pos) + 1;
        pos = textBody.indexOf("\n", pos) + 1;
        pos = textBody.indexOf("\n", pos) + 1;
        int boundaryLoc = textBody.indexOf(boundaryText, pos) - 4;
        int begin = ((textBody.substring(0, pos).getBytes(StandardCharsets.ISO_8859_1)).length);
        int end = ((textBody.substring(0, boundaryLoc).getBytes(StandardCharsets.ISO_8859_1)).length);
        return new Position(begin, end);
    }

    private String getFileName(String textBody) {
        // 截掉filename前面
        String fileName = textBody.substring(textBody.indexOf("filename=\"") + 10);
        // 截掉 换行符后面的内容
        fileName = fileName.substring(0, fileName.indexOf("\n"));
        // 截掉文件所在目录 和最后一个 "
        fileName = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.indexOf("\""));
        return fileName;
    }

    private void writeTo(String fileName, byte[] body, Position p) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(body, p.begin, (p.end - p.begin));
        fos.flush();
        fos.close();
    }
}
