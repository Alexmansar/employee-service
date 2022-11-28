package org.alexmansar.view;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.alexmansar.model.Department;
import org.alexmansar.service.DepartmentService;
import org.alexmansar.utils.RegEx;
import org.alexmansar.utils.StringUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractFrame extends JFrame {
    boolean NAME_DEPARTMENT_CHECK;
    boolean FIRST_NAME_CHECK;
    boolean LAST_NAME_CHECK;
    boolean EMAIL_CHECK;
    boolean PHONE_CHECK;
    boolean ADDRESS_CHECK;
    boolean HIRING_DATE_CHECK;
    boolean SALARY_CHECK;
    boolean BIRTHDAY_CHECK;
    String FIRST_NAME;
    String LAST_NAME;
    Department DEPARTMENT;
    String EMAIL;
    String PHONE;
    String ADDRESS;
    int SALARY;
    LocalDateTime HIRING_DATE;
    LocalDateTime BIRTHDAY;

    Dimension dimension = new Dimension(220, 20);

    public AbstractFrame() throws HeadlessException {
        super();
    }

    protected void createLabel(String name, JPanel panel) {
        JLabel label = new JLabel(name);
        label.setPreferredSize(dimension);
        panel.add(label);
    }

    protected JLabel createCheckLabel(JPanel panel) {
        JLabel label = new JLabel();
        label.setVisible(false);
        label.setPreferredSize(dimension);
        panel.add(label);
        return label;
    }

    protected JTextField createTextField(String text, JPanel panel) {
        JTextField textField = new JTextField(text, 22);
        textField.setPreferredSize(dimension);
        panel.add(textField);
        textField.setFont(new Font(null, Font.BOLD, 12));
        textField.setVisible(true);
        return textField;
    }

    protected String createText(JTextField field) {
        return field.getText();
    }

    protected LocalDateTime createDate(JTextField field) {
        return RegEx.getLocalDateTime(field);
    }


    protected JFrame createFrame(int width, int height, String frameName) {
        JFrame frame = new JFrame(frameName);
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(width, height));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        frame.setBounds(dimension.width / 2 - width / 2, dimension.height / 2 - height / 2, width, height);
        frame.setResizable(false);
        frame.revalidate();
        return frame;
    }

    protected JPanel getPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        frame.add(panel);
        return panel;
    }

    protected void addMouseListener(JTextField textField, String text, String defaultValue) {
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(text)) {
                    textField.setText(defaultValue);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().equals(defaultValue)) {
                    textField.setText(text);
                }
            }
        });
    }

    protected JButton getButton(String buttonName, JPanel panel) {
        JButton button = new JButton(buttonName);
        panel.add(button);
        button.setSize(100, 50);
        return button;
    }

    protected void getButton(String buttonName, JPanel panel, ActionListener listener) {
        JButton button = new JButton(buttonName);
        panel.add(button);
        button.setSize(100, 50);
        button.addActionListener(listener);
    }

    protected void clearDepartmentMouseListener(JButton button, JTextField departmentNameField) {
        button.addActionListener(e -> departmentNameField.setText(""));
    }

    protected void clearEmployeeMouseListener(JButton button,
                                              JTextField firstNameField,
                                              JTextField lastNameField,
                                              JTextField phoneOwnerField,
                                              JTextField emailField,
                                              JTextField addressField,
                                              JTextField salaryField,
                                              JTextField birthdayField,
                                              JTextField hiringField) {
        button.addActionListener(e -> clearEmployee(firstNameField, lastNameField, phoneOwnerField, emailField, addressField, salaryField, birthdayField, hiringField));
    }

    protected static void clearEmployee(JTextField firstNameField, JTextField lastNameField, JTextField phoneOwnerField, JTextField emailField, JTextField addressField, JTextField salaryField, JTextField birthdayField, JTextField hiringField) {
        firstNameField.setText("");
        lastNameField.setText("");
        phoneOwnerField.setText("+380");
        emailField.setText("@");
        addressField.setText("");
        salaryField.setText("");
        birthdayField.setText("");
        hiringField.setText("");
    }

    protected void showCheckMessage() {
        JOptionPane.showMessageDialog(null, "You have to change input value", "ERROR", JOptionPane.ERROR_MESSAGE, null);
    }

    @NotNull
    protected static JComboBox<Department> getDepartmentJComboBox(DepartmentService departmentService, JTable table, int index) {
        Department[] departments = departmentService.getDepartmentList().stream().toList().toArray(new Department[0]);
        JComboBox<Department> departmentJComboBox = new JComboBox<>(departments);
        TableColumn departmentColumn = table.getColumnModel().getColumn(index);
        departmentColumn.setCellEditor(new DefaultCellEditor(departmentJComboBox));
        table.getColumnModel().getColumn(index).setPreferredWidth(150);
        return departmentJComboBox;
    }

    @NotNull
    protected static JComboBox<Department> getDepartmentJComboBox(DepartmentService departmentService) {
        Department[] departments = departmentService.getDepartmentList().stream().toList().toArray(new Department[0]);
        return new JComboBox<>(departments);
    }

    protected void fillEmployeeFields(JTextField firstNameField, JTextField lastNameField, JComboBox<Department> departmentJComboBox, JTextField phoneField, JTextField emailField, JTextField addressField, JTextField salaryField, JTextField birthdayField, JTextField hiringField) {
        FIRST_NAME = StringUtil.firstUpperCase(createText(firstNameField));
        LAST_NAME = StringUtil.firstUpperCase(createText(lastNameField));
        DEPARTMENT = (Department) departmentJComboBox.getSelectedItem();
        EMAIL = StringUtil.firstUpperCase(createText(emailField));
        PHONE = StringUtil.firstUpperCase(createText(phoneField));
        ADDRESS = StringUtil.firstUpperCase(createText(addressField));
        SALARY = Integer.parseInt((createText(salaryField)));
        HIRING_DATE = createDate(hiringField);
        BIRTHDAY = createDate(birthdayField);
    }

    protected void printChecksEmp() {
        System.out.println(FIRST_NAME_CHECK);
        System.out.println(LAST_NAME_CHECK);
        System.out.println(PHONE_CHECK);
        System.out.println(EMAIL_CHECK);
        System.out.println(ADDRESS_CHECK);
        System.out.println(SALARY_CHECK);
        System.out.println(BIRTHDAY_CHECK);
        System.out.println(HIRING_DATE_CHECK);
    }
}