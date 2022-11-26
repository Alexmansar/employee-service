package org.alexmansar.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.alexmansar.model.Department;
import org.alexmansar.service.DepartmentService;
import org.alexmansar.view.frame.AddFrame;
import org.alexmansar.view.frame.DepartmentTable;
import org.alexmansar.view.frame.FrameView;
import org.alexmansar.view.frame.UpdateFrame;

import javax.swing.*;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Getter
@NoArgsConstructor
public class DepartmentController extends AbstractController {
    DepartmentService departmentService;
    DepartmentTable departmentTable;


    public DepartmentController(DepartmentService departmentService, DepartmentTable departmentTable) {
        this.departmentService = departmentService;
        this.departmentTable = departmentTable;
    }

    public void getDepartmentList() {
        List<Department> departmentList = departmentService.getDepartmentList();
        departmentTable.createFrame(departmentService, departmentList, this);
    }


    public void getDepartment() {
        Department department;
        try {
            department = findById();
            FrameView.printDepartmentInfo(department, "Information about department: ");
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }

    public void removeDepartment() {
        Department department = findById();
        departmentService.removeDepartment(department);
        FrameView.printDepartmentInfo(department, "Department success remove: ");
    }

    public void updateDepartment(UpdateFrame updateFrame) {
        Department department = findById();
        updateFrame.createDepartmentUpdateFrame(departmentService, department);

    }

    public void addDepartment(AddFrame addFrame) {
        addFrame.createAddDepartmentFrame(departmentService, null);
    }

    private Department findById() {
        long id = FrameView.getId();
        Department department = departmentService.getDepartment(id);
        if (department == null) {
            JOptionPane.showMessageDialog(null, "id: " + id + " not found", "Error", JOptionPane.ERROR_MESSAGE);
            log.error("id: {} not found", id);
            throw new RuntimeException();
        }
        return department;
    }
}