package org.alexmansar.controller;

import lombok.extern.slf4j.Slf4j;
import org.alexmansar.view.frame.FrameView;
import org.alexmansar.view.frame.MainFrame;

import javax.swing.*;

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
        mainFrame.createFrame(this, departmentController, employeeController);
    }


    public void exit() {
        Object[] options = {"Yes", "No!"};
        int rc = JOptionPane.showOptionDialog(
                null, "Close app?",
                "Confirm exit", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        if (rc == 0) {
            log.info("APP finished success");
            System.exit(0);
        }
    }

    public void about() {
        FrameView.about();
    }
}
