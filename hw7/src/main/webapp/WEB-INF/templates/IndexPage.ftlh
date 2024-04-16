<#import "commons.ftlh" as c>

<@c.page>
    Welcome on Codeforces!
    <div class="articles">
        <template>
            <article>
                <div class="title"></div>
                <div class="information"></div>
                <div class="text"></div>
            </article>
        </template>
        <div class="noData">
            <p>No data</p>
        </div>
    </div>
    <script>
        $(function () {
            const $noData = $(".articles .noData");
            $noData.find("p").text("Loading...")

            $.ajax({
                dataType: "json",
                data: {
                    action: "findAll",
                },
                success: function (response) {
                    response["articles"].forEach(function (article) {
                        let userId = article["userId"];
                        $.ajax({
                            dataType: "json",
                            data: {
                                action: "findUser",
                                userId: userId
                            },
                            success: function (response) {
                                let name = response["user"].login;
                                $noData.hide();
                                const $article = $($(".articles template").prop("content")).clone();
                                $article.find(".title").text(article["title"]);
                                $article.find(".information").text("By " + name + ", " + article["creationTime"]);
                                $article.find(".text").text(article["text"]);
                                $(".articles").append($article);
                            },
                            type: "POST"
                        });
                    });
                },
                type: "POST"
            });
        })
    </script>
</@c.page>
