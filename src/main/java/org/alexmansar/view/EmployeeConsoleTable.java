package org.alexmansar.view;

import org.alexmansar.model.Employee;

import java.util.List;

public class EmployeeConsoleTable extends Table {
    String topFormat = "%s%38s%38s%n";
    String tableName = "EMPLOYEES";

    public void createTable(Employee employee) {
        createTableTitle();
        printEmployee(employee);
        createLine();
    }

    String format = "%s %-2s %s %-15s %s %-15s  %s %-20s %s %-15s%s %-25s%s%-25s%s%-6s%s %-16s %s %-16s%s %-16s %s %-16s%s%n";

    private void printEmployee(Employee employee) {
        System.out.printf(format,
                C, employee.getId(),
                C, employee.getName(),
                C, employee.getLastName(),
                C, employee.getDepartment().getName(),
                C, employee.getPhone(),
                C, employee.getEmail(),
                C, employee.getAddress(),
                C, employee.getSalary(),
                C, employee.getBirthDate(),
                C, employee.getHiringDate(),
                C, employee.getCreateDate(),
                C, employee.getUpdateDate(),
                C);
    }

    public void createTable(List<Employee> employees) {
        createTableTitle();
        for (Employee employee : employees) {
            printEmployee(employee);
        }
        createLine();
    }

    protected void createTableTitle() {
        super.createTableTitle(topFormat, tableName);
        System.out.printf(format
                , C, "ID", C, "name", C, "last name", C,
                "Department", C, "phone", C, "email", C,
                "address", C, "salary", C, "birthday", C,
                "hiring date", C, "create date", C, "Update date", C);
        createLine();
    }
}
