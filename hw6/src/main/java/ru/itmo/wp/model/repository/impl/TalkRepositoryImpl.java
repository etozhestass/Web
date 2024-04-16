package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.TalkRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TalkRepositoryImpl extends BasicRepositoryImpl implements TalkRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void save(Talk talk) {
        Object[] fields = {talk.getSourceUserId(), talk.getTargetUserId(), talk.getText()};
        super.save(talk, "INSERT INTO Talk (sourceUserId, targetUserId, text, creationTime) VALUES (?, ?, ?, NOW())",
                fields);
    }

    protected Talk toCommon(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    talk.setId(resultSet.getLong(i));
                    break;
                case "sourceUserId":
                    talk.setSourceUserId(resultSet.getLong(i));
                    break;
                case "targetUserId":
                    talk.setTargetUserId(resultSet.getLong(i));
                    break;
                case "text":
                    talk.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    talk.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return talk;
    }

    @Override
    public String getTableName() {
        return "Talk";
    }

    @Override
    public List<Talk> findTalksByUserId(long userId) {
        List<Talk> talks = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Talk WHERE sourceUserId=? OR targetUserId=? ORDER BY creationTime")) {
                statement.setString(1, String.valueOf(userId));
                statement.setString(2, String.valueOf(userId));
                try (ResultSet resultSet = statement.executeQuery()) {
                    Talk talk;
                    while ((talk = toCommon(statement.getMetaData(), resultSet)) != null) {
                        talks.add(talk);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Talk.", e);
        }
        return talks;
    }
}
