package dao;

import entity.InternetAccess;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class InternetAccessDao {
    public void saveInternetAccess(InternetAccess megabytes) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(megabytes);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateInternetAccessById(InternetAccess megabytes, int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "UPDATE InternetAccess set megabytes = :megabytes "
                    + "WHERE id = :internetAccessId";
            Query query = session.createQuery(hql);
            query.setParameter("megabytes", megabytes.getMegabytes());
            query.setParameter("internetAccessId", id);
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

    public InternetAccess getInternetAccessById(int id) {
        Transaction transaction = null;
        InternetAccess megabytes = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM InternetAccess I WHERE I.id = :internetAccessId";
            Query query = session.createQuery(hql);
            query.setParameter("internetAccessId", id);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                megabytes = (InternetAccess) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return megabytes;
    }


    public List<InternetAccess> getInternetAccessInfo() {
        List<InternetAccess> megabytes = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            megabytes = session.createQuery("from InternetAccess", InternetAccess.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return megabytes;
    }

    public List<InternetAccess> getTop5InternetUsers() {
        List<InternetAccess> megabytes = null;
        Transaction transaction;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM InternetAccess i ORDER BY i.megabytes DESC";
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

    public void deleteInternetAccessInfoById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            InternetAccess megabytes = session.get(InternetAccess.class, id);
            if (megabytes != null) {
                String hql = "DELETE FROM InternetAccess " + "WHERE id = :internetAccessId";
                Query query = session.createQuery(hql);
                query.setParameter("internetAccessId", id);
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

    public void deleteInternetAccessInfo() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM InternetAccess";
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
