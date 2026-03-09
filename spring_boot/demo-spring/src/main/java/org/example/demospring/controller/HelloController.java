package org.example.demospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Đánh dấu đây là một controller
// Nơi tiếp nhận các request từ phía người dùng
@Controller
public class HelloController {
  // Đón nhận request
  @GetMapping("/") // Nếu người dùng request tới địa chỉ "/"
  public String hello(){
    return "index"; // trả về file index.html
  }
  @GetMapping("/about")
  public String about(){
    return "about";
  }
}
