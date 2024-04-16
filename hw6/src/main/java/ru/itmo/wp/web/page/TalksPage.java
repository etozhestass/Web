package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class TalksPage extends Page {
    private final TalkService talkService = new TalkService();
    private final UserService userService = new UserService();

    private void sendTalk(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = getUser();
        view.put("targetUsers", userService.findAll());
        view.put("userService", userService);
        view.put("talks", talkService.findTalksByUserId(user.getId()));
        String text = request.getParameter("text");
        System.out.println(text);
        long targetUserId = Long.parseLong(request.getParameter("targetUserId"));
        talkService.validateTalk(targetUserId, text);
        text = text.trim();
        talkService.register(talkService.makeTalk(user.getId(), targetUserId, text));
        request.getSession().setAttribute("message", "You have sent message successfully!");
        throw new RedirectException("/talks");
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("targetUsers", userService.findAll());
        User user = getUser();
        if (user != null) {
            try {
                setUser(user);
                view.put("userService", userService);
                view.put("talks", talkService.findTalksByUserId(user.getId()));
            } catch (Exception e) {
                // do nothing
            }
        } else {
            setMessage("You should log in to see talks");
            throw new RedirectException("../index");
        }
    }
}
