package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserService {
    private static final String PASSWORD_SALT = "1174f9d7bc21e00e9a5fd0a783a44c9a9f73413c";

    private final UserRepository userRepository = new UserRepositoryImpl();

    public void validateRegistration(User user, String password, String passwordConfirmation) throws ValidationException {
        if (Strings.isNullOrEmpty(user.getLogin())) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8 letters");
        }
        if (userRepository.findByLoginOrEmail(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }
        if (Strings.isNullOrEmpty(user.getEmail())) {
            throw new ValidationException("Email is required");
        }
        if (user.getEmail().indexOf('@') == -1 || user.getEmail().indexOf('@') != user.getEmail().lastIndexOf('@')) {
            throw new ValidationException("Email must contain only one @ symbol");
        }
        if (userRepository.findByLoginOrEmail(user.getEmail()) != null) {
            throw new ValidationException("Email is already in use");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4 characters");
        }
        if (password.length() > 64) {
            throw new ValidationException("Password can't be longer than 64 characters");
        }
        if (!password.equals(passwordConfirmation)) {
            throw new ValidationException("Passwords must be equal");
        }
    }

    public void register(User user, String password) {
        userRepository.save(user, getPasswordSha(password));
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashBytes((PASSWORD_SALT + password).getBytes(StandardCharsets.UTF_8)).toString();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public int findCount() {return userRepository.findCount();}

    public void validateEnter(String loginOrEmail, String password) throws ValidationException {
        User user = userRepository.findByLoginOrEmailAndPasswordSha(loginOrEmail, getPasswordSha(password));
        if (user == null) {
            throw new ValidationException("Invalid login or password");
        }
    }

    public User findByLoginAndPassword(String loginOrEmail, String password) {
        return userRepository.findByLoginOrEmailAndPasswordSha(loginOrEmail, getPasswordSha(password));
    }

    public User find(long id) {
        return (User) userRepository.find(id);
    }
}
