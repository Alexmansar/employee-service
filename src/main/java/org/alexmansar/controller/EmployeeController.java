package org.alexmansar.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.alexmansar.model.Department;
import org.alexmansar.model.Employee;
import org.alexmansar.service.DepartmentService;
import org.alexmansar.service.EmployeeService;
import org.alexmansar.view.AddFrame;
import org.alexmansar.view.EmployeeTable;
import org.alexmansar.view.FrameView;
import org.alexmansar.view.UpdateFrame;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Getter
@RequiredArgsConstructor
public class EmployeeController {

    EmployeeService employeeService;
    EmployeeTable employeeTable;


    public void getEmployeeList(DepartmentService departmentService) {
        List<Employee> employeeList = employeeService.getEmployeeList();
        employeeTable.createFrame(employeeService, employeeList, this, departmentService);
    }

    public void getAllEmployeeByDepartment(JComboBox<Department> departmentJComboBox, DepartmentService departmentService) {
        Department department = (Department) departmentJComboBox.getSelectedItem();
        List<Employee> employeeList = Objects.requireNonNull(department).getEmployeeList();
        employeeTable.createFrame(employeeService, employeeList, this, departmentService);
    }


    public void getEmployee() {
        Employee employee;
        try {
            employee = findById();
            FrameView.printEmployeeInfo(employee, "Information about employee: ");
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }

    public void removeEmployee() {
        Employee employee = findById();
        employeeService.removeEmployee(employee);
        FrameView.printEmployeeInfo(employee, "Employee success remove: ");
    }

    public void updateEmployee(UpdateFrame updateFrame, DepartmentService departmentService) {
        Employee employee = findById();
        updateFrame.createEmployeeUpdateFrame(departmentService, employeeService, employee);
    }

    public void addEmployee(AddFrame addFrame, DepartmentService departmentService) {
        addFrame.createAddEmployeeFrame(employeeService, departmentService, null);
    }

    private Employee findById() {
        long id = FrameView.getId();
        Employee employee = employeeService.getEmployee(id);
        if (employee == null) {
            JOptionPane.showMessageDialog(null, "id: " + id + " not found", "Error", JOptionPane.ERROR_MESSAGE);
            log.error("id: {} not found", id);

        }
        return employee;
    }
}
