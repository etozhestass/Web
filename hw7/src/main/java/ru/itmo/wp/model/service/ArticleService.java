package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

/**
 * @noinspection UnstableApiUsage
 */
public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validateArticle(String title, String text, long userId) throws ValidationException {
        if (articleRepository.findArticlesByUserId(userId) == null) {
            throw new ValidationException("User does not exist");
        }
        validateText(title, "Title", 50);
        validateText(text, "Text", 1000);
    }

    private void validateText(String text, String name, int length) throws ValidationException {
        if (Strings.isNullOrEmpty(text)) {
            throw new ValidationException(name + " is required");
        }
        text = text.trim();
        if (text.isEmpty()) {
            throw new ValidationException(name + " must contain not only spaces");
        }
        if (text.length() >= length) {
            throw new ValidationException(name + " is too long");
        }
    }

    public Article makeArticle(long userId, String title, String text) {
        Article article = new Article();
        article.setUserId(userId);
        article.setTitle(title);
        article.setText(text);
        article.setHidden(true);
        return article;
    }

    public void save(Article article) {
        articleRepository.save(article);
    }
    public Article find(long articleId) {return articleRepository.find(articleId);}

    public void changeType(long articleId, boolean newValue) {articleRepository.changeFieldHidden(articleId, newValue);}

    public List<Article> findArticlesByUserId(long userId) {
        return articleRepository.findArticlesByUserId(userId);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

}

