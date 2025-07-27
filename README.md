Certainly! Here's a high-quality Spring Boot 3 CRUD application for managing employees using:

Spring Boot 3

Spring Data JPA

Java 17 syntax

Clean architecture and best practices

DTO usage for better separation

Lombok to reduce boilerplate (optional but recommended)

✅ 1. Employee Entity
java
Copy
Edit
package com.example.employeeapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;
}
✅ 2. EmployeeRepository
java
Copy
Edit
package com.example.employeeapp.repository;

import com.example.employeeapp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
✅ 3. EmployeeDTO
java
Copy
Edit
package com.example.employeeapp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
✅ 4. EmployeeService
java
Copy
Edit
package com.example.employeeapp.service;

import com.example.employeeapp.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO getEmployeeById(Long id);
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
}
✅ 5. EmployeeServiceImpl
java
Copy
Edit
package com.example.employeeapp.service.impl;

import com.example.employeeapp.dto.EmployeeDTO;
import com.example.employeeapp.entity.Employee;
import com.example.employeeapp.repository.EmployeeRepository;
import com.example.employeeapp.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = mapToEntity(employeeDTO);
        return mapToDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
        return mapToDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        existing.setFirstName(employeeDTO.getFirstName());
        existing.setLastName(employeeDTO.getLastName());
        existing.setEmail(employeeDTO.getEmail());

        return mapToDTO(employeeRepository.save(existing));
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    private Employee mapToEntity(EmployeeDTO dto) {
        return Employee.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .build();
    }
}
✅ 6. EmployeeController
java
Copy
Edit
package com.example.employeeapp.controller;

import com.example.employeeapp.dto.EmployeeDTO;
import com.example.employeeapp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
✅ 7. application.yml
yaml
Copy
Edit
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/employeedb
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
  application:
    name: employee-app
✅ 8. Main Class
java
Copy
Edit
package com.example.employeeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeAppApplication.class, args);
    }
}
Would you like me to:

Add validation (e.g., using @Valid)?

Create Swagger/OpenAPI docs?

Use H2 instead of MySQL for easy testing?

Export this as a ZIP or GitHub repo structure?

Let me know!








You said:
I dont want to use @RequiredArgsConstructor annotation then what will be EmployeeController  and EmployeeService class code ?


ChatGPT said:
Great! If you don't want to use Lombok's @RequiredArgsConstructor, then you need to manually define the constructor in both the EmployeeController and the EmployeeServiceImpl classes.

Here’s how to rewrite those two classes without using @RequiredArgsConstructor:

✅ Updated EmployeeController (No Lombok)
java
Copy
Edit
package com.example.employeeapp.controller;

import com.example.employeeapp.dto.EmployeeDTO;
import com.example.employeeapp.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    // Constructor injection (manually written)
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
✅ Updated EmployeeServiceImpl (No Lombok)
java
Copy
Edit
package com.example.employeeapp.service.impl;

import com.example.employeeapp.dto.EmployeeDTO;
import com.example.employeeapp.entity.Employee;
import com.example.employeeapp.repository.EmployeeRepository;
import com.example.employeeapp.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    // Constructor injection (manually written)
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = mapToEntity(employeeDTO);
        return mapToDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
        return mapToDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        existing.setFirstName(employeeDTO.getFirstName());
        existing.setLastName(employeeDTO.getLastName());
        existing.setEmail(employeeDTO.getEmail());

        return mapToDTO(employeeRepository.save(existing));
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    private Employee mapToEntity(EmployeeDTO dto) {
        return Employee.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .build();
    }
}
