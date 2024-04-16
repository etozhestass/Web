<#import "commons.ftlh" as c>

<@c.page>
    <div class="article form-box">
        <div class="header">Post article</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="createArticle"/>
                <div class="field">
                    <div class="name">
                        <label for="">Title</label>
                    </div>
                    <div class="value">
                        <textarea id="title" name="title" rows="1" cols="30"></textarea>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text" rows="5" cols="30"></textarea>
                    </div>
                </div>
                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="Create">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".article form").submit(function () {
                const title = $(this).find("textarea[name='title']").val();
                const text = $(this).find("textarea[name='text']").val();
                const $error = $(this).find(".error");

                ajax({
                    data: {
                        action: "createArticle",
                        title: title,
                        text: text
                    },
                }, $error);

                return false;
            });
        })
    </script>
</@c.page>