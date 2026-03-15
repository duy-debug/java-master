# Spring Boot REST API Project

Dự án này là một ứng dụng Spring Boot cung cấp một REST API chuẩn, được thiết lập để làm việc với cơ sở dữ liệu MySQL thông qua JPA (Java Persistence API) và có tích hợp giao diện với Thymeleaf.

## 1. Cấu trúc thư mục của dự án

Cấu trúc thư mục của dự án tuân theo mô hình **MVC (Model - View - Controller)** kết hợp với kiến trúc phân tầng chuẩn của một ứng dụng Spring Boot.

```text
springbootdb_restapi/
├── pom.xml                   # File quản lý cấu hình và thư viện (dependencies) của Maven
├── src/
│   ├── main/
│   │   ├── java/org/example/springbootdb_restapi/
│   │   │   ├── SpringbootdbRestapiApplication.java # Class chính để chạy ứng dụng Spring Boot
│   │   │   ├── controller/   # Lớp Controller: Quy định các API endpoints (URL), tiếp nhận HTTP requests từ client
│   │   │   │   └── StudentController.java         
│   │   │   ├── service/      # Lớp Service: Chứa các logic nghiệp vụ (business logic)
│   │   │   │   └── StudentService.java
│   │   │   ├── repository/   # Lớp Repository: Chịu trách nhiệm tương tác trực tiếp với cơ sở dữ liệu qua JPA
│   │   │   │   └── StudentRepository.java
│   │   │   ├── entity/       # Lớp Entity (Model): Các class ánh xạ trực tiếp với bảng trong cơ sở dữ liệu
│   │   │   │   └── Student.java
│   │   │   └── dto/          # Lớp DTO (Data Transfer Object): Định dạng dữ liệu trao đổi giữa Client và Server nhằm che giấu Entity 
│   │   │       └── StudentDTO.java
│   │   └── resources/
│   │       ├── application.properties # File cấu hình của dự án (kết nối DB, cổng chạy server...)
│   │       ├── static/       # Chứa các file tĩnh như CSS, JS, hình ảnh
│   │       └── templates/    # Chứa các file giao diện Thymeleaf (HTML)
```

### Ý nghĩa của từng Package logic:
- **`Entity`**: Đại diện cho một bảng trong CSDL. Ví dụ `Student.java` tương ứng với bảng `student` trong DB. Mỗi thuộc tính trong class tương ứng với một cột của bảng.
- **`Repository`**: Dùng Spring Data JPA để kế thừa `JpaRepository`, giúp tự động hóa các thao tác CRUD (Thêm, Sửa, Xóa, Lấy dữ liệu) mà không cần viết câu lệnh SQL thủ công.
- **`DTO (Data Transfer Object)`**: Giúp đóng gói dữ liệu để gửi giữ client và server. Giúp bảo mật thông tin (không gửi những dữ liệu dư thừa từ Entity) và định hình đúng format JSON trả về.
- **`Service`**: Chứa thuật toán và nghiệp vụ xử lý phức tạp. Tầng này sẽ gọi đến các phương thức của tần `Repository`. Nó đóng vai trò là cầu nối giữa `Controller` và `Repository`.
- **`Controller`**: Dùng `@RestController` để định nghĩa ra các API (`GET`, `POST`, `PUT`, `DELETE`). Nó nhận các request từ phía người dùng (Client), gọi xuống `Service` xử lý và trả về kết quả (dưới dạng JSON hoặc HTML nếu dùng Thymeleaf).

---

## 2. Luồng hoạt động của Spring Boot REST API

Khi một yêu cầu (ví dụ: lấy danh sách học sinh) được gửi từ client (như Trình duyệt, Postman, hoặc Frontend app), luồng xử lý dữ liệu sẽ đi qua các bước sau một cách tuần tự:

```text
Client (Postman/Browser) 
  ────────► (1) HTTP Request ────────► Controller
                                          │
    ◄────── (6) HTTP Response ◄──────     │ (2) Gọi phương thức
              (JSON/HTML)                 ▼
                                       Service
                                          │
                                          │ (3) Xử lý logic nghiệp vụ và gọi
                                          ▼
                                       Repository
                                          │
                                          │ (4) JPA Query (SQL)
                                          ▼
                                       Database (MySQL)
                                          │
                                          │ (5) Trả về Entities
                                          ▼
                                       Repository ────────► Service (Chuyển Entity thành DTO) ────────► Controller
```

### Diễn giải các bước cụ thể:

1. **Client Gửi Yêu Cầu (Client Request)**: 
   - Client tạo 1 request, ví dụ `GET /api/students` để lấy danh sách sinh viên.
   
2. **Controller Tiếp Nhận (Receive Request)**:
   - Request đi qua bộ điều hướng của Spring (DispatcherServlet) và được chuyển tới `StudentController` (nhờ các Annotation ánh xạ như `@GetMapping("/api/students")`).
   - `StudentController` nhận dữ liệu đầu vào (nếu có, qua URL, Query Params, Body JSON/DTO) và ra lệnh cho `StudentService` thực thi logic.

3. **Service Xử Lý Nghiệp Vụ (Business Logic)**:
   - `StudentService` nhận thông tin từ Controller. Lớp này sẽ kiểm tra tính hợp lệ của dữ liệu, thực hiện các tính toán liên quan nếu có.
   - Khi cần làm việc với DB (ví dụ lấy hoặc lưu dữ liệu), Service sẽ gọi đến `StudentRepository`.

4. **Repository Thao Tác CSDL (Database Interaction)**:
   - `StudentRepository` sẽ thực thi các lệnh SQL (thực chất đã được Spring Data JPA tự động dịch và thực thi).
   - Nó truy vấn xuống cơ sở dữ liệu MySQL tương ứng (Thêm, Tìm, Xóa...). 

5. **Entity, Database & DTO trả về**:
   - CSDL trả về kết quả cho `StudentRepository` dưới dạng dối tượng `Student` (Entity).
   - `Repository` đưa List Entity `Student` về lại cho `Service`.
   - Tại `Service`, các dữ liệu thô này có thể được ánh xạ (mapping) sang đối tượng an toàn/gọn gàng hơn mang tên `StudentDTO`.
   - Service sẽ trả tiếp `StudentDTO` lên cho `Controller`.

6. **Controller Trả Về Kết Quả (Response)**:
   - `Controller` nhận `StudentDTO` hoặc danh sách DTO vừa nhận.
   - Spring Boot sẽ tự động chuyển đổi đối tượng Java này thành định dạng JSON bằng thư viện Jackson.
   - `Controller` đóng gói chuỗi JSON kèm theo HTTP Status Code (ví dụ `200 OK` cho hành động lấy thành công) và gửi về lại cho Client. Nếu Controller dùng hiển thị giao diện qua Thymeleaf, nó sẽ render màn hình tương ứng.
