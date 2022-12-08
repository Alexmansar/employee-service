package org.alexmansar.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.alexmansar.view.MainFrame;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class FrameController {

    DepartmentController departmentController;
    EmployeeController employeeController;
    MainFrame mainFrame;

    public void run() {
        mainFrame.createFrame(departmentController, employeeController);
    }
}
