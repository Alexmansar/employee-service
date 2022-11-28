package org.alexmansar.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.alexmansar.controller.dto.EmployeeDto;
import org.alexmansar.model.Employee;
import org.alexmansar.repository.EmployeeRepository;
import org.alexmansar.service.EmployeeService;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getEmployeeList() {
        return employeeRepository.getEmployeeList();
    }

    @Override
    public Employee getEmployee(long id) {
        return employeeRepository.getEmployee(id);
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.addEmployee(employee);
    }

    @Override
    public void updateEmployee(Employee employee, EmployeeDto employeeDto) {
        employeeRepository.updateEmployee(employee, employeeDto);
    }

    @Override
    public void removeEmployee(Employee employee) {
        employeeRepository.removeEmployee(employee);
    }

}

