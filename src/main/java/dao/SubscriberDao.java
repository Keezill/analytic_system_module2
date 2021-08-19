package dao;

import entity.Subscriber;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

public class SubscriberDao {
    public void saveSubscriber(Subscriber subscriber) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(subscriber);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateSubscriberById(Subscriber subscriber, int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "UPDATE Subscriber set name = :name, surname = :surname age = :age, gender = :gender "
                    + "WHERE id = :subscriberId";
            Query query = session.createQuery(hql);
            query.setParameter("name", subscriber.getName());
            query.setParameter("surname", subscriber.getSurname());
            query.setParameter("age", subscriber.getAge());
            query.setParameter("gender", subscriber.getGender());
            query.setParameter("subscriberId", id);
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

    public Subscriber getSubscriberById(int id) {
        Transaction transaction = null;
        Subscriber subscriber = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Subscriber S WHERE S.id = :subscriberId";
            Query query = session.createQuery(hql);
            query.setParameter("subscriberId", id);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                subscriber = (Subscriber) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return subscriber;
    }


    public List<Subscriber> getSubscribers() {
        List<Subscriber> subscribers = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            subscribers = session.createQuery("from Subscriber", Subscriber.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscribers;
    }

    public void deleteSubscriberById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Subscriber subscriber = session.get(Subscriber.class, id);
            if (subscriber != null) {
                String hql = "DELETE FROM Subscriber " + "WHERE id = :subscriberId";
                Query query = session.createQuery(hql);
                query.setParameter("subscriberId", id);
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

    public void deleteSubscribers() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Subscriber";
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

    public String getMostPopularService() {
        Transaction transaction;
        String mostPopularService = "";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hqlCalls = "SELECT count(distinct subscriber_id) FROM Calls";
            Query query = session.createQuery(hqlCalls);
            Object callsUniqueUsersAmount = query.getSingleResult();

            String hqlMessage = "SELECT count(distinct subscriber_id) FROM Message";
            Query query2 = session.createQuery(hqlMessage);
            Object messageUniqueUsersAmount = query2.getSingleResult();

            String hqlInternet = "SELECT count(distinct subscriber_id) FROM InternetAccess";
            Query query3 = session.createQuery(hqlInternet);
            Object internetUniqueUsersAmount = query3.getSingleResult();
            transaction.commit();

            int callsUserAmount = Integer.parseInt(String.valueOf(callsUniqueUsersAmount));
            int messageUserAmount = Integer.parseInt(String.valueOf(messageUniqueUsersAmount));
            int internetUserAmount = Integer.parseInt(String.valueOf(internetUniqueUsersAmount));

            if (callsUserAmount > messageUserAmount &&
                    callsUserAmount > internetUserAmount) {
                mostPopularService = "Calls";
            } else if (messageUserAmount > callsUserAmount &&
                    messageUserAmount > internetUserAmount) {
                mostPopularService = "Message";
            } else if (internetUserAmount > messageUserAmount &&
                    internetUserAmount > callsUserAmount) {
                mostPopularService = "Internet";
            } else {
                return "Most popular service can't be counted(maybe they have same amount of users). Try again later!";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mostPopularService + " is the most popular service!";
    }
}
