package ru.itmo.wp.controller;

import jdk.jfr.Enabled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String updateStatus(@RequestParam Long id, @RequestParam String new_status, HttpSession httpSession) {
        User user = userService.findById(id);
        User currentUser = getUser(httpSession);
        if (user != null) {
            if (currentUser != null && currentUser.isDisabled()) {
                setMessage(httpSession, "You were disabled");
                return "IndexPage";
            }
            userService.updateStatus(id, new_status.equals("Disable"));
        }
        return "redirect:/users/all";
    }
}
