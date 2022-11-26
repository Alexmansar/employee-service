package org.alexmansar.view;

import org.alexmansar.model.Department;

import java.util.List;

public class DepartmentTableConsole extends Table {
    String format = "%s %-5s %s %-20s %s %-20s %s %-20s%s%n";
    String topFormat = "%s%38s%38s%n";
    String tableName = "DEPARTMENTS";

    public void createTable(Department department) {
        createTableTitle();
        printDepartment(department);
        createLine();
    }

    public void createTable(List<Department> departments) {
        createTableTitle();
        for (Department department : departments) {
            printDepartment(department);
        }
        createLine();
    }

    private void printDepartment(Department department) {
        System.out.printf(format,
                C, department.getId(),
                C, department.getName(),
                C, department.getCreateDate(),
                C, department.getUpdateDate(),
                C);
    }

    protected void createTableTitle() {
        super.createTableTitle(topFormat, tableName);
        System.out.printf(format
                , C, "ID", C, "Department", C, "create date", C, "Update date", C);
        createLine();
    }
}