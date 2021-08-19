package dao;

import entity.Device;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

public class DeviceDao {
    public void saveDevice(Device device) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(device);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateDeviceById(Device device, int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "UPDATE Device set deviceName = :deviceName "
                    + "WHERE id = :deviceId";
            Query query = session.createQuery(hql);
            query.setParameter("deviceName", device.getDeviceName());
            query.setParameter("deviceId", id);
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

    public Device getDeviceById(int id) {
        Transaction transaction = null;
        Device device = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Device D WHERE D.id = :deviceId";
            Query query = session.createQuery(hql);
            query.setParameter("deviceId", id);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                device = (Device) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return device;
    }


    public List<Device> getDevices() {
        List<Device> devices = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            devices = session.createQuery("from Device", Device.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return devices;
    }

    public void deleteDeviceById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Device device = session.get(Device.class, id);
            if (device != null) {
                String hql = "DELETE FROM Device " + "WHERE id = :deviceId";
                Query query = session.createQuery(hql);
                query.setParameter("deviceId", id);
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

    public void deleteDevices() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Device";
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

    public String getMostPopularDevice() {
        Transaction transaction;
        String mostPopularDevice = "";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "SELECT deviceName FROM Device group by deviceName order by count(*) desc";
            Query query = session.createQuery(hql);
            List results = query.getResultList();
            mostPopularDevice = String.valueOf(results.get(0));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mostPopularDevice + " is the most popular device";
    }
}
