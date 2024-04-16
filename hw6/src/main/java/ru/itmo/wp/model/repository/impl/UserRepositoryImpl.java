package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Common;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SqlNoDataSourceInspection")
public class UserRepositoryImpl extends BasicRepositoryImpl implements UserRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public User findByLoginOrEmail(String login) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM User WHERE login=?")) {
                statement.setString(1, login);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toCommon(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM User WHERE login=? OR email=? AND passwordSha=?")) {
                statement.setString(1, loginOrEmail);
                statement.setString(2, loginOrEmail);
                statement.setString(3, passwordSha);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toCommon(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM User ORDER BY id DESC")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    User user;
                    while ((user = toCommon(statement.getMetaData(), resultSet)) != null) {
                        users.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
        return users;
    }
    
    @Override
    protected User toCommon(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return user;
    }

    @Override
    public String getTableName() {
        return "User";
    }

    @Override
    public void save(User user, String passwordSha) {
        Object[] fields = {user.getLogin(),user.getEmail(), passwordSha};
        super.save(user, "INSERT INTO User (login, email, passwordSha, creationTime) VALUES (?, ?, ?, NOW())",
                fields);
    }

    @Override
    public int findCount() {
        int usersNumber;
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM User")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    usersNumber = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
        return usersNumber;
    }
}
