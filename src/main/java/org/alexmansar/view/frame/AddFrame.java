package org.alexmansar.view.frame;

import org.alexmansar.model.Department;
import org.alexmansar.model.Employee;
import org.alexmansar.service.DepartmentService;
import org.alexmansar.service.EmployeeService;
import org.alexmansar.utils.StringUtil;
import org.alexmansar.utils.RegEx;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddFrame extends AbstractFrame {
    public void createAddDepartmentFrame(DepartmentService departmentService, JTable table) {
        JFrame frame = createFrame(240, 150, "Add new department");
        JPanel panel = getPanel(frame);
        createLabel("Department name", panel);
        JLabel departmentNameCheckLabel = createCheckLabel(panel);
        JTextField departmentNameField = createTextField("", panel);
        departmentNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                NAME_DEPARTMENT_CHECK = RegEx.checkName(departmentNameField, departmentNameCheckLabel);
            }
        });

        JButton addButton = getButton("NEW", panel);
        JButton clearButton = getButton("CLEAR", panel);
        addButton.addActionListener(e -> {
            if (NAME_DEPARTMENT_CHECK) {
                String name = StringUtil.firstUpperCase(createText(departmentNameField));
                Department department = new Department(name);
                departmentService.addDepartment(department);
                FrameView.printDepartmentInfo(department, "New department success create and added to DB");
                DefaultTableModel model;
                if (!(table == null)) {
                    model = (DefaultTableModel) table.getModel();
                    model.addRow(new Object[]{department.getId(), department.getName(), department.getCreateDate(), department.getUpdateDate()});
                }
                frame.setVisible(false);
            } else {
                showCheckMessage();
            }
        });
        clearDepartmentMouseListener(clearButton, departmentNameField);
    }


    public void createAddEmployeeFrame(EmployeeService employeeService, DepartmentService departmentService, JTable table) {
        JFrame frame = createFrame(240, 750, "Add new employee");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        frame.add(panel);
        panel.setAutoscrolls(true);

        createLabel("First name", panel);
        JLabel firstNameCheckLabel = createCheckLabel(panel);
        JTextField firstNameField = createTextField("", panel);
        firstNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                FIRST_NAME_CHECK = RegEx.checkName(firstNameField, firstNameCheckLabel);
            }
        });

        createLabel("Last name", panel);
        JLabel lastNameCheckLabel = createCheckLabel(panel);
        JTextField lastNameField = createTextField("", panel);
        lastNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                LAST_NAME_CHECK = RegEx.checkName(lastNameField, lastNameCheckLabel);
            }
        });

        createLabel("Department", panel);
        JComboBox<Department> departmentJComboBox = getDepartmentJComboBox(departmentService);
        departmentJComboBox.setPreferredSize(dimension);
        panel.add(departmentJComboBox);


        createLabel("Phone: ", panel);
        JLabel checkPhoneLabel = createCheckLabel(panel);
        JTextField phoneField = createTextField("+380", panel);
        addMouseListener(phoneField, "Enter phone", "+380");

        phoneField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                PHONE_CHECK = RegEx.checkPhone(phoneField, checkPhoneLabel);
            }
        });
        createLabel("Email: ", panel);
        JLabel checkEmailLabel = createCheckLabel(panel);
        JTextField emailField = createTextField("@", panel);
        addMouseListener(emailField, "Enter email", "@");

        emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                EMAIL_CHECK = RegEx.checkEmail(emailField, checkEmailLabel);
            }
        });

        createLabel("Address: ", panel);
        JLabel checkAddressLabel = createCheckLabel(panel);
        JTextField addressField = createTextField("", panel);
        addMouseListener(addressField, "Enter address", "");

        addressField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                ADDRESS_CHECK = RegEx.checkAddress(addressField, checkAddressLabel);
            }
        });


        createLabel("Salary: ", panel);
        JLabel salaryLabel = createCheckLabel(panel);
        JTextField salaryField = createTextField("", panel);
        addMouseListener(salaryField, "Enter salary", "");

        salaryField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                SALARY_CHECK = RegEx.checkSalary(salaryField, salaryLabel);
            }
        });


        createLabel("Birthday: ", panel);
        JLabel birthdayLabel = createCheckLabel(panel);
        JTextField birthdayField = createTextField("", panel);
        addMouseListener(birthdayField, "birthday", "");

        birthdayField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                BIRTHDAY_CHECK = RegEx.checkBirthday(birthdayField, birthdayLabel);
            }
        });

        createLabel("Hiring day: ", panel);
        JLabel hiringLabel = createCheckLabel(panel);
        JTextField hiringField = createTextField("", panel);
        addMouseListener(hiringField, "hiring", "");

        hiringField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                HIRING_DATE_CHECK = RegEx.checkHiringDate(hiringField, hiringLabel,birthdayField);
            }
        });


        JButton addButton = getButton("NEW", panel);
        JButton clearButton = getButton("CLEAR", panel);
        addButton.addActionListener(e -> {
            if (FIRST_NAME_CHECK && LAST_NAME_CHECK && PHONE_CHECK && EMAIL_CHECK && ADDRESS_CHECK && SALARY_CHECK && BIRTHDAY_CHECK && HIRING_DATE_CHECK) {


                FIRST_NAME = StringUtil.firstUpperCase(createText(firstNameField));
                LAST_NAME = StringUtil.firstUpperCase(createText(lastNameField));
                DEPARTMENT = (Department) departmentJComboBox.getSelectedItem();
                EMAIL = StringUtil.firstUpperCase(createText(emailField));
                PHONE = StringUtil.firstUpperCase(createText(phoneField));
                ADDRESS = StringUtil.firstUpperCase(createText(addressField));
                SALARY = Integer.parseInt(StringUtil.firstUpperCase(createText(salaryField)));
                HIRING_DATE = createDate(hiringField);
                BIRTHDAY = createDate(birthdayField);
                Employee employee = new Employee(FIRST_NAME, LAST_NAME, DEPARTMENT, PHONE, EMAIL, SALARY, ADDRESS, HIRING_DATE, BIRTHDAY);
                employeeService.addEmployee(employee);
                FrameView.printEmployeeInfo(employee, "New department success create and added to DB");
                DefaultTableModel model;
                if (!(table == null)) {
                    model = (DefaultTableModel) table.getModel();
                    model.addRow(new Object[]
                            {employee.getId(),
                            employee.getName(),
                            employee.getLastName(),
                            employee.getDepartment(),
                            employee.getPhone(),
                            employee.getEmail(),
                            employee.getAddress(),
                            employee.getSalary(),
                            employee.getBirthDate(),
                            employee.getHiringDate(),
                            employee.getCreateDate(),
                            employee.getUpdateDate()});
                }
                frame.setVisible(false);
                clearEmployee(firstNameField, lastNameField,
                        phoneField, emailField, addressField, salaryField, birthdayField, hiringField);
            } else {
                showCheckMessage();
            }
        });
        clearEmployeeMouseListener(clearButton, firstNameField, lastNameField,
                phoneField, emailField, addressField, salaryField, birthdayField, hiringField);
        frame.revalidate();
    }
}