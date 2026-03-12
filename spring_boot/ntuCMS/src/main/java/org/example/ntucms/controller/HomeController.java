package org.example.ntucms.controller;

import org.example.ntucms.models.Page;
import org.example.ntucms.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

  private final List<Page> dsTrang = new ArrayList<>();
  private final List<Post> dsBaiViet = new ArrayList<>();

  public HomeController() {
    dsTrang.add(new Page("1", "C\u00f4ng ngh\u1ec7", "CN", "N\u1ed9i dung trang C\u00f4ng ngh\u1ec7", "0"));
    dsTrang.add(new Page("2", "Gi\u00e1o d\u1ee5c", "GD", "N\u1ed9i dung trang Gi\u00e1o d\u1ee5c", "0"));
    dsTrang.add(new Page("3", "Th\u1ec3 thao", "TT", "N\u1ed9i dung trang Th\u1ec3 thao", "0"));

    dsBaiViet.add(new Post("1", "An ninh", "B\u00e0i vi\u1ebft v\u1ec1 an ninh", "10"));
    dsBaiViet.add(new Post("2", "X\u00e3 h\u1ed9i", "B\u00e0i vi\u1ebft v\u1ec1 x\u00e3 h\u1ed9i", "11"));
    dsBaiViet.add(new Post("3", "Th\u1ec3 thao", "B\u00e0i vi\u1ebft v\u1ec1 b\u00f3ng \u0111\u00e1", "12"));
  }

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/page/all")
  public String getTatCaTrang(ModelMap model) {
    model.addAttribute("dsTrang", dsTrang);
    return "allpage";
  }

  @GetMapping("/page/view/{id}")
  public String xemTrang(@PathVariable String id, ModelMap model) {
    Page page = dsTrang.stream()
        .filter(item -> item.getId().equals(id))
        .findFirst()
        .orElse(null);

    model.addAttribute("page", page);
    return "viewpage";
  }

  @GetMapping("/page/delete/{id}")
  public String xoaTrang(@PathVariable String id) {
    dsTrang.removeIf(page -> page.getId().equals(id));
    return "redirect:/page/all";
  }

  @GetMapping("/page/new")
  public String formThemTrang() {
    return "newpage";
  }

  @PostMapping("/page/new")
  public String themTrang(Page page) {
    if (page.getContent() == null || page.getContent().isBlank()) {
      page.setContent("Ch\u01b0a c\u00f3 n\u1ed9i dung");
    }
    if (page.getParentPage() == null || page.getParentPage().isBlank()) {
      page.setParentPage("0");
    }

    dsTrang.add(page);
    return "redirect:/page/all";
  }

  @GetMapping("/post/all")
  public String getTatCaBaiViet(ModelMap model) {
    model.addAttribute("dsBaiViet", dsBaiViet);
    return "allpost";
  }

  @GetMapping("/post/view/{id}")
  public String xemPost(@PathVariable String id, ModelMap model) {
    Post post = dsBaiViet.stream()
        .filter(item -> item.getId().equals(id))
        .findFirst()
        .orElse(null);

    model.addAttribute("post", post);
    return "viewpost";
  }

  @GetMapping("/post/delete/{id}")
  public String xoaPost(@PathVariable String id) {
    dsBaiViet.removeIf(post -> post.getId().equals(id));
    return "redirect:/post/all";
  }

  @GetMapping("/post/new")
  public String formThemPost() {
    return "newpost";
  }

  @PostMapping("/post/new")
  public String themPost(Post post) {
    if (post.getContent() == null || post.getContent().isBlank()) {
      post.setContent("Ch\u01b0a c\u00f3 n\u1ed9i dung");
    }

    dsBaiViet.add(post);
    return "redirect:/post/all";
  }
}