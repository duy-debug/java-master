package org.example.springbootdb_restapi.service;

import org.example.springbootdb_restapi.entity.Student;
import org.example.springbootdb_restapi.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentService {

  private final StudentRepository repository;

  public StudentService(StudentRepository repository){
    this.repository = repository;
  }

  public List<Student> getAllStudents(){
    return repository.findAll();
  }
  public Student save(Student student){
    return repository.save(student);
  }
  public Student update(Long id, Student student){
    Student s = repository.findById(id).orElseThrow();
    s.setName(student.getName());
    s.setAge(student.getAge());
    return repository.save(s);
  }
  public void delete(Long id){
    repository.deleteById(id);
  }
}
