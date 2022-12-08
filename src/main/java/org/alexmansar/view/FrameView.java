package org.alexmansar.view;

import lombok.extern.slf4j.Slf4j;
import org.alexmansar.model.Department;
import org.alexmansar.model.Employee;

import javax.swing.*;

@Slf4j
public class FrameView {

    public static long getId() {
        long id = 0;
        try {
            id = Long.parseLong(JOptionPane.showInputDialog(null, "Enter id"));
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }
        return id;
    }


    public static void printDepartmentInfo(Department department, String str) {
        try {
            String message = "id: " + department.getId() + "\n"
                    + "department: " + department.getName() + "\n"
                    + "create date: " + department.getCreateDate().toString() + "\n"
                    + "update date: " + department.getUpdateDate() + "\n"
                    + "number of employees: " + department.getEmployeeList().size() + "\n";
            JOptionPane.showMessageDialog(null, message, str, JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }
    }

    public static void printEmployeeInfo(Employee employee, String str) {
        try {
            String message = "id: " + employee.getId() + "\n"
                    + "name:: " + employee.getName() + "\n"
                    + "last name: " + employee.getLastName() + "\n"
                    + "department: " + employee.getDepartment() + "\n"
                    + "phone: " + employee.getPhone() + "\n"
                    + "email: " + employee.getEmail() + "\n"
                    + "address: " + employee.getAddress() + "\n"
                    + "salary: " + employee.getSalary() + "  USD\n"
                    + "birthday: " + employee.getBirthDate() + "\n"
                    + "hiring date : " + employee.getHiringDate() + "\n"
                    + "create date: " + employee.getCreateDate().toString() + "\n"
                    + "update date: " + employee.getUpdateDate() + "\n";
            JOptionPane.showMessageDialog(null, message, str, JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }
    }

    public static void about() {
        String String = """
                App name: Employee-service
                Version: 1.0
                Desktop app
                Product year: 2022
                """;
        JOptionPane.showMessageDialog(null, String, "Information: ", JOptionPane.INFORMATION_MESSAGE);
    }
}
