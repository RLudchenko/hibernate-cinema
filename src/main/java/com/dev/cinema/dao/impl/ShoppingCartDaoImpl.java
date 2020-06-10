package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.ShoppingCartDao;
import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(shoppingCart);
            transaction.commit();
            LOGGER.info(shoppingCart + " was added to DB");
            return shoppingCart;
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
    public ShoppingCart getByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            var cb = session.getCriteriaBuilder();
            CriteriaQuery<ShoppingCart> shoppingCartCriteriaQuery
                    = cb.createQuery(ShoppingCart.class);
            Root<ShoppingCart> shoppingCartRoot
                    = shoppingCartCriteriaQuery.from(ShoppingCart.class);
            shoppingCartRoot.fetch("tickets", JoinType.LEFT);
            var predicateByUser = cb.equal(shoppingCartRoot.get("user"), user.getId());
            shoppingCartCriteriaQuery.where(predicateByUser);
            return session.createQuery(shoppingCartCriteriaQuery).uniqueResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get shoppingCart by user " + user, e);
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(shoppingCart);
            transaction.commit();
            LOGGER.info(shoppingCart + " was updated");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("There was an error updating ", e);
        } finally {
            session.close();
        }
    }
}
