package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Color;
import ru.itmo.web.hw4.model.Post;
import ru.itmo.web.hw4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", Color.BLUE),
            new User(6, "pashka", "Pavel Mavrin", Color.GREEN),
            new User(9, "geranazavr555", "Georgiy Nazarov", Color.RED),
            new User(11, "tourist", "Gennady Korotkevich", Color.GREEN)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Искусственный интеллект: будущее или угроза?",
            "Искусственный интеллект (ИИ) вызывает много дискуссий и размышлений в обществе."  +
            "Многие видят в нем потенциал для трансформации нашей жизни и прогресса в различных сферах, начиная от медицины и образования и заканчивая промышленностью и искусством. ИИ способен улучшить нашу эффективность, предоставив нам новые инструменты и возможности, которые ранее казались недостижимыми." +
            "Однако, среди общества существует и обеспокоенность относительно будущего ИИ. Некоторые видят в нем потенциальную угрозу для человечества, опасаясь автономных систем и роботов, которые могут принимать решения, влияющие на нашу жизнь без человеческого вмешательства. Вопросы этики, безопасности и контроля над ИИ становятся все более актуальными." +
            "Таким образом, дискуссия вокруг искусственного интеллекта продолжается, и важно найти баланс между его потенциалом для улучшения нашей жизни и обеспокоенностью по поводу его возможных рисков и угроз." , 1),
            new Post(2, "Путешествие вокруг света", "Моя недавняя поездка вокруг света была невероятным приключением. Я посетил множество стран и познакомился с разными культурами.", 1),
            new Post(3, "Изучение глубокого обучения", "Глубокое обучение - увлекательная тема. Все эти нейронные сети и алгоритмы машинного обучения могут сделать удивительные вещи.", 9),
            new Post(4, "Забытые города мира", "Мир полон забытых и покинутых городов. Исследование их истории и архитектуры - это настоящее приключение.", 11)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }

        for (Post post : POSTS) {
            data.put("post", post);
        }
    }
}
