package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Common;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;

public abstract class BasicRepositoryImpl {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    protected abstract Common toCommon(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;
    public abstract String getTableName();

    public void save(Common common, String command, Object[] fields) {
        String DB = command.split("\\s")[2];
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    command,
                    Statement.RETURN_GENERATED_KEYS
            )) {
                for (int i = 0; i < fields.length; i++) {
                    statement.setString(i + 1, String.valueOf(fields[i]));
                }
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save " + DB + ".");
                } else {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        common.setId(generatedKeys.getLong(1));
                        common.setCreationTime(find(common.getId()).getCreationTime());
                    } else {
                        throw new RepositoryException("Can't save" + DB + "[no autogenerated fields].");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save" + DB + ".", e);
        }
    }

    public Common find(long id) {
        String command = "SELECT * FROM " + getTableName() + " WHERE id=?";
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(command)) {
                statement.setObject(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toCommon(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + getTableName() + ".", e);
        }
    }
}
