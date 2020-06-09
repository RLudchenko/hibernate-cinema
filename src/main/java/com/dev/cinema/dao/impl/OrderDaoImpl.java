package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.OrderDao;
import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.User;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements OrderDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Order add(Order order) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            LOGGER.info(order + " was added to DB");
            return order;
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
    public List<Order> getOrderHistory(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query<Order> query = session.createQuery("SELECT DISTINCT o "
                    + "FROM Order o "
                    + "LEFT JOIN FETCH o.tickets Ticket "
                    + "WHERE o.user =: user", Order.class);
            query.setParameter("user", user);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("There was an error getting order by user ", e);
        }
    }
}
