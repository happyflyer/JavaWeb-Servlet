# [JavaWeb-Servlet](https://github.com/happyflyer/JavaWeb-Servlet)

## 1. Web 应用程序简介

## 2. 编写与设置 Servlet

### 2.1. 第一个 Servlet 程序

- Servlet

```java
@WebServlet("/hello.view")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // 设置响应内容类型器
        response.setContentType("text/html;charset=UTF-8");
        // 取得响应输出对象
        PrintWriter out = response.getWriter();
        // 取得 “请求参数”
        String name = request.getParameter("name");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>HelloServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>hello, " + name + "! </p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
```

- 配置

```xml
<web-app>
  <servlet>
    <servlet-name>hello</servlet-name>
    <servlet-class>org.example.java_web.servlet.hello.HelloServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>hello</servlet-name>
    <url-pattern>/hello.view</url-pattern>
  </servlet-mapping>
</web-app>
```

```java
@WebServlet("/hello.view")
```

- 访问

```http
/hello.view?name=zhangsan
```

```java
<p>hello, zhangsan!</p>
```

### 2.2. URL 模式设置

- requestURI = contextURI + servletURI + pathInfo

```java
out.println("<p>request.getRequestURI(): " + request.getRequestURI() + "</p>");
out.println("<p>request.getContextPath(): " + request.getContextPath() + "</p>");
out.println("<p>request.getServletPath(): " + request.getServletPath() + "</p>");
out.println("<p>request.getPathInfo(): " + request.getPathInfo() + "</p>");
```

```html
<p>request.getRequestURI(): /JavaWeb_Servlet_war/path.view</p>
<p>request.getContextPath(): /JavaWeb_Servlet_war</p>
<p>request.getServletPath(): /path.view</p>
<p>request.getPathInfo(): null</p>
```

## 3. 请求

### 3.1. 处理请求参数与标头

- `request.getHeaderNames()`

```java
Enumeration<String> names = request.getHeaderNames();
while (names.hasMoreElements()) {
  String name = names.nextElement();
  out.println("<p>" + name + ": " + request.getHeader(name) + "</p>");
}
```

```html
<p>host: localhost:8080</p>
<p>connection: keep-alive</p>
<p>cache-control: max-age=0</p>
<p>sec-ch-ua: " Not A;Brand";v="99", "Chromium";v="90", "Microsoft Edge";v="90"</p>
<p>sec-ch-ua-mobile: ?0</p>
<p>upgrade-insecure-requests: 1</p>
<p>user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.85 Safari/537.36 Edg/90.0.818.46</p>
<p>accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9</p>
<p>sec-fetch-site: none</p>
<p>sec-fetch-mode: navigate</p>
<p>sec-fetch-user: ?1</p>
<p>sec-fetch-dest: document</p>
<p>accept-encoding: gzip, deflate, br</p>
<p>accept-language: zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6</p>
```

- `request.getHeader()`

```java
out.println("<p>host: " + request.getHeader("host") + "</p>");
```

- `request.getHeaders()`

```java
Enumeration<String> uas = request.getHeaders("user-agent");
while (uas.hasMoreElements()) {
    String ua = uas.nextElement();
    out.println("<p>user-agent: " + ua + "</p>");
}
```

### 3.2. 请求参数编码处理

#### 3.2.1. get 请求

```java
response.setContentType("text/html;charset=UTF-8");
```

#### 3.2.2. post 请求

```java
request.setCharacterEncoding("UTF-8");
response.setContentType("text/html;charset=UTF-8");
```

### 3.3. 读取 Body 内容

```java
private String readBody(HttpServletRequest request) throws IOException {
    BufferedReader reader = request.getReader();
    String input;
    StringBuilder requestBody = new StringBuilder();
    while ((input = reader.readLine()) != null) {
        requestBody.append(input).append("<br>");
    }
    return requestBody.toString();
}
```

```html
<p>user=%E5%BC%A0%E4%B8%89&password=123456&btn=submit</p>
```

### 3.4. 获取上传文件

#### 3.4.1. upload1

- `form` 标签需要设置 `enctype="multipart/form-data"`

```html
<form action="upload1.do" method="post" enctype="multipart/form-data">
  <div class="form-group">
    <input id="file" type="file" name="filename" value="" />
  </div>
  <button class="btn btn-primary" name="btn" value="upload">上传</button>
</form>
```

```java
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
```

```java
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
```

```java
private class Position {
    int begin;
    int end;
    Position(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }
}
```

```java
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
```

```java
private String getFileName(String textBody) {
    // 截掉filename前面
    String fileName = textBody.substring(textBody.indexOf("filename=\"") + 10);
    // 截掉 换行符后面的内容
    fileName = fileName.substring(0, fileName.indexOf("\n"));
    // 截掉文件所在目录 和最后一个 "
    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.indexOf("\""));
    return fileName;
}
```

```java
private void writeTo(String fileName, byte[] body, Position p) throws IOException {
    FileOutputStream fos = new FileOutputStream(fileName);
    fos.write(body, p.begin, (p.end - p.begin));
    fos.flush();
    fos.close();
}
```

#### 3.4.2. upload2

- Tomcat 中必须设置 `MultipartConfig` 标注才能使用 `getPart()` 相关的 API

```java
@MultipartConfig
@WebServlet("/upload2.do")
public class Upload2Servlet extends HttpServlet {
    // ...
}
```

```java
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
```

```java
private String getFilename(Part part) { // 取得上传文件名
    String header = part.getHeader("Content-Disposition");
    return header.substring(header.indexOf("filename=\"") + 10, header.lastIndexOf("\""));
}
```

```java
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
```

#### 3.4.3. upload3

```java
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    Part part = request.getPart("filename");
    String fileName = getFilename(part);
    part.write("d:/" + fileName);
}
```

```java
private String getFilename(Part part) { // 取得上传文件名
    String header = part.getHeader("Content-Disposition");
    return header.substring(header.indexOf("filename=\"") + 10, header.lastIndexOf("\""));
}
```

#### 3.4.4. upload4

```java
@MultipartConfig(location = "d:/")
@WebServlet("/upload4.do")
public class Upload4Servlet extends HttpServlet {
    // ...
}
```

```java
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    for (Part part : request.getParts()) {
        if (part.getName().startsWith("file")) {
            String fileName = getFilename(part);
            part.write("d:/" + fileName);
        }
    }
}
```

### 3.5. 调派请求

#### 3.5.1. include

```java
@WebServlet("/some.view")
public class SomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("Some do one ... ");
        RequestDispatcher rd = request.getRequestDispatcher("other.view");
        rd.include(request, response);
        out.println("Some do two ... ");
        out.close();
    }
}
```

```java
@WebServlet("/other.view")
public class OtherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("Other do one ... ");
        // 这里不要关闭 out，否则页面将无法继续写入 some.view 后面的内容
    }
}
```

#### 3.5.2. forward

```java
public class HelloModel {
    private final Map<String, String> messages = new HashMap<>();
    public HelloModel() {
        messages.put("zhangsan", "hello");
        messages.put("lisi", "welcome");
        messages.put("wangwu", "hi");
    }
    public String doHello(String user) {
        String massage = messages.get(user);
        return massage + ", " + user + "!";
    }
}
```

```java
@WebServlet("/request/hello.do")
public class HelloController extends HttpServlet {
    private final HelloModel helloModel = new HelloModel();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String name = request.getParameter("user");
        String message = helloModel.doHello(name);
        request.setAttribute("message", message);
        request.getRequestDispatcher("hello.view").forward(request, response);
    }
}
```

```java
@WebServlet("/request/hello.view")
public class HelloView extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String user = request.getParameter("user");
        String message = (String) request.getAttribute("message");
        String htmlTemplate = ""
                + "<html>"
                + "<head>"
                + "<title>%s</title>"
                + "</head>"
                + "<body>"
                + "<p>%s</p>"
                + "</body>"
                + "</html>";
        String html = String.format(htmlTemplate, user, message);
        response.getWriter().print(html);
    }
}
```

```http
/request/hello.do?user=zhangsan
```

```html
<p>hello, zhangsan!</p>
```

## 4. 响应

### 4.1. 输出字符

```java
response.setContentType("text/html;charset=UTF-8");
```

```java
PrintWriter out = response.getWriter();
```

### 4.2. 输出二进制字符

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app>
  <locale-encoding-mapping-list>
    <locale-encoding-mapping>
      <locale>zh_CN</locale>
      <encoding>UTF-8</encoding>
    </locale-encoding-mapping>
  </locale-encoding-mapping-list>
  <mime-mapping>
    <extension>pdf</extension>
    <mime-type>application/pdf</mime-type>
  </mime-mapping>
</web-app>
```

```java
@WebServlet("/download.do")
public class DownloadServlet extends HttpServlet {
    // ...
}
```

```java
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
    doPost(request, response);
}
```

```java
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
```

```java
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
```

### 4.3. 重定向

```java
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
```

## 5. 基本会话管理

### 5.1. 使用隐藏域

```java
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
```

```html
<form action="question.view" method="post">
  问题三：<input type="text" name="p2q1" /><br />
  <input type="hidden" name="p1q1" value="qqq" />
  <input type="hidden" name="p1q2" value="kkk" />
  <input type="submit" name="page" value="完成" />
</form>
```

### 5.2. 使用 Cookie

```html
<form action="cookie/login.do" method="post">
  <div class="form-group">
    <label for="username">用户名: </label>
    <input id="username" class="form-control" type="text" name="username" value="" />
  </div>
  <div class="form-group">
    <label for="password">密码: </label>
    <input id="password" class="form-control" type="password" name="password" value="" />
  </div>
  <div class="form-group">
    <label for="login">记住我: </label>
    <input id="login" class="form-control" type="checkbox" name="login" value="auto" />
  </div>
  <button class="btn btn-primary" name="btn" value="submit">提交</button>
</form>
```

```java
@WebServlet("/cookie/login.do")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String trueUsername = "zhangsan";
        String truePassword = "123456";
        String loginMode = "auto";
        if (trueUsername.equals(username) && truePassword.equals(password)) {
            String login = request.getParameter("login");
            if (loginMode.equals(login)) {
                Cookie cookie = new Cookie("user", username);
                cookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(cookie);
            }
            request.setAttribute("username", username);
            request.getRequestDispatcher("user.view").forward(request, response);
        } else {
            response.sendRedirect("../cookie_login.html");
        }
    }
}
```

```java
@WebServlet("/cookie/user.view")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String usernameKey = "username";
        if (request.getAttribute(usernameKey) == null) {
            response.sendRedirect("../cookie_login.html");
        }
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>UserServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>" + request.getAttribute(usernameKey) + "</p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
```

```properties
Set-Cookie: user=zhangsan; Max-Age=604800; Expires=date;
```

### 5.3. 使用 URL 重写

```java
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
```

## 6. HttpSession

### 6.1. 实现隐藏域

```java
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
        HttpSession session = request.getSession();
        session.setAttribute("p1q1", plq1);
        session.setAttribute("p1q2", plq2);
        out.println("问题三：<input type='text' name='p2q1'><br>");
        out.println("<input type='hidden' name='p1q1' value='" + plq1 + "'>");
        out.println("<input type='hidden' name='p1q2' value='" + plq2 + "'>");
        out.println("<input type='submit' name='page' value='完成'>");
    } else if (finish.equals(page)) {
        HttpSession session = request.getSession();
        out.println(session.getAttribute("p1q1") + "<br>");
        out.println(session.getAttribute("p1q2") + "<br>");
        out.println(request.getParameter("p2q1") + "<br>");
    }
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
    out.close();
}
```

### 6.2. 实现登录

```java
@WebServlet("/session/login.do")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String trueUsername = "zhangsan";
        String truePassword = "123456";
        if (trueUsername.equals(username) && truePassword.equals(password)) {
            request.getSession().setAttribute("username", username);
            request.getRequestDispatcher("user.view").forward(request, response);
        } else {
            response.sendRedirect("../session_login.html");
        }
    }
}
```

```java
@WebServlet("/session/user.view")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String usernameKey = "username";
        if (request.getSession().getAttribute(usernameKey) == null) {
            response.sendRedirect("../session_login.html");
        }
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>UserServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>" + request.getSession().getAttribute(usernameKey) + "</p>");
        out.println("<a href='logout.view'>注销</a>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
```

```java
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
```

```xml
<web-app>
  <session-config>
    <!-- minutes -->
    <session-timeout>30</session-timeout>
    <cookie-config>
      <name>sid</name>
      <http-only>true</http-only>
    </cookie-config>
  </session-config>
</web-app>
```

### 6.3. 实现 URL 重写

```java
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
```
