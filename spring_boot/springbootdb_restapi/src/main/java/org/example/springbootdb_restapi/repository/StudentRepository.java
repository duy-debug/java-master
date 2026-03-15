package org.example.springbootdb_restapi.repository;

import org.example.springbootdb_restapi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
