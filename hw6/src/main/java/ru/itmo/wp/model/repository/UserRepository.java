package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Common;
import ru.itmo.wp.model.domain.User;

import java.util.List;

public interface UserRepository {
    Common find(long id);

    User findByLoginOrEmail(String login);

    User findByLoginOrEmailAndPasswordSha(String login, String passwordSha);

    List<User> findAll();

    void save(User user, String passwordSha);

    int findCount();
}
