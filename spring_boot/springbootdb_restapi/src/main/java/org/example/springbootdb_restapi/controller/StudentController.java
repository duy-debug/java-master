package org.example.springbootdb_restapi.controller;

import org.example.springbootdb_restapi.dto.StudentDTO;
import org.example.springbootdb_restapi.entity.Student;
import org.example.springbootdb_restapi.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
  private final StudentService service;

  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping
  public List<Student> findAll() {
    return service.getAllStudents();
  }

  @PostMapping
  public Student add(@RequestBody StudentDTO studentDTO) {
    Student student = new Student();
    student.setName(studentDTO.getName());
    student.setAge(studentDTO.getAge());
    return service.save(student);
  }

  @PutMapping("/{id}")
  public Student update(@PathVariable Long id, @RequestBody Student student){
    return service.update(id, student);
  }
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long id) {
    service.delete(id);
    return "Deleted successfully";
  }
}

