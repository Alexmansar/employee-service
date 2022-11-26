package org.alexmansar.service;

import org.alexmansar.model.Department;
import org.alexmansar.model.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    List<Department> getDepartmentList();

    Department getDepartment(long id);

    void addDepartment(Department department);

    void updateDepartment(Department department, DepartmentDto departmentDTO);

    void removeDepartment(Department department);
}