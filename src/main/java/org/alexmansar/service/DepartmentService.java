package org.alexmansar.service;

import org.alexmansar.controller.dto.DepartmentDto;
import org.alexmansar.model.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getDepartmentList();

    Department getDepartment(long id);

    void addDepartment(Department department);

    void updateDepartment(Department department, DepartmentDto departmentDTO);

    void removeDepartment(Department department);
}