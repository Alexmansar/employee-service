package org.alexmansar.repository;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.alexmansar.model.Employee;
import org.alexmansar.model.dto.EmployeeDto;

import java.util.List;
import java.util.Objects;

@Slf4j
public class EmployeeRepository extends AbstractRepository {

    public List<Employee> getEmployeeList() {
        session.get(Employee.class, 1L);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        criteriaQuery.select(employeeRoot);
        Query query = session.createQuery(criteriaQuery);
        @SuppressWarnings("unchecked")
        List<Employee> employeeList = query.getResultList();
        return employeeList;
    }


    public Employee getEmployee(long id) {
        try {
            transaction = session.beginTransaction();
            Employee employee = session.find(Employee.class, id);
            transaction.commit();
            return employee;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public void addEmployee(Employee employee) {
        addModel(employee);
    }

    public void updateEmployee(Employee employee, EmployeeDto employeeDto) {
        try {
            transaction = session.beginTransaction();
            employee.setName(employeeDto.getName());
            employee.setLastName(employeeDto.getLastName());
            employee.setDepartment(employeeDto.getDepartment());
            employee.setPhone(employeeDto.getPhone());
            employee.setEmail(employeeDto.getEmail());
            employee.setAddress(employeeDto.getAddress());
            employee.setSalary(employeeDto.getSalary());
            employee.setBirthDate(employeeDto.getBirthDate());
            employee.setHiringDate(employeeDto.getHiringDate());
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            Objects.requireNonNull(transaction).rollback();
        }
    }

    public void removeEmployee(Employee employee) {
        removeModel(employee);
    }
}