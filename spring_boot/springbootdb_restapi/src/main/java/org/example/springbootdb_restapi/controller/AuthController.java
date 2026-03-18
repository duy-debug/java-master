package org.example.springbootdb_restapi.controller;

import org.example.springbootdb_restapi.dto.LoginRequest;
import org.example.springbootdb_restapi.entity.User;
import org.example.springbootdb_restapi.repository.UserRepository;
import org.example.springbootdb_restapi.security.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserRepository repository;

  public AuthController(UserRepository repository) {
    this.repository = repository;
  }
  @PostMapping("/register")
  public User register(@RequestBody User user){
    user.setRole("USER");
    return repository.save(user);
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginRequest request){
    User user = repository.findByUsername(request.getUsername()).orElseThrow();
    if(!user.getPassword().equals(request.getPassword())){
      throw new RuntimeException("Invalid password");
    }
    return JwtUtil.generateToken(user.getUsername());
  }
}
