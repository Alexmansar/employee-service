package org.alexmansar.view;

import org.alexmansar.model.Department;
import org.alexmansar.model.Employee;
import org.alexmansar.model.dto.DepartmentDto;
import org.alexmansar.model.dto.EmployeeDto;
import org.alexmansar.service.DepartmentService;
import org.alexmansar.service.EmployeeService;
import org.alexmansar.utils.RegEx;
import org.alexmansar.utils.StringUtil;

import javax.swing.*;
import java.awt.*;
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


    public void createEmployeeUpdateFrame(DepartmentService departmentService, EmployeeService employeeService, Employee employee) {
        JFrame frame = createFrame(240, 750, "Update employee");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        frame.add(panel);
        panel.setAutoscrolls(true);

        createLabel("First name", panel);
        JLabel firstNameCheckLabel = createCheckLabel(panel);
        JTextField firstNameField = createTextField(employee.getName(), panel);
        if (firstNameField.getText().equals(employee.getName())) {
            FIRST_NAME_CHECK = true;
        }

        firstNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                FIRST_NAME_CHECK = RegEx.checkName(firstNameField, firstNameCheckLabel);
            }
        });

        createLabel("Last name", panel);
        JLabel lastNameCheckLabel = createCheckLabel(panel);
        JTextField lastNameField = createTextField(employee.getLastName(), panel);
        if (lastNameField.getText().equals(employee.getLastName())) {
            LAST_NAME_CHECK = true;
        }
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
        departmentJComboBox.setSelectedItem(employee.getDepartment());
        panel.add(departmentJComboBox);


        createLabel("Phone: ", panel);
        JLabel checkPhoneLabel = createCheckLabel(panel);
        JTextField phoneField = createTextField(employee.getPhone(), panel);
        addMouseListener(phoneField, "Enter phone", "+380");
        if (phoneField.getText().equals(employee.getPhone())) {
            PHONE_CHECK = true;
        }
        phoneField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                PHONE_CHECK = RegEx.checkPhone(phoneField, checkPhoneLabel);
            }
        });
        createLabel("Email: ", panel);
        JLabel checkEmailLabel = createCheckLabel(panel);
        JTextField emailField = createTextField(employee.getEmail(), panel);
        addMouseListener(emailField, "Enter email", "@");
        if (emailField.getText().equals(employee.getEmail())) {
            EMAIL_CHECK = true;
        }
        emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                EMAIL_CHECK = RegEx.checkEmail(emailField, checkEmailLabel);
            }
        });

        createLabel("Address: ", panel);
        JLabel checkAddressLabel = createCheckLabel(panel);
        JTextField addressField = createTextField(employee.getAddress(), panel);
        addMouseListener(addressField, "Enter address", "");
        if (addressField.getText().equals(employee.getAddress())) {
            ADDRESS_CHECK = true;
        }
        addressField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                ADDRESS_CHECK = RegEx.checkAddress(addressField, checkAddressLabel);
            }
        });


        createLabel("Salary: ", panel);
        JLabel salaryLabel = createCheckLabel(panel);
        JTextField salaryField = createTextField(String.valueOf(employee.getSalary()), panel);
        addMouseListener(salaryField, "Enter salary", "");
        if (salaryField.getText().equals(String.valueOf(employee.getSalary()))) {
            SALARY_CHECK = true;
        }
        salaryField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                SALARY_CHECK = RegEx.checkSalary(salaryField, salaryLabel);
            }
        });


        createLabel("Birthday: ", panel);
        JLabel birthdayLabel = createCheckLabel(panel);
        JTextField birthdayField = createTextField(String.valueOf(employee.getBirthDate()), panel);
        addMouseListener(birthdayField, "birthday", "");
        if (birthdayField.getText().equals(String.valueOf(employee.getBirthDate()))) {
            BIRTHDAY_CHECK = true;
        }
        birthdayField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                BIRTHDAY_CHECK = RegEx.checkBirthday(birthdayField, birthdayLabel);
            }
        });

        createLabel("Hiring day: ", panel);
        JLabel hiringLabel = createCheckLabel(panel);
        JTextField hiringField = createTextField(String.valueOf(employee.getHiringDate()), panel);
        addMouseListener(hiringField, "hiring", "");
        if (hiringField.getText().equals(String.valueOf(employee.getHiringDate()))) {
            HIRING_DATE_CHECK = true;
        }
        hiringField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                HIRING_DATE_CHECK = RegEx.checkHiringDate(hiringField, hiringLabel, birthdayField);
            }
        });

        JButton update = getButton("UPDATE", panel);
        printChecksEmp();
        JButton clearButton = getButton("CLEAR", panel);
        update.addActionListener(e -> {
            if (FIRST_NAME_CHECK && LAST_NAME_CHECK && PHONE_CHECK && EMAIL_CHECK && ADDRESS_CHECK && SALARY_CHECK && BIRTHDAY_CHECK && HIRING_DATE_CHECK) {
                EmployeeDto employeeDto;
                fillEmployeeFields(firstNameField, lastNameField, departmentJComboBox, phoneField, emailField, addressField, salaryField, birthdayField, hiringField);
                printChecksEmp();
                employeeDto = new EmployeeDto(FIRST_NAME, LAST_NAME, DEPARTMENT, PHONE, EMAIL, ADDRESS, SALARY, BIRTHDAY, HIRING_DATE);
                employeeService.updateEmployee(employee, employeeDto);
                FrameView.printEmployeeInfo(employee, "New employee success create and added to DB");
            } else {
                showCheckMessage();
            }
        });
        clearEmployeeMouseListener(clearButton, firstNameField, lastNameField,
                phoneField, emailField, addressField, salaryField, birthdayField, hiringField);
        frame.revalidate();
    }
}