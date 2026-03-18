package org.example.springbootdb_restapi.controller;

import jakarta.validation.Valid;
import org.example.springbootdb_restapi.dto.StudentDTO;
import org.example.springbootdb_restapi.entity.Student;
import org.example.springbootdb_restapi.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
  private final StudentService service;

  public StudentController(StudentService service){
    this.service = service;
  }

  @GetMapping
  public List<Student> getAll(){
    return service.findAll();
  }
}

