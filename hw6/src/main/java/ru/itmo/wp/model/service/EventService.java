package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.Type;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;

public class EventService {
    private final EventRepository eventRepository = new EventRepositoryImpl();

    public Event makeEvent(long userId, Type type) {
        Event event = new Event();
        event.setUserId(userId);
        event.setType(type);
        return event;
    }

    public void register(Event event) {
        eventRepository.save(event);
    }
}
