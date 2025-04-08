package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    public void createUsersTable() {
        // Создать строку запроса SQL
        String sql = "CREATE TABLE userstable\n" +
                "(" +
                "id SERIAL PRIMARY KEY,\n" +
                "name CHARACTER VARYING(30),\n" +
                "lastname CHARACTER VARYING(30),\n" +
                "age SMALLSERIAL,\n" +
                "email CHARACTER VARYING(30)\n" +
                " );";
        // объявить транзакцию и присвоить ей значение null
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // начать транзакцию
            transaction = session.beginTransaction();
            // создать запрос
            session.createNativeQuery(sql, User.class);
            // исполнить транзакцию
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                // отменить действие запросов
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS userstable";

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createNativeQuery(sql, User.class).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.persist(new User(name,lastName,age));

            transaction.commit();

            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.remove(session.get(User.class, id));

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM userstable";

        List<User> allUsers = null;
        try (Session session = sessionFactory.openSession()) {
            return session.createNativeQuery(sql, User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM userstable";

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createNativeQuery(sql, User.class).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
