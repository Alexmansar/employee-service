package org.alexmansar.controller;

import lombok.extern.slf4j.Slf4j;
import org.alexmansar.repository.AbstractRepository;
import org.hibernate.Session;

@Slf4j
public abstract class AbstractController extends AbstractRepository {
    Session session = getSession();
}
