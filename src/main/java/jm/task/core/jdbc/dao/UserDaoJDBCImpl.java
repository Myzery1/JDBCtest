package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    private final Connection connection = Util.getConnection();

    private static final String sql = "CREATE TABLE IF NOT EXISTS `users` (\n" +
            "        `ID` BIGINT NOT NULL AUTO_INCREMENT,\n" +
            "        `NAME` CHAR(45) NOT NULL,\n" +
            "        `LASTNAME` CHAR(45) NOT NULL,\n" +
            "        `AGE` TINYINT NOT NULL,\n" +
            "        PRIMARY KEY (`id`))\n" +
            "        ENGINE = InnoDB\n" +
            "        DEFAULT CHARACTER SET = utf8;";

    public void createUsersTable() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate(sql);
            System.out.println("таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate("drop table if exists users;");
            System.out.println("таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement = connection.
                prepareStatement("insert into users (NAME, LASTNAME, AGE) values (?, ?, ?);")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("пользователь добавлен: " + name + " " + lastName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try (PreparedStatement preparedStatement = connection.
                prepareStatement("delete from users where id = ?;")) {
            preparedStatement.setLong(1, id);
            System.out.println("пользователь удален, ID " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.
                prepareStatement("select * from users;")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {

        try (PreparedStatement preparedStatement = connection.
                prepareStatement("delete from users;")) {
            preparedStatement.executeUpdate();
            System.out.println("таблица юзеров очищена!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



