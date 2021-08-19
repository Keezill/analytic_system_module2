package dao;

import entity.Message;
import entity.Subscriber;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    public void saveMessage(Message message) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateMessageById(Message message, int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "UPDATE Message set text = :messageText "
                    + "WHERE id = :messageId";
            Query query = session.createQuery(hql);
            query.setParameter("messageText", message.getText());
            query.setParameter("messageId", id);
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Message getMessageByText(String text) {
        Transaction transaction = null;
        Message message = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Message M WHERE M.text like :messageText";
            Query query = session.createQuery(hql);
            query.setParameter("messageText", "%" + text + "%");
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                message = (Message) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return message;
    }

    public List<Object> getTop5MessageUsers() {
        List<Object> messages = new ArrayList<>();
        Transaction transaction;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String sql = "SELECT subscriber_id FROM Message GROUP BY subscriber_id ORDER BY count(text) desc";
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            sqlQuery.setMaxResults(5);
            List queryResultList = sqlQuery.getResultList();
            for (int i = 0; i < queryResultList.size(); i++) {
                String hql = "FROM Subscriber WHERE id = :subscriberId";
                Query query = session.createQuery(hql);
                query.setParameter("subscriberId", queryResultList.get(i));
                Object singleSubscriber = query.getSingleResult();
                messages.add(i, singleSubscriber);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }


    public List<Message> getMessages() {
        List<Message> messages = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            messages = session.createQuery("from Message", Message.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public List<Message> getMessagesByText(String text) {
        List<Message> messages = null;
        Transaction transaction;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Message WHERE text like concat('%',:messageText,'%')";
            Query query = session.createQuery(hql);
            query.setParameter("messageText", text);
            List results = query.getResultList();
            messages = new ArrayList<>(results);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public void deleteMessageById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Message message = session.get(Message.class, id);
            if (message != null) {
                String hql = "DELETE FROM Message " + "WHERE id = :messageId";
                Query query = session.createQuery(hql);
                query.setParameter("messageId", id);
                int result = query.executeUpdate();
                System.out.println("Rows affected: " + result);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteMessages() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Message";
            Query query = session.createQuery(hql);
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
