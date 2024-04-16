package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class ArticlePage {
    private final ArticleService articleService = new ArticleService();
    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("message", "You should log in first!");
            throw new RedirectException("../index");
        }
        // No operation
    }

    private void createArticle(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            String title = request.getParameter("title");
            String text = request.getParameter("text");
            articleService.validateArticle(title, text, user.getId());
            text = text.trim();
            title = title.trim();
            articleService.save(articleService.makeArticle(user.getId(), title, text));
            request.getSession().setAttribute("message", "Article was created successfully!");
        }

        throw new RedirectException("../index");
    }
}
