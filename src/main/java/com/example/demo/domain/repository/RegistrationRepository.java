package com.example.demo.domain.repository;

import com.example.demo.domain.model.Course;
import com.example.demo.domain.model.Registration;
import com.example.demo.domain.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

}
