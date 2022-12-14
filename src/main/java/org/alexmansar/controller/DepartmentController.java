package org.alexmansar.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.alexmansar.model.Department;
import org.alexmansar.service.DepartmentService;
import org.alexmansar.view.AddFrame;
import org.alexmansar.view.DepartmentTable;
import org.alexmansar.view.FrameView;
import org.alexmansar.view.UpdateFrame;

import javax.swing.*;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Getter
@RequiredArgsConstructor
public class DepartmentController {
    DepartmentService departmentService;
    DepartmentTable departmentTable;

    public void getDepartmentList( ) {
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