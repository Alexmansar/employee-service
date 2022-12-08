package org.alexmansar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee extends AbstractModel {
    String name;
    @Column(name = "last_name")
    String lastName;
    @ManyToOne
    @JoinColumn(name = "department_id")
    Department department;
    String phone;
    String email;
    String address;
    int salary;
    @Column(name = "birthday")
    LocalDateTime birthDate;
    @Column(name = "hiring_date")
    LocalDateTime hiringDate;

    public Employee(String name, String lastName, Department department, String phone, String email, int salary, String address, LocalDateTime birthDate, LocalDateTime hiringDate) {
        super();
        this.name = name;
        this.lastName = lastName;
        this.department = department;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.salary = salary;
        this.birthDate = birthDate;
        this.hiringDate = hiringDate;
    }
}