package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS `myfirstdb`.`users` (\n" +
                "  `id` INTEGER NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastName` VARCHAR(45) NOT NULL,\n" +
                "  `age` INT NOT NULL,\n" +
                "    PRIMARY KEY (`id`))\n" +
                "    ENGINE = InnoDB\n" +
                "    DEFAULT CHARACTER SET = utf8;";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCreateTable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void dropUsersTable() {
        String sqlDropTable = "DROP TABLE IF EXISTS`myfirstdb`.`users`;";

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(sqlDropTable);
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sqlSaveUser = "INSERT INTO `myfirstdb`.`users` (`name`, `lastName`, `age`) VALUES (?, ?, ?);";
        connection.setAutoCommit(false);

        try (PreparedStatement ps = connection.prepareStatement(sqlSaveUser)) {


            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();

            System.out.println("User с именем – " + name + " добавлен в базу данных");
            connection.commit();

        } catch (SQLException t) {
            t.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void removeUserById(long id) throws SQLException {
        String sqlRemoveUserById = "DELETE FROM `myfirstdb`.`users` WHERE (`id` = ?);";
        connection.setAutoCommit(false);

        try (PreparedStatement ps = connection.prepareStatement(sqlRemoveUserById)) {

            ps.setInt(1, (int) id);
            ps.executeUpdate();
            connection.commit();

        } catch (SQLException t) {
            t.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        String sqlGetAllUsers = "SELECT * FROM myfirstdb.users;";
        List<User> userList = new ArrayList<>();
        connection.setAutoCommit(false);

        try (Statement st = connection.createStatement();
             ResultSet resultSet = st.executeQuery(sqlGetAllUsers)) {

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("LastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
            connection.commit();

        } catch (SQLException th) {
            th.printStackTrace();
            connection.rollback();

        } finally {
            connection.setAutoCommit(true);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() throws SQLException {
        String sqlCleanUsersTable = "TRUNCATE `myfirstdb`.`users`;";
        connection.setAutoCommit(false);

        try (Statement st = connection.createStatement()) {
            st.executeUpdate(sqlCleanUsersTable);

        } catch (SQLException th) {
            th.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
