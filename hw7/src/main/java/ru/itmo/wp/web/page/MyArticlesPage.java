package ru.itmo.wp.web.page;

import org.checkerframework.checker.units.qual.A;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/** @noinspection unused*/
public class MyArticlesPage {
    private final ArticleService articleService = new ArticleService();
    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("myArticles", articleService.findArticlesByUserId(user.getId()));
        }
    }

    private void changeType(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        long articleId = Long.parseLong(request.getParameter("articleId"));
        if (user != null && user.getId() == articleService.find(articleId).getUserId()) {
            boolean newValue = Objects.equals(request.getParameter("curr_value"), "Hide");
            articleService.changeType(articleId, newValue);
        } else {
            view.put("message", "Only author can change article type");
        }
    }


}
