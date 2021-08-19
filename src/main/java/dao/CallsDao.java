package dao;

import entity.Calls;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class CallsDao {
    public void saveCalls(Calls calls) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(calls);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateDeviceById(Calls calls, int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "UPDATE Calls set callLength = :callLength "
                    + "WHERE id = :callId";
            Query query = session.createQuery(hql);
            query.setParameter("callLength", calls.getCallLength());
            query.setParameter("callId", id);
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

    public Calls getCallById(int id) {
        Transaction transaction = null;
        Calls calls = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Calls C WHERE C.id = :callId";
            Query query = session.createQuery(hql);
            query.setParameter("callId", id);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                calls = (Calls) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return calls;
    }


    public List<Calls> getCall() {
        List<Calls> callList = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            callList = session.createQuery("from Calls", Calls.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return callList;
    }

    public List<Calls> getTop5CallUsers() {
        List<Calls> megabytes = null;
        Transaction transaction;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Calls c ORDER BY c.callLength DESC";
            Query query = session.createQuery(hql);
            query.setMaxResults(5);
            List results = query.getResultList();
            megabytes = new ArrayList<>(results);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return megabytes;
    }

    public void deleteCallById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Calls calls = session.get(Calls.class, id);
            if (calls != null) {
                String hql = "DELETE FROM Calls " + "WHERE id = :callId";
                Query query = session.createQuery(hql);
                query.setParameter("callId", id);
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

    public void deleteCalls() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Calls";
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