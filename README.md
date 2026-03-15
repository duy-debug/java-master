# Lập trình Java

Java là một trong những ngôn ngữ lập trình phổ biến nhất thế giới, được phát triển bởi Sun Microsystems (hiện thuộc Oracle) vào năm 1995. Với khẩu hiệu nổi tiếng "Write Once, Run Anywhere" (Viết một lần, chạy mọi nơi), Java đã chứng minh được sức mạnh và tính linh hoạt vượt trội trong nhiều lĩnh vực lập trình.

Các đặc điểm nổi bật của Java:
- Hướng đối tượng (Object-Oriented): Mọi thành phần trong Java đều được coi là đối tượng, giúp mã nguồn dễ quản lý, bảo trì và tái sử dụng.
- Độc lập nền tảng: Mã nguồn Java sau khi biên dịch sẽ thành Bytecode, có thể chạy trên bất kỳ hệ điều hành nào có cài đặt JVM (Java Virtual Machine).
- Bảo mật và Mạnh mẽ: Java loại bỏ việc thao tác trực tiếp với bộ nhớ (con trỏ), thay vào đó là cơ chế quản lý bộ nhớ tự động (Garbage Collection) và kiểm soát lỗi chặt chẽ.
- Hiệu suất cao: Với trình biên dịch JIT (Just-In-Time), Java đạt được tốc độ thực thi tiệm cận với các ngôn ngữ lập trình hệ thống.

Dự án này được xây dựng như một lộ trình học tập toàn diện, dẫn dắt bạn từ những khái niệm cơ bản nhất đến các kỹ thuật lập trình hướng đối tượng chuyên sâu và cấu trúc dữ liệu phức tạp.


## Các chủ đề chính

- [Step 01: Nền tảng Java](#step-01-nền-tảng-java)
- [Step 02: Mảng và Tiện ích](#step-02-mảng-và-tiện-ích)
- [Step 03: Hướng đối tượng cơ bản](#step-03-hướng-đối-tượng-cơ-bản)
- [Step 04: Hướng đối tượng nâng cao](#step-04-hướng-đối-tượng-nâng-cao)
- [Step 05: Collection và Generic](#step-05-collection-và-generic)
- [Step 06: Ngoại lệ và Tương tác File](#step-06-ngoại-lệ-và-tương-tác-file)
- [Step 07: Spring Boot & Java Web](#step-07-spring-boot--java-web)
- [Hướng dẫn cài đặt IntelliJ IDEA và Chạy Project](#hướng-dẫn-cài-đặt-intellij-idea-và-chạy-project)
- [Tài liệu tham khảo học tập](#tài-liệu-tham-khảo-học-tập)

---

### [Step 01: Nền tảng Java](./step_01_basic/README.md)
Tập trung vào cú pháp, biến, kiểu dữ liệu và các cấu trúc điều khiển (vòng lặp, câu lệnh điều kiện).

| STT | Tên bài | Mô tả |
|:---:|:---|:---|
| 01 | [Hello](./step_01_basic/Hello) | Chương trình đầu tiên Hello World. |
| 02 | [variable](./step_01_basic/variable) | Cách khai báo và sử dụng biến. |
| 03 | [DataType](./step_01_basic/DataType) | Các kiểu dữ liệu cơ bản trong Java. |
| 04 | [Const](./step_01_basic/Const) | Cách sử dụng hằng số với từ khóa final. |
| 05 | [Operator](./step_01_basic/Operator) | Các phép toán số học, so sánh và logic. |
| 06 | [If-else](./step_01_basic/If-else) | Câu lệnh điều kiện rẽ nhánh. |
| 07 | [Switch-Case](./step_01_basic/Switch-Case) | Câu lệnh điều kiện Switch-Case. |
| 08 | [for](./step_01_basic/for) | Vòng lặp for cơ bản. |
| 09 | [While](./step_01_basic/While) | Vòng lặp while và do-while. |

### [Step 02: Mảng và Tiện ích](./step_02_array/README.md)
Cách quản lý tập hợp dữ liệu đơn giản, xử lý toán học và thời gian.

| STT | Tên bài | Mô tả |
|:---:|:---|:---|
| 01 | [Array](./step_02_array/Array) | Khai báo và sử dụng mảng cơ bản. |
| 02 | [Array-Coppy](./step_02_array/Array-Coppy) | Các phương thức sao chép mảng. |
| 03 | [TimKiem-SapXep-CoppyArray](./step_02_array/TimKiem-SapXep-CoppyArray) | Kỹ thuật tìm kiếm và sắp xếp trên mảng. |
| 04 | [ClassMath](./step_02_array/ClassMath) | Sử dụng các hàm toán học Math class. |
| 05 | [Time-Date-Dateformat-Calendar](./step_02_array/Time-Date-Dateformat-Calendar) | Quản lý và định dạng thời gian. |
| 06 | [MiniGame-Random-Numberformat](./step_02_array/MiniGame-Random-Numberformat) | Bài tập thực hành tổng hợp. |

### [Step 03: Hướng đối tượng cơ bản](./step_03_oop_basic/README.md)
Làm quen với các khái niệm Class, Object, Encapsulation và các dự án thực tế.

| STT | Tên bài | Mô tả |
|:---:|:---|:---|
| 01 | [OOP-1-Class](./step_03_oop_basic/OOP-1-Class) | Khởi tạo lớp và đối tượng. |
| 02 | [OOP-2-HoaDonCafe](./step_03_oop_basic/OOP-2-HoaDonCafe) | Bài tập thực hành OOP đầu tiên. |
| 03 | [OOP-3-GET-SET](./step_03_oop_basic/OOP-3-GET-SET) | Tính đóng gói với Getter và Setter. |
| 04 | [OOP-4-ToString](./step_03_oop_basic/OOP-4-ToString) | Phương thức toString trong Object. |
| 05 | [OOP-5-equals-hascode](./step_03_oop_basic/OOP-5-equals-hascode) | So sánh đối tượng với equals và hashCode. |
| 06 | [OOP-QuanLySach](./step_03_oop_basic/OOP-QuanLySach) | Dự án quản lý sách. |
| 07 | [OOP-QuanLyMayTinh](./step_03_oop_basic/OOP-QuanLyMayTinh) | Dự án quản lý máy tính. |
| 08 | [OOP-QuanLyBoPhim](./step_03_oop_basic/OOP-QuanLyBoPhim) | Dự án quản lý bộ phim. |
| 09 | [OOP-QuanLySinhVien](./step_03_oop_basic/OOP-QuanLySinhVien) | Dự án quản lý sinh viên. |

### [Step 04: Hướng đối tượng nâng cao](./step_04_oop_advanced/README.md)
Đi sâu vào các tính chất của OOP: Kế thừa, Đa hình, Trừu tượng và xử lý Chuỗi (String).

| STT | Tên bài | Mô tả |
|:---:|:---|:---|
| 01 | [OOP-Package](./step_04_oop_advanced/OOP-Package) | Quản lý mã nguồn với Package. |
| 02 | [OOP-Public-Protected-Private](./step_04_oop_advanced/OOP-Public-Protected-Private) | Phạm vi truy cập Access Modifiers. |
| 03 | [OOP-Overloading](./step_04_oop_advanced/OOP-Overloading) | Nạp chồng phương thức. |
| 04 | [OOP-Inheritance](./step_04_oop_advanced/OOP-Inheritance) | Tính kế thừa trong Java. |
| 05 | [OOP-Interihance-TT](./step_04_oop_advanced/OOP-Interihance-TT) | Thực hành kế thừa. |
| 06 | [OOP-Overriding](./step_04_oop_advanced/OOP-Overriding) | Ghi đè phương thức. |
| 07 | [OOP-Abstract](./step_04_oop_advanced/OOP-Abstract) | Lớp trừu tượng Abstract class. |
| 08 | [OOP-Interface](./step_04_oop_advanced/OOP-Interface) | Giao tiếp Interface. |
| 09 | [OOP-Abstract-Interihance](./step_04_oop_advanced/OOP-Abstract-Interihance) | Kết hợp Abstract và Inheritance. |
| 10 | [OOP-String](./step_04_oop_advanced/OOP-String) | Xử lý chuỗi cơ bản. |
| 11 | [OOP-String1](./step_04_oop_advanced/OOP-String1) | Các phương thức so sánh chuỗi. |
| 12 | [OOP-String2](./step_04_oop_advanced/OOP-String2) | Các phương thức tìm kiếm chuỗi. |
| 13 | [OOP-String3](./step_04_oop_advanced/OOP-String3) | Cắt và noi chuỗi. |
| 14 | [Phan-Tach-Chuoi-Thanh-Mang](./step_04_oop_advanced/Phan-Tach-Chuoi-Thanh-Mang) | Sử dụng split để tách chuỗi. |
| 15 | [Enum](./step_04_oop_advanced/Enum) | Kiểu dữ liệu liệt kê Enum. |

### [Step 05: Collection và Generic](./step_05_collection/README.md)
Cấu trúc dữ liệu nâng cao như ArrayList, Stack, Queue, Set và kỹ thuật Generic.

| STT | Tên bài | Mô tả |
|:---:|:---|:---|
| 01 | [Generic](./step_05_collection/Generic) | Lập trình tổng quát với Generic. |
| 02 | [QuanLyDanhSachSV-ArrayList](./step_05_collection/QuanLyDanhSachSV-ArrayList) | Quản lý danh sách với ArrayList. |
| 03 | [Stack-Java](./step_05_collection/Stack-Java) | Cấu trúc dữ liệu Ngăn xếp (LIFO). |
| 04 | [Queue-Deque](./step_05_collection/Queue-Deque) | Cấu trúc dữ liệu Hàng đợi (FIFO). |
| 05 | [SET](./step_05_collection/SET) | Cấu trúc dữ liệu Tập hợp (Set). |
| 06 | [compareTo-Comparable](./step_05_collection/compareTo-Comparable) | So sánh và sắp xếp đối tượng. |

### [Step 06: Ngoại lệ và Tương tác File](./step_06_io/README.md)
Cách xử lý lỗi (Exception Handling) và tương tác với hệ thống tập tin.

| STT | Tên bài | Mô tả |
|:---:|:---|:---|
| 01 | [XuLyNgoaiLe](./step_06_io/XuLyNgoaiLe) | Bắt lỗi với Try-Catch-Finally. |
| 02 | [Tao-Tap-Tin-Thu-Muc](./step_06_io/Tao-Tap-Tin-Thu-Muc) | Thao tác với File và Directory. |

### [Step 07: Spring Boot & Java Web](./spring_boot)
Giới thiệu về lập trình Web với Java, bao gồm nền tảng Servlet/JSP cơ bản và Framework Spring Boot hiện đại (RESTful API, tương tác cơ sở dữ liệu...).

| STT | Tên Project | Mô tả |
|:---:|:---|:---|
| 01 | [demo](./spring_boot/demo) | Project Java Web cơ bản (Servlet/JSP). |
| 02 | [demo-spring](./spring_boot/demo-spring) | Ứng dụng Spring Boot đơn giản. |
| 03 | [demo2](./spring_boot/demo2) | Thực hành Java Web cơ bản (Servlet/JSP). |
| 04 | [BMI_Calculator](./spring_boot/BMI_Calculator) | Ứng dụng tính chỉ số BMI với Spring Boot. |
| 05 | [SinhVienApp](./spring_boot/SinhVienApp) | Ứng dụng quản lý sinh viên. |
| 06 | [ntuCMS](./spring_boot/ntuCMS) | Hệ thống quản lý nội dung (CMS) với Spring Boot. |
| 07 | [springbootdb_restapi](./spring_boot/springbootdb_restapi) | Xây dựng RESTful API và tích hợp Database bằng Spring Boot. |

---

## Hướng dẫn cài đặt IntelliJ IDEA và Chạy Project

### 1. Cài đặt môi trường
- **JDK (Java Development Kit)**: Tải và cài đặt JDK (khuyên dùng bản 11 hoặc 17 LTS) từ [Oracle](https://www.oracle.com/java/technologies/downloads/) hoặc [Adoptium](https://adoptium.net/).
- **IntelliJ IDEA**: Tải bản Community (Miễn phí) hoặc Ultimate tại [JetBrains](https://www.jetbrains.com/idea/download/).

### 2. Cấu hình IntelliJ IDEA để code
- **Bước 1**: Mở IntelliJ IDEA, chọn `Open`.
- **Bước 2**: Trỏ đến thư mục gốc của project này (`Java-core`).
- **Bước 3**: Thiết lập SDK: Vào `File` -> `Project Structure` -> `Project` -> Chọn phiên bản JDK bạn đã cài đặt.
- **Bước 4**: Chạy code: Chuột phải vào file `.java` có chứa hàm `main` (ví dụ: `ViDu.java`) và chọn `Run 'filename.main()'`.

### 3. Lưu ý về cấu trúc Project
Mỗi thư mục nhỏ trong các `step_xx` được thiết kế như một Project độc lập. Bạn có thể mở trực tiếp thư mục con đó để IntelliJ nhận diện đúng cấu trúc thư mục `src`.

## Tài liệu tham khảo học tập

Để học tập hiệu quả, bạn có thể tham khảo các nguồn uy tín sau:

1. **W3Schools Java Tutorial**: Nguồn học cơ bản, ngắn gọn, dễ hiểu. https://www.w3schools.com/java/
2. **GeeksforGeeks Java**: Chuyên sâu về thuật toán và cấu trúc dữ liệu trong Java. https://www.geeksforgeeks.org/java/
3. **Java Programming Language (Oracle Docs)**: Tài liệu chính thống từ nhà phát hành Java. https://docs.oracle.com/javase/tutorial/
4. **Java Design Patterns**: Nếu bạn muốn học nâng cao về thiết kế hệ thống. https://refactoring.guru/design-patterns/java
5. **Kênh Youtube (Tiếng Việt)**: Có thể tìm các khóa học của TITV hoặc các thầy cô trên Youtube để nghe giảng trực quan hơn.
