#  Lộ Trình Học Java Web: Servlet & JSP → Spring MVC → Spring Boot REST API

> **Dành cho người mới bắt đầu** — Tài liệu này giúp bạn hiểu rõ từng tầng công nghệ, tại sao chúng tồn tại, và chúng kết nối với nhau như thế nào.

---

##  Mục Lục

1. [Bức tranh toàn cảnh](#1-bức-tranh-toàn-cảnh)
2. [Java Servlet](#2-java-servlet)
3. [JSP (JavaServer Pages)](#3-jsp-javaserver-pages)
4. [Mô hình MVC với Servlet + JSP](#4-mô-hình-mvc-với-servlet--jsp)
5. [Spring MVC](#5-spring-mvc)
6. [Spring Boot REST API](#6-spring-boot-rest-api)
7. [So sánh tổng quan](#7-so-sánh-tổng-quan)
8. [Cấu trúc thư mục chuẩn](#8-cấu-trúc-thư-mục-chuẩn)
9. [Lộ trình học khuyến nghị](#9-lộ-trình-học-khuyến-nghị)
10. [Các lỗi thường gặp](#10-các-lỗi-thường-gặp)

---

## 1. Bức Tranh Toàn Cảnh

Trước khi đi vào từng công nghệ, hãy hiểu **tại sao** chúng xuất hiện theo thứ tự này:

```
Thế hệ 1: Servlet thuần (Khó, nhiều code lặp)
      ↓
Thế hệ 2: Servlet + JSP (Tách logic và giao diện)
      ↓
Thế hệ 3: Spring MVC (Framework chuẩn, ít boilerplate)
      ↓
Thế hệ 4: Spring Boot REST API (Phát triển nhanh, microservice)
```

Mỗi thế hệ **giải quyết điểm yếu** của thế hệ trước. Hiểu điều này giúp bạn học có chiều sâu thay vì chỉ học cú pháp.

### Mô hình request-response cơ bản

```
Client (Browser/Mobile)
       │  HTTP Request (GET /products)
       ▼
  [Web Server / Servlet Container - Tomcat]
       │
       ▼
  [Ứng dụng Java của bạn]  ──→  [Database]
       │
       ▼
  HTTP Response (HTML / JSON)
       │
       ▼
Client nhận kết quả
```

> **Servlet Container** (Tomcat, Jetty, WildFly) là môi trường chạy ứng dụng Java Web. Nó quản lý vòng đời của Servlet, xử lý kết nối TCP, phân tích HTTP request và chuyển cho đúng Servlet.

---

## 2. Java Servlet

### 2.1 Servlet là gì?

**Servlet** là một class Java chạy trên server, nhận HTTP request và trả về HTTP response. Đây là **nền tảng của toàn bộ Java Web**.

```
HTTP Request ──→ Servlet Container ──→ Servlet.service()
                                              │
                               ┌──────────────┴──────────────┐
                               ▼                             ▼
                          doGet()                       doPost()
                    (xử lý GET request)           (xử lý POST request)
```

### 2.2 Vòng đời của Servlet

Servlet Container quản lý Servlet qua **3 giai đoạn**:

```
1. KHỞI TẠO (Initialization)
   └── Container gọi init() một lần duy nhất khi load Servlet

2. XỬ LÝ REQUEST (Request Handling)
   └── Container gọi service() → doGet() / doPost() mỗi khi có request

3. HỦY BỎ (Destruction)
   └── Container gọi destroy() khi Servlet bị unload (server tắt)
```

### 2.3 Cấu trúc một Servlet cơ bản

```java
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

// @WebServlet thay thế cho cấu hình trong web.xml
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    // Chạy một lần khi server khởi động
    @Override
    public void init() throws ServletException {
        System.out.println("Servlet được khởi tạo!");
    }

    // Xử lý HTTP GET request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Đọc tham số từ URL: /hello?name=Nam
        String name = request.getParameter("name");
        if (name == null) name = "World";

        // 2. Thiết lập kiểu nội dung trả về
        response.setContentType("text/html;charset=UTF-8");

        // 3. Ghi response
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><body>");
        out.println("<h1>Xin chào, " + name + "!</h1>");
        out.println("</body></html>");
    }

    // Xử lý HTTP POST request (ví dụ: submit form)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Xử lý logic đăng nhập...
        if ("admin".equals(username) && "123456".equals(password)) {
            // Tạo session
            HttpSession session = request.getSession();
            session.setAttribute("user", username);

            // Redirect về trang chủ
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            // Chuyển tiếp sang trang lỗi
            request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    // Chạy một lần khi server tắt
    @Override
    public void destroy() {
        System.out.println("Servlet bị hủy!");
    }
}
```

### 2.4 HttpServletRequest — Đọc dữ liệu từ client

```java
// Đọc tham số URL: /search?keyword=sofa&page=2
String keyword = request.getParameter("keyword");
String page    = request.getParameter("page");

// Đọc tất cả tham số cùng tên: /filter?color=red&color=blue
String[] colors = request.getParameterValues("color");

// Đọc header
String userAgent  = request.getHeader("User-Agent");
String authToken  = request.getHeader("Authorization");

// Đọc thông tin request
String method     = request.getMethod();        // "GET", "POST"...
String uri        = request.getRequestURI();    // "/products/detail"
String contextPath = request.getContextPath(); // "/myapp"

// Làm việc với Session
HttpSession session = request.getSession();           // Tạo mới nếu chưa có
HttpSession session = request.getSession(false);      // Trả null nếu chưa có
session.setAttribute("cart", cartObject);
Object cart = session.getAttribute("cart");
session.invalidate(); // Đăng xuất / xóa session

// Làm việc với Cookie
Cookie[] cookies = request.getCookies();
for (Cookie c : cookies) {
    System.out.println(c.getName() + " = " + c.getValue());
}
```

### 2.5 HttpServletResponse — Trả dữ liệu về client

```java
// Thiết lập Content-Type
response.setContentType("text/html;charset=UTF-8");
response.setContentType("application/json;charset=UTF-8");

// Thiết lập HTTP status code
response.setStatus(200); // OK
response.setStatus(404); // Not Found
response.sendError(500, "Internal Server Error");

// Redirect (client tự gửi request mới)
response.sendRedirect("/login");                         // Redirect tuyệt đối
response.sendRedirect(request.getContextPath() + "/home"); // Redirect với context path

// Ghi nội dung
PrintWriter out = response.getWriter();      // Cho text/HTML
OutputStream os = response.getOutputStream(); // Cho binary (ảnh, file)

// Thêm Cookie
Cookie cookie = new Cookie("remember_token", "abc123");
cookie.setMaxAge(7 * 24 * 3600); // Sống 7 ngày (giây)
cookie.setHttpOnly(true);         // Không cho JavaScript đọc
cookie.setPath("/");
response.addCookie(cookie);
```

### 2.6 forward() vs redirect() — Sự khác biệt quan trọng

```
FORWARD (request.getRequestDispatcher().forward())
┌────────────────────────────────────────────────────┐
│  Client ──→ Server: GET /products                  │
│              └── ProductServlet.doGet()            │
│                    └── forward → product.jsp       │
│  Client ←── Server: HTML (URL trên browser: /products) │
│                                                    │
│   Nhanh hơn (không tạo request mới)              │
│   Dữ liệu request.setAttribute() được giữ       │
│   URL trên browser KHÔNG thay đổi               │
└────────────────────────────────────────────────────┘

REDIRECT (response.sendRedirect())
┌────────────────────────────────────────────────────┐
│  Client ──→ Server: POST /login                    │
│              └── LoginServlet.doPost()             │
│  Client ←── Server: 302 Found, Location: /home    │
│  Client ──→ Server: GET /home (request MỚI)       │
│  Client ←── Server: HTML (URL: /home)              │
│                                                    │
│   URL trên browser thay đổi                     │
│   Tránh F5 submit form lại (PRG Pattern)        │
│   Mất dữ liệu request.setAttribute()            │
└────────────────────────────────────────────────────┘
```

> **Quy tắc vàng**: Sau khi xử lý POST (submit form), luôn dùng **redirect** để tránh người dùng F5 gửi form lại (Post/Redirect/Get pattern).

---

## 3. JSP (JavaServer Pages)

### 3.1 JSP là gì và tại sao cần nó?

**Vấn đề của Servlet thuần**: Viết HTML bên trong Java rất cồng kềnh và khó bảo trì:

```java
// Xấu kinh khủng — HTML lẫn lộn trong Java
out.println("<table class='products'>");
out.println("  <tr><th>Tên</th><th>Giá</th></tr>");
for (Product p : products) {
    out.println("  <tr>");
    out.println("    <td>" + p.getName() + "</td>");
    out.println("    <td>" + p.getPrice() + "</td>");
    out.println("  </tr>");
}
out.println("</table>");
```

**Giải pháp — JSP**: Viết HTML bình thường, nhúng Java vào chỗ cần thiết:

```jsp
<%-- Đẹp hơn nhiều! HTML là chính, Java là phụ --%>
<table class="products">
  <tr><th>Tên</th><th>Giá</th></tr>
  <c:forEach var="p" items="${products}">
    <tr>
      <td>${p.name}</td>
      <td>${p.price}</td>
    </tr>
  </c:forEach>
</table>
```

> **Bản chất của JSP**: Khi deploy, Tomcat **biên dịch JSP thành Servlet**. Bạn thấy file `.jsp` nhưng máy chạy file `.java`. JSP chỉ là cú pháp tiện lợi hơn để viết Servlet xuất HTML.

### 3.2 Cú pháp JSP cần biết

```jsp
<%-- 1. SCRIPTLET: Nhúng code Java vào JSP (KHÔNG khuyến khích dùng) --%>
<%
    String name = request.getParameter("name");
    int count = 0;
%>

<%-- 2. EXPRESSION: In giá trị ra HTML --%>
<p>Xin chào, <%= name %>!</p>

<%-- 3. DECLARATION: Khai báo biến/method ở cấp class --%>
<%! 
    private int counter = 0;
    public String greet(String name) { return "Chào " + name; }
%>

<%-- 4. DIRECTIVE: Cấu hình trang JSP --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.example.model.Product" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<%-- 5. EL (Expression Language) — KHUYẾN KHÍCH dùng thay scriptlet --%>
<p>Tên: ${user.name}</p>
<p>Email: ${user.email}</p>
<p>Giỏ hàng: ${sessionScope.cart.totalItems} sản phẩm</p>
<p>Tham số URL: ${param.keyword}</p>
```

### 3.3 Các đối tượng ngầm định (Implicit Objects)

JSP cung cấp sẵn các đối tượng không cần khai báo:

| Đối tượng | Kiểu | Mô tả |
|-----------|------|-------|
| `request` | `HttpServletRequest` | Thông tin HTTP request |
| `response` | `HttpServletResponse` | Thông tin HTTP response |
| `session` | `HttpSession` | Dữ liệu phiên làm việc |
| `application` | `ServletContext` | Dữ liệu toàn ứng dụng |
| `out` | `JspWriter` | Ghi nội dung ra response |
| `pageContext` | `PageContext` | Truy cập tất cả scope |
| `config` | `ServletConfig` | Cấu hình Servlet |

### 3.4 JSTL (JSP Standard Tag Library)

JSTL giúp thay thế scriptlet bằng tag HTML-like, sạch hơn và dễ đọc hơn:

```jsp
<%-- Thêm dependency JSTL vào pom.xml trước --%>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%-- Điều kiện --%>
<c:if test="${user != null}">
    <p>Xin chào, ${user.name}!</p>
</c:if>

<c:choose>
    <c:when test="${product.stock > 10}">
        <span class="badge green">Còn hàng</span>
    </c:when>
    <c:when test="${product.stock > 0}">
        <span class="badge orange">Sắp hết (còn ${product.stock})</span>
    </c:when>
    <c:otherwise>
        <span class="badge red">Hết hàng</span>
    </c:otherwise>
</c:choose>

<%-- Vòng lặp --%>
<c:forEach var="product" items="${products}" varStatus="status">
    <tr class="${status.index % 2 == 0 ? 'even' : 'odd'}">
        <td>${status.count}</td>    <%-- Số thứ tự từ 1 --%>
        <td>${product.name}</td>
        <td>
            <%-- Định dạng số thành tiền tệ --%>
            <fmt:formatNumber value="${product.price}" 
                              type="currency" 
                              currencySymbol="₫"/>
        </td>
    </tr>
</c:forEach>

<%-- URL với context path tự động --%>
<c:url var="homeUrl" value="/home"/>
<a href="${homeUrl}">Trang chủ</a>

<%-- Set / remove attribute --%>
<c:set var="tax" value="0.1" scope="page"/>
<c:remove var="tempData" scope="session"/>

<%-- Định dạng ngày tháng --%>
<fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
```

---

## 4. Mô Hình MVC với Servlet + JSP

### 4.1 MVC là gì?

**MVC (Model - View - Controller)** là mẫu thiết kế tách ứng dụng thành 3 phần:

```
┌─────────────────────────────────────────────────────────┐
│                        MVC Pattern                       │
│                                                         │
│   ┌──────────┐    2. Gọi     ┌──────────┐              │
│   │          │  ──────────→  │          │              │
│   │Controller│               │  Model   │              │
│   │(Servlet) │  ←──────────  │  (POJO / │              │
│   │          │  3. Trả data  │   DAO)   │              │
│   └──────────┘               └──────────┘              │
│        │                                                │
│        │ 4. Đặt data vào request, forward              │
│        ▼                                                │
│   ┌──────────┐                                         │
│   │   View   │  5. Render HTML dùng data từ Model     │
│   │  (JSP)   │                                         │
│   └──────────┘                                         │
│        ↑                                                │
│        │ 1. HTTP Request                               │
│   [Client/Browser]                                      │
└─────────────────────────────────────────────────────────┘
```

| Tầng | Công nghệ | Trách nhiệm |
|------|-----------|-------------|
| **Model** | Java class / DAO | Dữ liệu và nghiệp vụ (kết nối DB, tính toán) |
| **View** | JSP | Hiển thị giao diện HTML |
| **Controller** | Servlet | Điều phối: nhận request, gọi Model, chọn View |

### 4.2 Ví dụ hoàn chỉnh — Danh sách sản phẩm

**Model — Product.java**
```java
package com.example.model;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;

    // Constructor, Getter, Setter
    public Product(int id, String name, double price, int stock) {
        this.id = id; this.name = name;
        this.price = price; this.stock = stock;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
}
```

**DAO — ProductDAO.java**
```java
package com.example.dao;

import com.example.model.Product;
import java.sql.*;
import java.util.*;

public class ProductDAO {

    private static final String URL  = "jdbc:mysql://localhost:3306/furniture_db";
    private static final String USER = "root";
    private static final String PASS = "password";

    public List<Product> getAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE is_active = 1 ORDER BY id DESC";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                ));
            }
        }
        return list;
    }

    public Product findById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getInt("id"), rs.getString("name"),
                        rs.getDouble("price"), rs.getInt("stock")
                    );
                }
            }
        }
        return null;
    }
}
```

**Controller — ProductServlet.java**
```java
package com.example.controller;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("detail".equals(action)) {
                showDetail(request, response);
            } else {
                showList(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Lỗi database", e);
        }
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {
        // 1. Lấy dữ liệu từ Model
        List<Product> products = productDAO.getAll();

        // 2. Đặt dữ liệu vào request scope
        req.setAttribute("products", products);
        req.setAttribute("totalCount", products.size());

        // 3. Forward sang View (JSP)
        req.getRequestDispatcher("/WEB-INF/views/product/list.jsp")
           .forward(req, resp);
    }

    private void showDetail(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productDAO.findById(id);

        if (product == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm");
            return;
        }

        req.setAttribute("product", product);
        req.getRequestDispatcher("/WEB-INF/views/product/detail.jsp")
           .forward(req, resp);
    }
}
```

**View — /WEB-INF/views/product/list.jsp**
```jsp
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Sản Phẩm</title>
</head>
<body>
<h1>Sản Phẩm (${totalCount} sản phẩm)</h1>

<table border="1">
    <thead>
        <tr><th>#</th><th>Tên</th><th>Giá</th><th>Tồn kho</th><th></th></tr>
    </thead>
    <tbody>
        <c:forEach var="p" items="${products}" varStatus="s">
            <tr>
                <td>${s.count}</td>
                <td>${p.name}</td>
                <td><fmt:formatNumber value="${p.price}" pattern="#,###"/> ₫</td>
                <td>
                    <c:choose>
                        <c:when test="${p.stock > 0}">${p.stock}</c:when>
                        <c:otherwise><em>Hết hàng</em></c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a href="products?action=detail&id=${p.id}">Chi tiết</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty products}">
            <tr><td colspan="5">Không có sản phẩm nào.</td></tr>
        </c:if>
    </tbody>
</table>
</body>
</html>
```

> **Tại sao đặt JSP trong `/WEB-INF/`?** Thư mục `WEB-INF` được bảo vệ — người dùng không thể truy cập trực tiếp qua URL. JSP chỉ được truy cập thông qua `RequestDispatcher.forward()` từ Servlet, đảm bảo luôn có Controller xử lý trước.

---

## 5. Spring MVC

### 5.1 Tại sao cần Spring MVC?

Với Servlet + JSP thuần, bạn phải tự làm nhiều thứ lặp đi lặp lại:
- Tự parse tham số từ request, tự convert kiểu dữ liệu
- Tự validate dữ liệu đầu vào
- Tự quản lý dependency giữa các class
- Mỗi URL phải ánh xạ thủ công trong `web.xml` hoặc `@WebServlet`

**Spring MVC giải quyết tất cả những điều đó tự động.**

### 5.2 Kiến trúc Spring MVC

```
Client ──→ DispatcherServlet ──→ HandlerMapping
                │                     │
                │              (Tìm đúng Controller)
                │                     │
                ◀──────────────────────┘
                │
                ▼
          Controller Method
                │
          (Xử lý logic)
                │
                ▼
            ModelAndView
          (data + tên view)
                │
                ▼
          ViewResolver
      (Tìm file JSP/Thymeleaf)
                │
                ▼
        Render HTML response
```

**DispatcherServlet** là "cổng duy nhất" (Front Controller Pattern) — tất cả request đều đi qua đây trước khi được phân phối.

### 5.3 Cấu hình Spring MVC (Java Config)

```java
// WebConfig.java — Thay thế web.xml và Spring XML config
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example")
public class WebConfig implements WebMvcConfigurer {

    // Cấu hình ViewResolver: ánh xạ tên view → đường dẫn JSP
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    // Phục vụ file tĩnh (CSS, JS, ảnh)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/WEB-INF/static/");
    }
}
```

### 5.4 Controller trong Spring MVC

```java
@Controller
@RequestMapping("/products")   // URL gốc cho toàn class
public class ProductController {

    @Autowired
    private ProductService productService; // Spring tự inject

    // GET /products  hoặc  GET /products/
    @GetMapping
    public String list(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/list"; // → /WEB-INF/views/product/list.jsp
    }

    // GET /products/5
    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        if (product == null) return "error/404";
        model.addAttribute("product", product);
        return "product/detail";
    }

    // GET /products/new  (hiển thị form)
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product()); // Object rỗng cho form
        return "product/form";
    }

    // POST /products  (xử lý submit form)
    @PostMapping
    public String create(@Valid @ModelAttribute Product product,
                         BindingResult result,
                         RedirectAttributes redirectAttrs) {

        if (result.hasErrors()) {
            return "product/form"; // Quay lại form nếu có lỗi
        }

        productService.save(product);
        redirectAttrs.addFlashAttribute("successMsg", "Thêm sản phẩm thành công!");
        return "redirect:/products"; // PRG Pattern
    }

    // GET /products/search?keyword=sofa&minPrice=100000
    @GetMapping("/search")
    public String search(@RequestParam String keyword,
                         @RequestParam(required = false, defaultValue = "0") double minPrice,
                         @RequestParam(required = false, defaultValue = "1") int page,
                         Model model) {

        List<Product> results = productService.search(keyword, minPrice, page);
        model.addAttribute("results", results);
        model.addAttribute("keyword", keyword);
        return "product/search-result";
    }
}
```

### 5.5 Các annotation quan trọng

```java
// MAPPING — Ánh xạ URL đến method
@RequestMapping("/path")          // Áp dụng mọi HTTP method
@GetMapping("/path")              // Chỉ GET
@PostMapping("/path")             // Chỉ POST
@PutMapping("/path")              // Chỉ PUT
@DeleteMapping("/path")           // Chỉ DELETE

// THAM SỐ — Lấy dữ liệu từ request
@PathVariable int id              // Từ URL: /products/{id}
@RequestParam String keyword      // Từ URL: /search?keyword=sofa
@RequestParam(defaultValue="1") int page  // Giá trị mặc định
@ModelAttribute Product product   // Bind form data vào object
@RequestBody ProductDTO dto       // Đọc JSON body (thường dùng cho REST)
@RequestHeader String token       // Đọc HTTP header

// VALIDATION
@Valid                            // Kích hoạt Bean Validation
BindingResult result              // Chứa kết quả validation

// RESPONSE
@ResponseBody                     // Trả về data trực tiếp (không qua ViewResolver)
@ResponseStatus(HttpStatus.CREATED) // Đặt HTTP status code

// DEPENDENCY INJECTION
@Controller                       // Class là Spring MVC Controller
@Service                          // Class là Service layer
@Repository                       // Class là DAO/Repository
@Component                        // Class là Spring Bean generic
@Autowired                        // Spring tự inject dependency
```

### 5.6 Bean Validation

```java
// Model với validation annotation
public class Product {
    @NotBlank(message = "Tên sản phẩm không được trống")
    @Size(min = 3, max = 100, message = "Tên phải từ 3 đến 100 ký tự")
    private String name;

    @NotNull(message = "Giá không được bỏ trống")
    @Min(value = 1000, message = "Giá tối thiểu 1,000₫")
    @Max(value = 100_000_000, message = "Giá tối đa 100 triệu")
    private Double price;

    @Min(value = 0, message = "Tồn kho không được âm")
    private int stock;

    @Email(message = "Email không hợp lệ")
    private String supplierEmail;
}

// JSP hiển thị lỗi validation
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form modelAttribute="product" action="/products" method="post">
    <div>
        <label>Tên sản phẩm:</label>
        <form:input path="name"/>
        <form:errors path="name" cssClass="error-text"/>
    </div>
    <div>
        <label>Giá:</label>
        <form:input path="price"/>
        <form:errors path="price" cssClass="error-text"/>
    </div>
    <button type="submit">Lưu</button>
</form:form>
```

---

## 6. Spring Boot REST API

### 6.1 Spring Boot là gì?

**Spring Boot = Spring Framework + Auto-configuration + Embedded Server**

| | Spring MVC truyền thống | Spring Boot |
|---|---|---|
| Cấu hình | Nhiều file XML / Java Config | Tự động (Convention over Configuration) |
| Server | Deploy WAR lên Tomcat ngoài | Tomcat **nhúng sẵn** trong ứng dụng |
| Chạy | Deploy lên server | `java -jar myapp.jar` là xong |
| Build | WAR | JAR (thường) |
| Bắt đầu | Cấu hình nhiều | Spring Initializr → Code ngay |

### 6.2 REST API là gì?

**REST (Representational State Transfer)** là kiến trúc thiết kế API sử dụng HTTP. Thay vì trả về HTML, API trả về **JSON** để frontend (React, Vue, mobile app) tự render.

```
Truyền thống (Spring MVC):
Client ──→ Controller ──→ View (JSP) ──→ HTML ──→ Client
                                         (Server render)

REST API (Spring Boot):
Client ──→ @RestController ──→ JSON ──→ Client
                                    (Client render với React/Vue/Flutter)
```

**Các nguyên tắc REST:**

```
Resource (Tài nguyên) — Mọi thứ là "danh từ" (products, users, orders)

GET    /api/products          → Lấy danh sách tất cả sản phẩm
GET    /api/products/{id}     → Lấy một sản phẩm
POST   /api/products          → Tạo mới sản phẩm
PUT    /api/products/{id}     → Cập nhật toàn bộ sản phẩm
PATCH  /api/products/{id}     → Cập nhật một phần sản phẩm
DELETE /api/products/{id}     → Xóa sản phẩm

HTTP Status Code nói lên kết quả:
200 OK          — Thành công (GET, PUT, PATCH)
201 Created     — Tạo mới thành công (POST)
204 No Content  — Xóa thành công (DELETE)
400 Bad Request — Dữ liệu gửi lên sai
401 Unauthorized — Chưa đăng nhập
403 Forbidden   — Không có quyền
404 Not Found   — Không tìm thấy
500 Internal Server Error — Lỗi server
```

### 6.3 Khởi tạo dự án Spring Boot

**spring-initializr** (https://start.spring.io):
- Project: **Maven**
- Language: **Java**
- Spring Boot: **3.x.x**
- Dependencies: **Spring Web**, **Spring Data JPA**, **MySQL Driver**, **Lombok**, **Validation**

**Cấu trúc `pom.xml` cơ bản:**
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>

<dependencies>
    <!-- Web + REST -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- JPA + Hibernate -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- MySQL -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <!-- Lombok (giảm boilerplate code) -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

### 6.4 Cấu hình application.properties

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/furniture_db?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
# create       — Xóa và tạo lại table mỗi khi chạy (chỉ dùng khi dev)
# update       — Chỉ cập nhật thêm column/table mới (an toàn hơn)
# validate     — Chỉ kiểm tra schema, không thay đổi gì
# none         — Không làm gì (production)

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Server
server.port=8080
server.servlet.context-path=/api

# Logging
logging.level.org.springframework.web=DEBUG
logging.level.com.example=DEBUG
```

### 6.5 Entity

```java
@Entity
@Table(name = "products")
@Data                  // Lombok: tự generate getter, setter, toString, equals
@NoArgsConstructor     // Lombok: constructor không tham số
@AllArgsConstructor    // Lombok: constructor đầy đủ tham số
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Tên sản phẩm không được trống")
    private String name;

    @Column(nullable = false)
    @Min(value = 0, message = "Giá phải >= 0")
    private Double price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Min(value = 0)
    private Integer stock = 0;

    @Column(name = "is_active")
    private Boolean active = true;

    @CreationTimestamp   // Tự động điền khi INSERT
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp     // Tự động cập nhật khi UPDATE
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
```

### 6.6 Repository

```java
// Spring Data JPA: tự generate SQL, không cần viết DAO thủ công
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Spring tự tạo SQL từ tên method
    List<Product> findByActiveTrue();
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByPriceBetween(double min, double max);

    // Dùng JPQL (Java Persistence Query Language) cho query phức tạp
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.stock > 0 ORDER BY p.createdAt DESC")
    List<Product> findAvailableProducts();

    // Query với tham số
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% AND p.price >= :minPrice")
    List<Product> searchProducts(@Param("keyword") String keyword,
                                  @Param("minPrice") double minPrice);

    // Native SQL (dùng khi JPQL không đủ)
    @Query(value = "SELECT * FROM products WHERE stock < :threshold LIMIT 10",
           nativeQuery = true)
    List<Product> findLowStockProducts(@Param("threshold") int threshold);
}
```

### 6.7 DTO (Data Transfer Object)

**DTO** là object dùng để transfer dữ liệu giữa client và server, tách biệt với Entity:

```java
// Nhận dữ liệu từ client (Request)
@Data
public class ProductRequestDTO {
    @NotBlank(message = "Tên không được trống")
    private String name;

    @NotNull
    @Min(0)
    private Double price;

    private String description;
    private Integer stock;
    private Long categoryId;
}

// Trả dữ liệu về client (Response) — chỉ expose field cần thiết
@Data
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private Integer stock;
    private String status;    // "Còn hàng" / "Hết hàng"
    private String createdAt; // String thay vì LocalDateTime (dễ đọc hơn)
}

// Chuẩn hóa format response cho mọi API
@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "Thành công", data);
    }
    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
```

### 6.8 Service Layer

```java
@Service
@Transactional  // Mọi method đều chạy trong transaction
public class ProductService {

    private final ProductRepository productRepository;

    // Constructor Injection (khuyến khích hơn @Autowired trên field)
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDTO> findAll() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm id=" + id));
        return toResponseDTO(product);
    }

    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStock(dto.getStock() != null ? dto.getStock() : 0);
        product.setActive(true);

        return toResponseDTO(productRepository.save(product));
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm id=" + id));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        if (dto.getStock() != null) product.setStock(dto.getStock());

        return toResponseDTO(productRepository.save(product));
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm id=" + id));
        product.setActive(false); // Soft delete: ẩn thay vì xóa thật
        productRepository.save(product);
    }

    // Chuyển Entity → DTO
    private ProductResponseDTO toResponseDTO(Product p) {
        String status = p.getStock() > 0 ? "Còn hàng" : "Hết hàng";
        String createdAt = p.getCreatedAt() != null
                ? p.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : "";
        return new ProductResponseDTO(
                p.getId(), p.getName(), p.getPrice(),
                p.getDescription(), p.getStock(), status, createdAt
        );
    }
}
```

### 6.9 REST Controller

```java
@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")  // Cho phép frontend domain khác gọi API
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(productService.findAll()));
    }

    // GET /api/products/5
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(productService.findById(id)));
    }

    // POST /api/products  — Body: {"name":"Sofa","price":5000000}
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> create(
            @Valid @RequestBody ProductRequestDTO dto) {

        ProductResponseDTO created = productService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Tạo sản phẩm thành công", created));
    }

    // PUT /api/products/5
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO dto) {

        return ResponseEntity.ok(ApiResponse.ok("Cập nhật thành công", productService.update(id, dto)));
    }

    // DELETE /api/products/5
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.ok("Xóa thành công", null));
    }

    // GET /api/products/search?keyword=sofa&minPrice=1000000
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") double minPrice) {

        return ResponseEntity.ok(ApiResponse.ok(productService.search(keyword, minPrice)));
    }
}
```

**Ví dụ JSON response:**
```json
{
  "success": true,
  "message": "Thành công",
  "data": [
    {
      "id": 1,
      "name": "Sofa góc L phòng khách",
      "price": 8500000,
      "description": "Sofa cao cấp, chất liệu da thật",
      "stock": 5,
      "status": "Còn hàng",
      "createdAt": "15/03/2026 10:30"
    }
  ]
}
```

### 6.10 Global Exception Handler

```java
@RestControllerAdvice  // Xử lý exception toàn ứng dụng
public class GlobalExceptionHandler {

    // Xử lý resource không tìm thấy
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage()));
    }

    // Xử lý lỗi validation (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, "Dữ liệu không hợp lệ", errors));
    }

    // Xử lý mọi exception còn lại
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Lỗi server: " + ex.getMessage()));
    }
}

// Custom exception
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

---

## 7. So Sánh Tổng Quan

| Tiêu chí | Servlet + JSP | Spring MVC | Spring Boot REST |
|---|---|---|---|
| **Cấu hình** | `web.xml` + `@WebServlet` | Java Config + XML | `application.properties` |
| **Map URL** | `@WebServlet("/path")` | `@GetMapping("/path")` | `@GetMapping("/path")` |
| **Lấy param** | `request.getParameter()` | `@RequestParam`, `@PathVariable` | `@RequestParam`, `@PathVariable` |
| **Trả dữ liệu** | `out.println(html)` + forward | `Model.addAttribute()` + View | `@ResponseBody` JSON |
| **Session** | `request.getSession()` | `HttpSession` trong method param | JWT Token (stateless) |
| **Database** | JDBC thuần | JDBC / JPA | Spring Data JPA |
| **Validation** | Tự viết if-else | Bean Validation (`@Valid`) | Bean Validation (`@Valid`) |
| **Server** | Deploy WAR lên Tomcat | Deploy WAR lên Tomcat | `java -jar` (Tomcat nhúng) |
| **Phù hợp** | Học nền tảng | Web app truyền thống | API cho SPA / Mobile |

---

## 8. Cấu Trúc Thư Mục Chuẩn

### Servlet + JSP (Maven WAR)

```
my-servlet-app/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/
│       │       ├── controller/       ← Servlet classes
│       │       │   ├── ProductServlet.java
│       │       │   └── LoginServlet.java
│       │       ├── model/            ← POJO / Entity
│       │       │   └── Product.java
│       │       ├── dao/              ← Database access
│       │       │   └── ProductDAO.java
│       │       └── util/             ← Tiện ích
│       │           └── DBConnection.java
│       └── webapp/
│           ├── WEB-INF/
│           │   ├── web.xml           ← Deployment descriptor
│           │   └── views/            ← JSP files (không accessible trực tiếp)
│           │       ├── product/
│           │       │   ├── list.jsp
│           │       │   └── detail.jsp
│           │       └── includes/
│           │           ├── header.jsp
│           │           └── footer.jsp
│           └── static/               ← CSS, JS, Images (accessible)
│               ├── css/
│               └── js/
└── pom.xml
```

### Spring Boot REST (Maven JAR)

```
furniture-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/furniture/
│   │   │       ├── FurnitureApplication.java   ← Main class (@SpringBootApplication)
│   │   │       ├── controller/                 ← REST Controllers
│   │   │       │   ├── ProductController.java
│   │   │       │   └── OrderController.java
│   │   │       ├── service/                    ← Business logic
│   │   │       │   ├── ProductService.java
│   │   │       │   └── OrderService.java
│   │   │       ├── repository/                 ← JPA Repositories
│   │   │       │   └── ProductRepository.java
│   │   │       ├── entity/                     ← JPA Entities
│   │   │       │   └── Product.java
│   │   │       ├── dto/                        ← Data Transfer Objects
│   │   │       │   ├── request/
│   │   │       │   │   └── ProductRequestDTO.java
│   │   │       │   └── response/
│   │   │       │       └── ProductResponseDTO.java
│   │   │       ├── exception/                  ← Custom exceptions + handler
│   │   │       │   ├── ResourceNotFoundException.java
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       └── config/                     ← Cấu hình (Security, CORS...)
│   │   │           └── WebConfig.java
│   │   └── resources/
│   │       ├── application.properties          ← Cấu hình môi trường
│   │       ├── application-dev.properties      ← Cấu hình riêng cho dev
│   │       └── application-prod.properties     ← Cấu hình riêng cho production
│   └── test/
│       └── java/
│           └── com/example/furniture/
│               └── controller/
│                   └── ProductControllerTest.java
└── pom.xml
```

---

## 9. Lộ Trình Học Khuyến Nghị

```
TUẦN 1-2: NỀN TẢNG
├── HTTP là gì? GET vs POST, Status Code, Header, Cookie, Session
├── HTML Form, cách gửi dữ liệu lên server
└── Cài đặt: JDK 17+, IntelliJ IDEA / Eclipse, Apache Tomcat 10+

TUẦN 3-4: SERVLET
├── Tạo project Maven WAR, cấu hình Tomcat
├── Viết HelloServlet đầu tiên
├── Xử lý form đăng nhập/đăng ký (POST)
├── Quản lý Session và Cookie
└── Bài tập: Ứng dụng To-Do List CRUD với Servlet + ArrayList (chưa cần DB)

TUẦN 5-6: JSP + JSTL
├── Cú pháp JSP, Expression Language (EL)
├── JSTL: c:forEach, c:if, c:choose, fmt:formatNumber
├── Mô hình MVC với Servlet + JSP
├── Kết nối MySQL với JDBC, viết DAO
└── Bài tập: Ứng dụng quản lý sản phẩm (CRUD) với Servlet + JSP + MySQL

TUẦN 7-8: SPRING MVC
├── Dependency Injection, IoC Container
├── Cấu hình Spring MVC (Java Config)
├── Controller, @RequestMapping, @GetMapping, @PostMapping
├── Bean Validation với @Valid
└── Bài tập: Refactor ứng dụng sản phẩm từ Servlet thuần sang Spring MVC

TUẦN 9-10: SPRING DATA JPA
├── ORM là gì, Hibernate
├── Entity, @Id, @Column, @ManyToOne, @OneToMany
├── JpaRepository, derived query method
└── Bài tập: Thay JDBC thuần bằng Spring Data JPA

TUẦN 11-12: SPRING BOOT REST API
├── Spring Boot Initializr, cấu trúc project
├── @RestController, @ResponseBody
├── DTO pattern, ResponseEntity
├── Global Exception Handler
└── Bài tập: Xây dựng REST API quản lý sản phẩm, test bằng Postman

TUẦN 13+: NÂNG CAO
├── Spring Security (JWT Authentication)
├── Pagination & Sorting
├── File Upload
├── API Documentation với Swagger / OpenAPI
└── Deploy lên cloud (Railway, Render, AWS EC2)
```

---

## 10. Các Lỗi Thường Gặp

### Lỗi Servlet & JSP

```
 HTTP 404 — Servlet không tìm thấy
    Kiểm tra @WebServlet("/path") — path phải bắt đầu bằng /
    Kiểm tra project đã được deploy lên Tomcat chưa

 HTTP 405 — Method Not Allowed
    Form POST nhưng Servlet chỉ có doGet() — thêm doPost()

 Tiếng Việt bị lỗi/???
    Thêm request.setCharacterEncoding("UTF-8") đầu doPost()
    Thêm response.setContentType("text/html;charset=UTF-8")
    Thêm <%@ page contentType="text/html;charset=UTF-8" %> đầu JSP
    Thêm URIEncoding="UTF-8" vào Connector trong server.xml Tomcat

 NullPointerException khi dùng EL trong JSP
    Kiểm tra Servlet đã setAttribute() đúng tên chưa
    EL dùng ${products} nhưng setAttribute("productList",...) — tên khác nhau

 JSP truy cập trực tiếp được qua URL
    Di chuyển JSP vào trong /WEB-INF/views/
```

### Lỗi Spring Boot

```
 Port 8080 already in use
    Tắt process đang dùng port hoặc đổi server.port=8081

 No qualifying bean of type 'XxxService'
    Thiếu @Service / @Component annotation trên class đó
    Class không nằm trong package được @ComponentScan quét

 Could not autowire. No beans of type 'XxxRepository'
    Interface chưa extend JpaRepository<Entity, IdType>
    @EnableJpaRepositories chưa được cấu hình

 JSON parse error / HttpMessageNotReadableException
    Request body JSON sai format hoặc thiếu Content-Type: application/json header

 LazyInitializationException: could not initialize proxy
    Truy cập @ManyToOne/@OneToMany ngoài transaction
    Thêm @Transactional vào Service method hoặc dùng EAGER fetch

 Circular dependency
    Service A inject Service B, Service B inject Service A
    Tách logic sang Service C hoặc dùng @Lazy trên một trong hai
```

---

##  Tài Liệu Tham Khảo

| Tài liệu | Link |
|---|---|
| Jakarta EE Javadoc | https://jakarta.ee/specifications/servlet/ |
| Spring Framework Docs | https://docs.spring.io/spring-framework/docs/current/reference/html/ |
| Spring Boot Docs | https://docs.spring.io/spring-boot/docs/current/reference/html/ |
| Spring Data JPA | https://docs.spring.io/spring-data/jpa/docs/current/reference/html/ |
| Baeldung (Tutorials) | https://www.baeldung.com |
| Spring Initializr | https://start.spring.io |

---

>  **Lời khuyên cuối**: Đừng cố học thuộc cú pháp. Hãy **làm một dự án thật** (dù nhỏ) xuyên suốt cả lộ trình — ví dụ như ứng dụng quản lý cửa hàng nội thất. Bắt đầu bằng Servlet thuần, sau đó refactor dần lên Spring Boot. Khi bạn tự tay refactor, bạn sẽ thấy rõ Spring giải quyết vấn đề gì, và kiến thức sẽ khắc sâu hơn rất nhiều.