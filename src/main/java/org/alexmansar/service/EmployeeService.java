package org.alexmansar.service;

import org.alexmansar.controller.dto.EmployeeDto;
import org.alexmansar.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployeeList();

    Employee getEmployee(long id);

    void addEmployee(Employee employee);

    void updateEmployee(Employee employee, EmployeeDto employeeDto);

    void removeEmployee(Employee employee);

}
