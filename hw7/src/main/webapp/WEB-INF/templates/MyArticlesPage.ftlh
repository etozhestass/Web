<#-- @ftlvariable name="myArticles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>State</th>
            </tr>
            </thead>
            <#if myArticles??>
                <tbody>
                <#list myArticles as article>
                    <tr>
                        <td>${article.id}</td>
                        <td>${article.title}</td>
                        <td>
                            <div class="button-field">
                                <input type="submit" name="button" value="<#if article.hidden>Show<#else>Hide</#if>" data-article-id="${article.id}">
                            </div>
                        </td>
                    </tr>
                </#list>
                </tbody>
            <#else >
                <tr>
                    <td colspan="3">
                        No Articles
                    </td>
                </tr>
            </#if>
        </table>
    </div>
    <script>
        $(function () {
            $(".users input[name='button']").click(function () {
                const button = $(this);
                const articleId = button.data('article-id');
                const curr_value = button.val();
                notify(curr_value);
                    $.ajax({
                    type: "POST",
                    dataType: "json",
                    data: {
                        action: "changeType",
                        curr_value: curr_value,
                        articleId: articleId,
                    },
                    success: function (response) {
                        button.val() === "Show" ? button.val("Hide") : button.val("Show");
                    }
                })
            });
        })
    </script>
</@c.page>