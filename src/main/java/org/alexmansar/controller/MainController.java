package org.alexmansar.controller;

import org.alexmansar.repository.DepartmentRepository;
import org.alexmansar.repository.EmployeeRepository;
import org.alexmansar.service.impl.DepartmentServiceImpl;
import org.alexmansar.service.impl.EmployeeServiceImpl;
import org.alexmansar.utils.HibernateUtil;
import org.alexmansar.view.frame.*;
import org.hibernate.Session;

public class MainController {
    FrameController frameController = new FrameController(
            new DepartmentController(new DepartmentServiceImpl(new DepartmentRepository()), new DepartmentTable()),
            new EmployeeController(new EmployeeServiceImpl(new EmployeeRepository()), new EmployeeTable()),
            new MainFrame(new UpdateFrame(),new AddFrame(),new DepartmentTable(),new EmployeeTable()));

    public void run() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        frameController.run();
        session.close();
    }
}
