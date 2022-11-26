package org.alexmansar.repository;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.alexmansar.model.Department;
import org.alexmansar.model.dto.DepartmentDto;

import java.util.List;
import java.util.Objects;

@Slf4j
public class DepartmentRepository extends AbstractRepository {

    public List<Department> getDepartmentList() {
        session.get(Department.class, 1L);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Department> criteriaQuery = criteriaBuilder.createQuery(Department.class);
        Root<Department> departmentRoot = criteriaQuery.from(Department.class);
        criteriaQuery.select(departmentRoot);
        Query query = session.createQuery(criteriaQuery);
        //noinspection unchecked
        return (List<Department>) query.getResultList();
    }

    public Department getDepartment(long id) {
        try {
            transaction = session.beginTransaction();
            Department department = session.find(Department.class, id);
            transaction.commit();
            return department;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public void addDepartment(Department department) {
        addModel(department);
    }

    public void updateDepartment(Department department, DepartmentDto departmentDTO) {
        try {
            transaction = session.beginTransaction();
            department.setName(departmentDTO.getName());
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            Objects.requireNonNull(transaction).rollback();
        }
    }

    public void removeDepartment(Department department) {
        removeModel(department);
    }
}