package dao;

import entity.Tariff;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

public class TariffDao {
    public void saveTariff(Tariff tariff) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(tariff);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateTariffById(Tariff tariff, int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "UPDATE Tariff set currentTariff = :currentTariff "
                    + "WHERE id = :tariffId";
            Query query = session.createQuery(hql);
            query.setParameter("currentTariff", tariff.getCurrentTariff());
            query.setParameter("tariffId", id);
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

    public Tariff getTariffById(int id) {
        Transaction transaction = null;
        Tariff tariff = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Tariff T WHERE T.id = :tariffId";
            Query query = session.createQuery(hql);
            query.setParameter("tariffId", id);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                tariff = (Tariff) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return tariff;
    }


    public List<Tariff> getTariffs() {
        List<Tariff> tariffs = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tariffs = session.createQuery("from Tariff", Tariff.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tariffs;
    }

    public void deleteTariffById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Tariff tariff = session.get(Tariff.class, id);
            if (tariff != null) {
                String hql = "DELETE FROM Tariff " + "WHERE id = :tariffId";
                Query query = session.createQuery(hql);
                query.setParameter("tariffId", id);
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

    public void deleteTariffs() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Tariffs";
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