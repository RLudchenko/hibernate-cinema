package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.UserDao;
import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.model.User;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User add(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            LOGGER.info(user + " was added to DB");
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("There was an error adding ", e);
        } finally {
            session.close();
        }
    }

    @Override
    public User getUserById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder
                    .createQuery(User.class);
            Root<User> sessionRoot = criteriaQuery.from(User.class);
            Predicate predicate = criteriaBuilder
                    .equal(sessionRoot.get("id"), id);
            sessionRoot.fetch("tickets", JoinType.LEFT);
            criteriaQuery.select(sessionRoot).where(predicate);
            return session.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "An Error Occurred While Retrieving User by Id! " + id, e);
        }
    }

    @Override
    public Optional<User> getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session
                    .createQuery("FROM User u "
                            + "LEFT JOIN FETCH u.roles Role "
                            + " WHERE u.email =: email", User.class);
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "An Error Occurred While Retrieving Email! " + email, e);
        }
    }
}
