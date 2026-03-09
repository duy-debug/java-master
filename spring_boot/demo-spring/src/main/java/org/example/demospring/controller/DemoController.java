package org.example.demospring.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemoController {
  // ví dụ truyền dữ liệu sang View index (trang index.html)
  @GetMapping("/vd1")
  public String getVidu1(ModelMap model){
    // Xử lý
    String kq = "Dữ liệu này đến từ Controller";
    model.addAttribute("tb",kq);
    return "index";
  }
  // Lấy giá trị tham số URL tại controller cách 2
  @GetMapping("/vd2")
  public String getVidu2(HttpServletRequest thamSo, ModelMap model){
    String tenNhanDuoc = thamSo.getParameter("ten");
    String mkNhanDuoc = thamSo.getParameter("mk");
    model.addAttribute("name",tenNhanDuoc);
    model.addAttribute("pass",mkNhanDuoc);
    return "index2"; // index2.html sẽ hiện tên nhân được từ URL
  }
  // Lấy giá trị tham số URL tại controller cách 2
  @GetMapping("/vd3")
  public String getVidu3(@RequestParam("ten") String tenP, @RequestParam("mk") String mkP, ModelMap model){
    model.addAttribute("name",tenP);
    model.addAttribute("pass",mkP);
    return "index2"; // index2.html sẽ hiện tên nhân được từ URL
  }
  @GetMapping("/login")
  public String dangNhap(){
    return "login"; // tạo trang login.html, trong đó có form có: action='/vd4'' method=post
  }
  // Lấy dữ liệu từ form trong trang login.html
  @PostMapping("/vd4")
  public String postVidu4(HttpServletRequest thamso, ModelMap model){
    String tenNhanDuoc = thamso.getParameter("uname");
    String mkNhanDuoc = thamso.getParameter("upass");
    model.addAttribute("name",tenNhanDuoc);
    model.addAttribute("pass",mkNhanDuoc);
    return "index4"; // login.html sẽ hiện tên nhận được từ URL
  }
}
