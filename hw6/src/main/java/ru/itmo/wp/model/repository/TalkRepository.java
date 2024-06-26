package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Common;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;

import java.util.List;

public interface TalkRepository {
    void save(Talk talk);

    List<Talk> findTalksByUserId(long userId);

    Common find(long id);
}
