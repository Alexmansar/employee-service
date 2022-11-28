package org.alexmansar.service;

import org.alexmansar.model.Department;
import org.alexmansar.model.Employee;
import org.alexmansar.model.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployeeList();

    Employee getEmployee(long id);

    void addEmployee(Employee employee);

    void updateEmployee(Employee employee, EmployeeDto employeeDto);

    void removeEmployee(Employee employee);

    List<Employee> getAllEmployeeByDepartment(Department department);
}
