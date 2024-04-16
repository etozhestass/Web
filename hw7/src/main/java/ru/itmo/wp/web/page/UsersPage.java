package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void changeAdminStatus(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && userService.isAdmin(user.getId())) {
            long userId = Long.parseLong(request.getParameter("userId"));
            boolean currState = Boolean.parseBoolean(request.getParameter("curr_state"));
            System.out.println(currState);
            userService.changeAdminStatus(userId, !currState);
            request.getSession().removeAttribute("user");
            request.getSession().setAttribute("user", userService.find(userId));
            view.put("changed", true);
        } else {
            view.put("message", "You do not have admin rights");
        }
    }
}
