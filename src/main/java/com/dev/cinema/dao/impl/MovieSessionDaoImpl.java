package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.MovieSessionDao;
import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.model.MovieSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final Logger LOGGER = LogManager.getLogger(MovieSessionDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<MovieSession> findSession(Long movieId, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<MovieSession> query
                    = cb.createQuery(MovieSession.class);
            Root<MovieSession> root = query.from(MovieSession.class);
            Predicate idPredicate
                    = cb.equal(root.get("movie"), movieId);
            Predicate datePredicate = cb.between(
                    root.get("sessionTime"), date.atStartOfDay(), date.atTime(LocalTime.MAX));
            return session.createQuery(query.where(idPredicate, datePredicate)).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "There was an error retrieving available sessions", e);
        }
    }

    @Override
    public MovieSession getMovieSessionById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<MovieSession> criteriaQuery = criteriaBuilder
                    .createQuery(MovieSession.class);
            Root<MovieSession> sessionRoot = criteriaQuery.from(MovieSession.class);
            Predicate predicate = criteriaBuilder
                    .equal(sessionRoot.get("id"), id);
            criteriaQuery.select(sessionRoot).where(predicate);
            return session.createQuery(criteriaQuery).uniqueResult();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "An Error Occurred While Retrieving Movie Session by Id! " + id, e);
        }
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = sessionFactory.openSession();;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            LOGGER.info(movieSession + " was inserted to DB");
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("There was an error inserting "
                    + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
