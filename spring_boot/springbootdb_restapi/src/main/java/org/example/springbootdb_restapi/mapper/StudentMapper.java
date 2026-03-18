package org.example.springbootdb_restapi.mapper;

import org.example.springbootdb_restapi.dto.StudentDTO;
import org.example.springbootdb_restapi.entity.Student;

public class StudentMapper {
  public static Student toEntity(StudentDTO studentDTO){
    Student student = new Student();
    student.setName(studentDTO.getName());
    student.setAge(studentDTO.getAge());
    return student;
  }
}
