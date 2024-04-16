package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class Page {
    private final UserService userService = new UserService();
    private HttpServletRequest request;

    private void action(HttpServletRequest request, Map<String, Object> view) {}
    public void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        putUser(view);
        putMessage(view);
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
        view.put("userCount", userService.findCount());
    }

    public void putUser(Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", user);
        }
    }

    public void putMessage(Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            System.out.println(message);
            request.getSession().removeAttribute("message");
        }
    }

    public void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }

    public void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    public User getUser() {
        return (User) request.getSession().getAttribute("user");
    }
}
