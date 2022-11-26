package org.alexmansar.repository;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.alexmansar.model.AbstractModel;
import org.alexmansar.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Objects;

@Slf4j
public abstract class AbstractRepository {
    Transaction transaction = null;

    protected AbstractRepository() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @Getter
    protected final Session session;

    protected void removeModel(AbstractModel model) {
        try {
            transaction = session.beginTransaction();
            session.remove(model);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            Objects.requireNonNull(transaction).rollback();
        }
    }

    protected void addModel(AbstractModel model) {
        try {
            transaction = session.beginTransaction();
            session.persist(model);
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            Objects.requireNonNull(transaction).rollback();
        }
    }
}