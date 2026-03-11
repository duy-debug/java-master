package org.example.bmi_calculator.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BMIController {
  @GetMapping("/index")
  public String index(){
    return "index"; // tạo trang index trong đó có form BMI calculator
  }
  @PostMapping("/bmi") // lấy dữ liệu từ form index
    public String calculatorBMI(HttpServletRequest thamso, ModelMap model) {
      String chieuCao = thamso.getParameter("height");
      String canNang = thamso.getParameter("weight");
      double height=0, weight=0, bmi;
      String danhGia;
      boolean flag = false;
      try{
        height = Double.parseDouble(chieuCao);
        if(height <= 0){
          model.addAttribute("heightError","Chiều cao phải lớn hơn 0");
          flag = true;
        }
      }
      catch(Exception e){
        model.addAttribute("heightError","Vui lòng nhập số!");
        flag = true;
      }
      try{
        weight = Double.parseDouble(canNang);
        if(weight <= 0){
          model.addAttribute("weightError", "Cân nặng phải lớn hơn 0");
          flag = true;
        }
      }
      catch(Exception e){
        model.addAttribute("weightError","Vui lòng nhập số!");
        flag = true;
      }
      if(flag){
        return "index";
      }
      bmi = weight / (height * height);
      if(bmi < 18.5){
        danhGia = "Gầy";
      }
      else if(bmi < 25){
        danhGia = "Bình thường";
      }
      else if(bmi < 30){
        danhGia = "Thừa cân";
      }
      else{
        danhGia = "Béo phì";
      }
      model.addAttribute("height", height);
      model.addAttribute("weight", weight);
      model.addAttribute("bmi",bmi);
      model.addAttribute("danhGia",danhGia);
      return "/bmi";
  }
}
