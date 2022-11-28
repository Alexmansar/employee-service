package org.alexmansar.view;

import org.alexmansar.controller.DepartmentController;
import org.alexmansar.controller.EmployeeController;
import org.alexmansar.model.Department;
import org.alexmansar.model.dto.DepartmentDto;
import org.alexmansar.service.DepartmentService;
import org.alexmansar.utils.RegEx;
import org.alexmansar.utils.StringUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.util.List;

public class DepartmentTable extends AbstractTable {

    private void createGUI(String[][] data, DepartmentService departmentService, DepartmentController departmentController, EmployeeController employeeController) {
        JFrame frame = createFrame(500, 400, "DEPARTMENT");
        JPanel mainPane = getPanel(frame);
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        JPanel buttonPane = getPanel(frame);
        JPanel addPanel = getPanel(frame);
        addPanel.setVisible(true);
        addPanel.setPreferredSize(new Dimension(500, 100));
        createLabel("new department", addPanel);
        JTextField departmentNameField = createTextField("", addPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] columnNames = {
                "id",
                "Department",
                "Create date",
                "Update date",};
        JTable table = getTable(data, frame, mainPane, buttonPane, columnNames);
        mainPane.add(addPanel);
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(170);
        table.getColumnModel().getColumn(2).setPreferredWidth(145);
        table.getColumnModel().getColumn(3).setPreferredWidth(145);
        ActionListener addDepartmentListener = addListener(departmentService, table, addPanel, departmentNameField);
        ActionListener getAllEmployeesByDepartment = employeeController.getEmployeeTable().getAllEmployeeByDepartment(employeeController, departmentService, null);
        ActionListener getDepartmentById = e -> departmentController.getDepartment();
        ActionListener removeDepartmentListener = getRemoveListener(departmentService, table);
        ActionListener updateDepartmentListener = updateListener(departmentService, table);
        getButton("Remove", buttonPane, removeDepartmentListener);
        getButton("Add", addPanel, addDepartmentListener);
        getButton("Update", buttonPane, updateDepartmentListener);
        JButton clearButton = getButton("CLEAR", addPanel);
        clearDepartmentMouseListener(clearButton, departmentNameField);
        getButton("Print all employees", buttonPane, getAllEmployeesByDepartment);
        getButton("Find by id", buttonPane, getDepartmentById);
        frame.pack();
        frame.setVisible(true);
    }


    private ActionListener addListener(DepartmentService departmentService, JTable table, JPanel panel, JTextField departmentNameField) {
        return e -> {
            JLabel departmentNameCheckLabel = createCheckLabel(panel);
            departmentNameField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    super.keyReleased(e);
                    NAME_DEPARTMENT_CHECK = RegEx.checkName(departmentNameField, departmentNameCheckLabel);
                }
            });
            if (NAME_DEPARTMENT_CHECK) {
                String name = StringUtil.firstUpperCase(createText(departmentNameField));
                Department department = new Department(name);
                departmentService.addDepartment(department);
                FrameView.printDepartmentInfo(department, "New department success create and added to DB");
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{department.getId(), department.getName(), department.getCreateDate(), department.getUpdateDate()});
            } else {
                showCheckMessage();
            }
        };
    }

    private ActionListener updateListener(DepartmentService departmentService, JTable table) {
        return e -> {
            int row = table.getSelectedRow();
            Department department = departmentService.getDepartment(Long.parseLong(table.getValueAt(row, 0).toString()));
            JTextField departmentName = new JTextField();
            departmentName.setText(table.getValueAt(row, 1).toString());
            NAME_DEPARTMENT_CHECK = departmentName.toString().equals(department.getName());
            NAME_DEPARTMENT_CHECK = RegEx.checkName(table, 1, row);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            if (NAME_DEPARTMENT_CHECK) {
                DepartmentDto departmentDTO = new DepartmentDto(createText(departmentName));
                department.setUpdateDate(LocalDateTime.now());
                model.setValueAt(department.getUpdateDate(), row, 3);
                departmentService.updateDepartment(department, departmentDTO);
                FrameView.printDepartmentInfo(department, "Department " + department.getId() + " success update");
            } else {
                showMessage();
            }
        };
    }

    private ActionListener getRemoveListener(DepartmentService departmentService, JTable table) {
        return e -> {
            int row = table.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            long id = Long.parseLong(table.getValueAt(row, 0).toString());
            Department department = departmentService.getDepartment(id);
            departmentService.removeDepartment(department);
            model.removeRow(row);
            FrameView.printDepartmentInfo(null, "Car " + id + " success delete");
        };
    }

    public String[][] getDeepArrayFromList(List<Department> list) {
        String[][] strings = new String[list.size()][4];
        for (int i = 0; i < strings.length; i++) {
            strings[i][0] = String.valueOf(list.get(i).getId());
            strings[i][1] = list.get(i).getName();
            strings[i][2] = String.valueOf(list.get(i).getCreateDate());
            strings[i][3] = String.valueOf(list.get(i).getUpdateDate());
        }
        return strings;
    }

    public void createFrame(DepartmentService departmentService, List<Department> departments,
                            DepartmentController departmentController, EmployeeController employeeController) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            String[][] data = getDeepArrayFromList(departments);
            createGUI(data, departmentService, departmentController,  employeeController);
        });
    }

    private void showMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("You have to change input value");
        if (!NAME_DEPARTMENT_CHECK) {
            builder.append("\nnot correct name");
        }
        JOptionPane.showMessageDialog(null, builder.toString(), "ERROR", JOptionPane.ERROR_MESSAGE, null);
        builder.delete(0, builder.length()).append("You have to change input value");
    }
}
