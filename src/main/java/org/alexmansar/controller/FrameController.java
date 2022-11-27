package org.alexmansar.controller;

import lombok.extern.slf4j.Slf4j;
import org.alexmansar.view.MainFrame;

@Slf4j
public class FrameController {

    DepartmentController departmentController;
    EmployeeController employeeController;
    MainFrame mainFrame;


    public FrameController(DepartmentController departmentController, EmployeeController employeeController, MainFrame mainFrame) {
        this.departmentController = departmentController;
        this.employeeController = employeeController;
        this.mainFrame = mainFrame;
    }

    public void run() {
        mainFrame.createFrame(departmentController, employeeController);
    }
}
