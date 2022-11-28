package org.alexmansar.controller.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.alexmansar.model.Department;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class EmployeeDto {
    String name;
    String lastName;
    Department department;
    String phone;
    String email;
    String address;
    int salary;
    LocalDateTime birthDate;
    LocalDateTime hiringDate;
}
