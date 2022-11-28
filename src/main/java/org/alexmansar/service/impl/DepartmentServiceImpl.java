package org.alexmansar.service.impl;

import org.alexmansar.model.Department;
import org.alexmansar.model.Employee;
import org.alexmansar.model.dto.DepartmentDto;
import org.alexmansar.repository.DepartmentRepository;
import org.alexmansar.service.DepartmentService;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    DepartmentRepository departmentRepository;

    @Override
    public List<Department> getDepartmentList() {
        return departmentRepository.getDepartmentList();
    }

    @Override
    public Department getDepartment(long id) {
        return departmentRepository.getDepartment(id);
    }

    @Override
    public void addDepartment(Department department) {
        departmentRepository.addDepartment(department);
    }

    @Override
    public void updateDepartment(Department department, DepartmentDto departmentDTO) {
        departmentRepository.updateDepartment(department, departmentDTO);
    }

    @Override
    public void removeDepartment(Department department) {
        departmentRepository.removeDepartment(department);
    }

    @Override
    public List<Employee> getAllEmployeeByDepartment(Department department) {
        return departmentRepository.getAllEmployeeByDepartment(department);
    }
}
