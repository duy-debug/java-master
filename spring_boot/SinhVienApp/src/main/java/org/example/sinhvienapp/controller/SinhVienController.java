package org.example.sinhvienapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class SinhVienController {
  @GetMapping("/")
  public String index(ModelMap m) {
    List<SinhVien> ds = Arrays.asList(new SinhVien("65133236", "Si Thanh", "2005", "Nam"),
            new SinhVien("65173623", "Tam Huy", "2005", "Nam"),
            new SinhVien("65863832", "Minh Quang", "2005", "Nam"));
    m.addAttribute("sinhviens", ds);
    return "index";
  }
}
