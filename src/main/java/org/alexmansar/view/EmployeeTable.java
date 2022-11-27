package org.alexmansar.view;

import lombok.extern.slf4j.Slf4j;
import org.alexmansar.controller.EmployeeController;
import org.alexmansar.model.Department;
import org.alexmansar.model.Employee;
import org.alexmansar.model.dto.EmployeeDto;
import org.alexmansar.service.DepartmentService;
import org.alexmansar.service.EmployeeService;
import org.alexmansar.utils.StringUtil;
import org.alexmansar.utils.RegEx;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EmployeeTable extends AbstractFrame {

    private void createGUI(String[][] data, EmployeeService employeeService, EmployeeController employeeController, DepartmentService departmentService) {
        JFrame frame = createFrame(1200, 400, "EMPLOYEE");
        JPanel mainPane = getPanel(frame);
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        JPanel buttonPane = getPanel(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] columnNames = {
                "id",
                "name",
                "last name",
                "Department",
                "phone",
                "email",
                "address",
                "salary",
                "birthday",
                "hiring date",
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
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        JComboBox<Department> departmentJComboBox = getDepartmentJComboBox(departmentService, table, 3);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(200);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);
        table.getColumnModel().getColumn(7).setPreferredWidth(50);
        table.getColumnModel().getColumn(8).setPreferredWidth(120);
        table.getColumnModel().getColumn(9).setPreferredWidth(120);
        table.getColumnModel().getColumn(10).setPreferredWidth(120);
        table.getColumnModel().getColumn(11).setPreferredWidth(120);
        AddFrame addFrame = new AddFrame();
        ActionListener addEmployeeListener = e -> addFrame.createAddEmployeeFrame(employeeService, departmentService, null);
        ActionListener getAllEmployeeByDepartment = getAllEmployeeByDepartment(employeeService, employeeController, departmentService);
        ActionListener getEmployeeById = e -> employeeController.addEmployee(addFrame, departmentService);
        ActionListener updateEmployeeListener = updateListener(employeeService, table, frame, departmentJComboBox);
        ActionListener removeEmployeeListener = getRemoveListener(employeeService, table);
        getButton("Update", buttonPane, updateEmployeeListener);
        getButton("Remove", buttonPane, removeEmployeeListener);
        getButton("Add", buttonPane, addEmployeeListener);
        getButton("Find by id", buttonPane, getEmployeeById);
        getButton("Find all employee by department", buttonPane, getAllEmployeeByDepartment);
        frame.pack();
        frame.setVisible(true);
    }

    private ActionListener updateListener(EmployeeService employeeService, JTable table, JFrame frame, JComboBox<Department> departmentJComboBox) {
        return e -> {
            int row = table.getSelectedRow();
            Employee employee = employeeService.getEmployee(Long.parseLong(table.getValueAt(row, 0).toString()));
            JTextField firstNameField = new JTextField();
            firstNameField.setText(table.getValueAt(row, 1).toString());
            FIRST_NAME_CHECK = firstNameField.toString().equals(employee.getName());
            FIRST_NAME_CHECK = RegEx.checkName(table, 1, row);

            JTextField lastNameField = new JTextField();
            lastNameField.setText(table.getValueAt(row, 2).toString());
            LAST_NAME_CHECK = lastNameField.toString().equals(employee.getLastName());
            LAST_NAME_CHECK = RegEx.checkName(table, 2, row);

            JTextField phoneField = new JTextField();
            phoneField.setText(table.getValueAt(row, 4).toString());
            PHONE_CHECK = phoneField.toString().equals(employee.getPhone());
            PHONE_CHECK = RegEx.checkPhone(table, 4, row);

            JTextField emailField = new JTextField();
            emailField.setText(table.getValueAt(row, 5).toString());
            EMAIL_CHECK = emailField.toString().equals(employee.getEmail());
            EMAIL_CHECK = RegEx.checkEmail(table, 5, row);

            JTextField addressField = new JTextField();
            addressField.setText(table.getValueAt(row, 6).toString());
            ADDRESS_CHECK = addressField.toString().equals(employee.getEmail());
            ADDRESS_CHECK = RegEx.checkAddress(table, 6, row);

            JTextField salaryField = new JTextField();
            salaryField.setText(table.getValueAt(row, 7).toString());
            SALARY_CHECK = salaryField.toString().equals(employee.getEmail());
            SALARY_CHECK = RegEx.checkSalary(table, 7, row);

            JTextField birthdayField = new JTextField();
            birthdayField.setText(table.getValueAt(row, 8).toString());
            BIRTHDAY_CHECK = birthdayField.toString().equals(employee.getEmail());
            BIRTHDAY_CHECK = RegEx.checkBirthday(table, 8, row);

            JTextField hiringField = new JTextField();
            hiringField.setText(table.getValueAt(row, 9).toString());
            HIRING_DATE_CHECK = hiringField.toString().equals(employee.getEmail());
            HIRING_DATE_CHECK = RegEx.checkHiringDate(table, 9, row);

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            if (FIRST_NAME_CHECK && LAST_NAME_CHECK && PHONE_CHECK && EMAIL_CHECK && ADDRESS_CHECK && SALARY_CHECK && BIRTHDAY_CHECK && HIRING_DATE_CHECK) {
                FIRST_NAME = StringUtil.firstUpperCase(createText(firstNameField));
                LAST_NAME = StringUtil.firstUpperCase(createText(lastNameField));
                DEPARTMENT = (Department) departmentJComboBox.getSelectedItem();
                EMAIL = StringUtil.firstUpperCase(createText(emailField));
                PHONE = StringUtil.firstUpperCase(createText(phoneField));
                ADDRESS = StringUtil.firstUpperCase(createText(addressField));
                SALARY = Integer.parseInt(StringUtil.firstUpperCase(createText(salaryField)));
                BIRTHDAY = createDate(birthdayField);
                HIRING_DATE = createDate(hiringField);
                EmployeeDto employeeDto = new EmployeeDto(FIRST_NAME, LAST_NAME, DEPARTMENT, PHONE, EMAIL, ADDRESS, SALARY, BIRTHDAY, HIRING_DATE);
                employee.setUpdateDate(LocalDateTime.now());
                model.setValueAt(employeeDto.getName(), row, 1);
                model.setValueAt(employeeDto.getLastName(), row, 2);
                model.setValueAt(employeeDto.getDepartment(), row, 3);
                model.setValueAt(employeeDto.getPhone(), row, 4);
                model.setValueAt(employeeDto.getEmail(), row, 5);
                model.setValueAt(employeeDto.getAddress(), row, 6);
                model.setValueAt(employeeDto.getSalary(), row, 7);
                model.setValueAt(employeeDto.getBirthDate(), row, 8);
                model.setValueAt(employeeDto.getHiringDate(), row, 9);
                employeeService.updateEmployee(employee, employeeDto);
                FrameView.printEmployeeInfo(employee, "Employee " + employee.getId() + " success update");
                frame.setVisible(false);
            } else {
                showUpdateMessage();
            }
        };
    }

    private ActionListener getRemoveListener(EmployeeService employeeService, JTable table) {
        return e -> {
            int row = table.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            long id = Long.parseLong(table.getValueAt(row, 0).toString());
            Employee employee = employeeService.getEmployee(id);
            employeeService.removeEmployee(employee);
            model.removeRow(row);
            FrameView.printDepartmentInfo(null, "Employee " + id + " success delete");
        };
    }

    protected ActionListener getAllEmployeeByDepartment(EmployeeService employeeService, EmployeeController
            employeeController, DepartmentService departmentService) {
        return e -> {
            JFrame jFrame = createFrame(250, 220, "Choose department");
            JPanel panel = new JPanel();
            jFrame.setVisible(true);
            jFrame.add(panel);
            createLabel("Department", panel);
            JComboBox<Department> departmentJComboBox = getDepartmentJComboBox(departmentService);
            departmentJComboBox.setPreferredSize(dimension);
            panel.add(departmentJComboBox);
            JButton findButton = getButton("Find", panel);
            findButton.addActionListener(e1 -> {
                Department department = (Department) departmentJComboBox.getSelectedItem();
                List<Employee> allEml = employeeService.getEmployeeList();
                List<Employee> allEmployeeByDepartment = new ArrayList<>();
                for (Employee em : allEml) {
                    if (em.getDepartment().equals(department)) {
                        allEmployeeByDepartment.add(em);
                    }
                }
                JLabel jLabel = new JLabel();
                panel.add(jLabel);
                createFrame(employeeService, allEmployeeByDepartment, employeeController, departmentService);
                jFrame.setVisible(false);
            });
        };
    }

    public void createFrame(EmployeeService employeeService, List<Employee> employees, EmployeeController
            employeeController, DepartmentService departmentService) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            String[][] data = getDeepArrayFromList(employees);
            createGUI(data, employeeService, employeeController, departmentService);
        });
    }

    private String[][] getDeepArrayFromList(List<Employee> list) {
        String[][] strings = new String[list.size()][13];
        for (int i = 0; i < strings.length; i++) {
            strings[i][0] = String.valueOf(list.get(i).getId());
            strings[i][1] = list.get(i).getName();
            strings[i][2] = list.get(i).getLastName();
            strings[i][3] = list.get(i).getDepartment().getName();
            strings[i][4] = list.get(i).getPhone();
            strings[i][5] = list.get(i).getEmail();
            strings[i][6] = list.get(i).getAddress();
            strings[i][7] = String.valueOf(list.get(i).getSalary());
            strings[i][8] = String.valueOf(list.get(i).getBirthDate());
            strings[i][9] = String.valueOf(list.get(i).getHiringDate());
            strings[i][10] = String.valueOf(list.get(i).getCreateDate());
            strings[i][11] = String.valueOf(list.get(i).getUpdateDate());
        }
        return strings;
    }


    private void showUpdateMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("You have to change input value");
        if (!FIRST_NAME_CHECK) {
            builder.append("\nnot correct name");
        }
        if (!LAST_NAME_CHECK) {
            builder.append("\nnot correct last name");
        }
        if (!PHONE_CHECK) {
            builder.append("\nnot correct phone");
        }
        if (!EMAIL_CHECK) {
            builder.append("\nnot correct email");
        }
        if (!ADDRESS_CHECK) {
            builder.append("\nnot correct address");
        }
        if (!SALARY_CHECK) {
            builder.append("\nnot salary");
        }
        if (!BIRTHDAY_CHECK) {
            builder.append("\nnot birthday");
        }
        if (!HIRING_DATE_CHECK) {
            builder.append("\nnot hiring day");
        }
        JOptionPane.showMessageDialog(null, builder.toString(), "ERROR", JOptionPane.ERROR_MESSAGE, null);
        builder.delete(0, builder.length()).append("You have to change input value");
    }
}
