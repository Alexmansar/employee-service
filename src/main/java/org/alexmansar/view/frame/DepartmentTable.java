package org.alexmansar.view.frame;

import org.alexmansar.controller.DepartmentController;
import org.alexmansar.model.Department;
import org.alexmansar.model.dto.DepartmentDto;
import org.alexmansar.service.DepartmentService;
import org.alexmansar.utils.RegEx;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

public class DepartmentTable extends AbstractFrame {

    private void createGUI(String[][] data, DepartmentService departmentService, DepartmentController departmentController) {
        JFrame frame = createFrame(500, 400, "DEPARTMENT");
        JPanel mainPane = getPanel(frame);
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        JPanel buttonPane = getPanel(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] columnNames = {
                "id",
                "Department",
                "Create date",
                "Update date",};
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(defaultTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPane.add(scrollPane);
        mainPane.add(buttonPane);
        frame.add(mainPane);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(170);
        table.getColumnModel().getColumn(2).setPreferredWidth(145);
        table.getColumnModel().getColumn(3).setPreferredWidth(145);
        AddFrame addFrame = new AddFrame();
        ActionListener addDepartmentListener = e -> addFrame.createAddDepartmentFrame(departmentService, table);
        ActionListener getAllEmployeesByDepartment = e -> departmentController.getDepartmentList();
        ActionListener getDepartmentById = e -> departmentController.getDepartment();
        ActionListener removeDepartmentListener = getRemoveListener(departmentService, table);
        ActionListener updateDepartmentListener = updateListener(departmentService, table);
        getButton("Remove", buttonPane, removeDepartmentListener);
        getButton("Update", buttonPane, updateDepartmentListener);
        getButton("Add", buttonPane, addDepartmentListener);
        getButton("Print all employees", buttonPane, getAllEmployeesByDepartment);
        getButton("Find by id", buttonPane, getDepartmentById);
        frame.pack();
        frame.setVisible(true);
    }


    private ActionListener updateListener(DepartmentService departmentService, JTable table) {
        return e -> {
            int row = table.getSelectedRow();
            //     Transaction transaction = session.beginTransaction();
            Department department = departmentService.getDepartment(Long.parseLong(table.getValueAt(row, 0).toString()));

            JTextField departmentName = new JTextField();
            departmentName.setText(table.getValueAt(row, 1).toString());

            NAME_DEPARTMENT_CHECK = departmentName.toString().equals(department.getName());
            NAME_DEPARTMENT_CHECK = RegEx.checkName(table, 1, row);

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            if (NAME_DEPARTMENT_CHECK) {
                DepartmentDto departmentDTO = new DepartmentDto(createText(departmentName));
                // department.setName(createText(departmentName));
                department.setUpdateDate(LocalDateTime.now());
                //transaction.commit();model.setValueAt(departmentDTO.getName(), row, 1);
                model.setValueAt(department.getUpdateDate(), row, 3);
                departmentService.updateDepartment(department, departmentDTO);
                FrameView.printDepartmentInfo(department, "Department " + department.getId() + " success update");
                //     frame.setVisible(false);
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

    public void createFrame(DepartmentService departmentService, List<Department> departments, DepartmentController departmentController) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            String[][] data = getDeepArrayFromList(departments);
            createGUI(data, departmentService, departmentController);
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
}//
