package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Common;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.Type;
import ru.itmo.wp.model.repository.EventRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class EventRepositoryImpl extends BasicRepositoryImpl implements EventRepository {
    @Override
    public void save(Event event) {
        Object[] fields = {event.getUserId(), event.getType()};
        super.save(event, "INSERT INTO Event (userId, type, creationTime) VALUES (?, ?, NOW())",
                fields);
    }

    @Override
    protected Common toCommon(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    event.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    event.setUserId(resultSet.getLong(i));
                    break;
                case "type":
                    event.setType(Type.valueOf(resultSet.getString(i)));
                    break;
                case "creationTime":
                    event.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return event;
    }

    @Override
    public String getTableName() {
        return "Event";
    }
}
