package org.alexmansar.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.alexmansar.controller.dto.DepartmentDto;
import org.alexmansar.model.Department;
import org.alexmansar.repository.DepartmentRepository;
import org.alexmansar.service.DepartmentService;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

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
}
