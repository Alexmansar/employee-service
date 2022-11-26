package org.alexmansar.view.frame;

import org.alexmansar.model.Department;
import org.alexmansar.model.Employee;
import org.alexmansar.model.dto.DepartmentDto;
import org.alexmansar.service.DepartmentService;
import org.alexmansar.utils.StringUtil;
import org.alexmansar.utils.RegEx;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UpdateFrame extends AbstractFrame {

    public void createDepartmentUpdateFrame(DepartmentService departmentService, Department department) {
        JFrame frame = createFrame(240, 150, "Update department");
        JPanel panel = getPanel(frame);

        createLabel("Department name", panel);
        JLabel departmentNameCheckLabel = createCheckLabel(panel);
        JTextField departmentNameField = createTextField(department.getName(), panel);
        departmentNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                NAME_DEPARTMENT_CHECK = RegEx.checkName(departmentNameField, departmentNameCheckLabel);
            }
        });
        JButton updateButton = getButton("UPDATE", panel);
        JButton clearButton = getButton("CLEAR", panel);
        updateButton.addActionListener(e -> {
            if (NAME_DEPARTMENT_CHECK) {
                DepartmentDto departmentDto = new DepartmentDto(StringUtil.firstUpperCase(createText(departmentNameField)));
                departmentService.updateDepartment(department, departmentDto);
                FrameView.printDepartmentInfo(department, "Department " + department.getId() + " success update");
                frame.setVisible(false);
            } else {
                showCheckMessage();
            }
        });
        clearDepartmentMouseListener(clearButton, departmentNameField);
        frame.revalidate();
    }


    public void createEmployeeUpdateFrame(DepartmentService departmentService, Employee employee) {
       //JFrame frame = createFrame(240, 150, "Update employee");
       //JPanel panel = getPanel(frame);

       //createLabel("Department name", panel);
       //JLabel departmentNameCheckLabel = createCheckLabel(panel);
       //JTextField departmentNameField = createTextField(employee.getName(), panel);
       //departmentNameField.addKeyListener(new KeyAdapter() {
       //    @Override
       //    public void keyReleased(KeyEvent e) {
       //        super.keyReleased(e);
       //        NAME_DEPARTMENT_CHECK = RegEx.checkName(departmentNameField, departmentNameCheckLabel);
       //    }
       //});
       //JButton updateButton = getButton("UPDATE", panel);
       //JButton clearButton = getButton("CLEAR", panel);
       //updateButton.addActionListener(e -> {
       //    if (NAME_DEPARTMENT_CHECK) {
       //        DepartmentDto departmentDto = new DepartmentDto(ConsoleUtil.firstUpperCase(createText(departmentNameField)));
       //        departmentService.updateDepartment(employee, departmentDto);
       //        FrameView.printDepartmentInfo(employee, "Department " + employee.getId() + " success update");
       //        frame.setVisible(false);
       //    } else {
       //        showCheckMessage();
       //    }
       //});
       //clearDepartmentMouseListener(clearButton, departmentNameField);
       //frame.revalidate();
    }
}