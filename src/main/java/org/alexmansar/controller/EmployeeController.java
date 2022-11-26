package org.alexmansar.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.alexmansar.model.Employee;
import org.alexmansar.service.DepartmentService;
import org.alexmansar.service.EmployeeService;
import org.alexmansar.view.frame.AddFrame;
import org.alexmansar.view.frame.EmployeeTable;
import org.alexmansar.view.frame.FrameView;
import org.alexmansar.view.frame.UpdateFrame;

import javax.swing.*;
import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeController extends AbstractController {

    EmployeeService employeeService;
    EmployeeTable employeeTable;

    public EmployeeController( EmployeeService employeeService,EmployeeTable employeeTable) {
        this.employeeService = employeeService;
        this.employeeTable = employeeTable;
    }


    public void getEmployeeList(DepartmentService departmentService) {
        List<Employee> employeeList = employeeService.getEmployeeList();
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
        updateFrame.createEmployeeUpdateFrame(departmentService, employee);
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
            throw new RuntimeException();
        }
        return employee;
    }
}
