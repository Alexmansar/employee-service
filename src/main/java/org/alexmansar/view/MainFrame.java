package org.alexmansar.view;

import lombok.extern.slf4j.Slf4j;
import org.alexmansar.controller.DepartmentController;
import org.alexmansar.controller.EmployeeController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static javax.swing.UIManager.setLookAndFeel;

@Slf4j
public class MainFrame extends AbstractFrame {
    UpdateFrame updateFrame;
    AddFrame addFrame;
    DepartmentTable departmentTable;
    EmployeeTable employeeTable;

    public MainFrame(UpdateFrame updateFrame, AddFrame addFrame, DepartmentTable departmentTable, EmployeeTable employeeTable) throws HeadlessException {
        this.updateFrame = updateFrame;
        this.addFrame = addFrame;
        this.departmentTable = departmentTable;
        this.employeeTable = employeeTable;
    }


    JMenuBar jMenuBar = new JMenuBar();
    JPopupMenu popupMenu = new JPopupMenu();

    public void createFrame( DepartmentController departmentController, EmployeeController employeeController) {
        JFrame window = createWindow();
        JPanel panel = getPanel(window);
        JMenu main = getJMenu("Models");
        JMenu department = getJMenu("Department");
        JMenu employee = getJMenu("Employee");
        main.add(department);
        main.add(employee);
        main.addSeparator();
        ActionListener exitListener = e -> exit();
        getMenuItem("Exit", exitListener, "ctrl E", main);
        JMenu style = getJMenu("Style");
        JMenu edit = getJMenu("Edit");
        JMenu info = getJMenu("Info");
        panel.setBackground(Color.LIGHT_GRAY);

        ActionListener getDepartmentList = e -> departmentController.getDepartmentList();
        ActionListener addDepartment = e -> departmentController.addDepartment(addFrame);
        ActionListener getDepartmentById = e -> departmentController.getDepartment();
        ActionListener removeDepartment = e -> departmentController.removeDepartment();
        ActionListener updateDepartment = e -> departmentController.updateDepartment(updateFrame);


        ActionListener getEmployeeList = e -> employeeController.getEmployeeList(departmentController.getDepartmentService());
        ActionListener addEmployee = e -> employeeController.addEmployee(addFrame, departmentController.getDepartmentService());
        ActionListener getEmployeeById = e -> employeeController.getEmployee();
        ActionListener removeEmployee = e -> employeeController.removeEmployee();
        ActionListener updateEmployee = e -> employeeController.updateEmployee(updateFrame, departmentController.getDepartmentService());

        ActionListener aboutListener = e -> about();


        getStyleMenu(style);

        getMenuItem("Add", addDepartment, "ctrl A", department);
        getMenuItem("Remove", removeDepartment, "ctrl R", department);
        getMenuItem("Update", updateDepartment, "ctrl U", department);
        department.addSeparator();
        getMenuItem("Print all", getDepartmentList, "ctrl P", department);
        getMenuItem("Print by id", getDepartmentById, "ctrl I", department);


        getMenuItem("Add", addEmployee, "shift A", employee);
        getMenuItem("Remove", removeEmployee, "shift R", employee);
        getMenuItem("Update", updateEmployee, "shift U", employee);
        employee.addSeparator();
        getMenuItem("Print all", getEmployeeList, "shift P", employee);
        getMenuItem("Print by id", getEmployeeById, "shift I", employee);

        window.setJMenuBar(jMenuBar);

        getMenuItem("Copy", null, "ctrl C", edit);
        getMenuItem("Paste", null, "ctrl P", edit);
        getMenuItem("Cut", null, "ctrl X", edit);

        info.addSeparator();
        getMenuItem("About", aboutListener, null, info);

        getButton("Employee", panel, getEmployeeList);
        getButton("Department", panel, getDepartmentList);

        getMenuItemPopup("Add department", addDepartment, "ctrl A");
        getMenuItemPopup("Remove department", removeDepartment, "ctrl U");
        getMenuItemPopup("Update department", updateDepartment, "ctrl U");
        popupMenu.addSeparator();
        getMenuItemPopup("Print all department", getDepartmentList, "ctrl P");
        getMenuItemPopup("Print by id department", getDepartmentById, "ctrl I");
        popupMenu.addSeparator();
        getMenuItemPopup("Add", addEmployee, "shift A");
        getMenuItemPopup("Remove", removeEmployee, "shift R");
        getMenuItemPopup("Update", updateEmployee, "shift U");
        popupMenu.addSeparator();
        getMenuItemPopup("Print all", getEmployeeList, "shift P");
        getMenuItemPopup("Print by id", getEmployeeById, "shift I");
        getMenuItemPopup("Exit", exitListener, "ctrl E");
        popupMenu.addSeparator();
        panel.setComponentPopupMenu(popupMenu);

        window.revalidate();
    }

    private JMenu getJMenu(String name) {
        JMenu file = new JMenu(name);
        jMenuBar.add(file);
        return file;
    }

    private void getStyleMenu(JMenu style) {
        String[] styleName = getLookAndFeelInfo();
        String[] styleClassName = getLookAndFeelName();
        for (int i = 0; i < styleClassName.length; i++) {
            ActionListener listener = getStyleListener(styleClassName[i]);
            getMenuItem(styleName[i], listener, null, style);
        }
    }

    private ActionListener getStyleListener(String className) {
        return e -> {
            try {
                setLookAndFeel(className);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException exception) {
                throw new RuntimeException(exception);
            }
        };
    }


    private void getMenuItem(String Add, ActionListener addListener, String shortCard, JMenu jMenu) {
        JMenuItem jMenuItem = new JMenuItem(Add);
        jMenuItem.addActionListener(addListener);
        jMenuItem.setAccelerator(KeyStroke.getKeyStroke(shortCard));
        jMenu.add(jMenuItem);
    }

    private void getMenuItemPopup(String Add, ActionListener addListener, String shortCard) {
        JMenuItem jMenuItem = new JMenuItem(Add);
        jMenuItem.addActionListener(addListener);
        jMenuItem.setAccelerator(KeyStroke.getKeyStroke(shortCard));
        popupMenu.add(jMenuItem);
    }

    private JFrame createWindow() {
        JFrame window = super.createFrame(500, 200, "Employee service");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        InputStream inputStream = MainFrame.class.getClassLoader().getResourceAsStream("employee.png");
        Image image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(inputStream));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        window.setIconImage(image);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        window.setResizable(false);
        window.revalidate();
        return window;
    }


    private String[] getLookAndFeelInfo() {
        UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
        String[] strings = new String[lookAndFeels.length];
        for (int i = 0; i < lookAndFeels.length; i++) {
            strings[i] = lookAndFeels[i].getName();
        }
        return strings;
    }

    private String[] getLookAndFeelName() {
        UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
        String[] strings = new String[lookAndFeels.length];
        for (int i = 0; i < lookAndFeels.length; i++) {
            strings[i] = lookAndFeels[i].getClassName();
        }
        return strings;
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
