package org.alexmansar.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.alexmansar.repository.DepartmentRepository;
import org.alexmansar.repository.EmployeeRepository;
import org.alexmansar.service.impl.DepartmentServiceImpl;
import org.alexmansar.service.impl.EmployeeServiceImpl;
import org.alexmansar.utils.HibernateUtil;
import org.alexmansar.view.*;
import org.hibernate.Session;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class MainController {
    FrameController frameController = new FrameController(
            new DepartmentController(new DepartmentServiceImpl(new DepartmentRepository()), new DepartmentTable()),
            new EmployeeController(new EmployeeServiceImpl(new EmployeeRepository()), new EmployeeTable()),
            new MainFrame(new UpdateFrame(),new AddFrame(),new DepartmentTable(),new EmployeeTable()));

    public void run() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.info("App run, session start {}", session);
        frameController.run();
        session.close();
        log.info("App finish, session close {}", session);

    }
}
