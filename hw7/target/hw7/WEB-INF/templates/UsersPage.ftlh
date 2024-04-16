<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if user?? && user.admin>
                    <th>Admin</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user-id"></td>
                    <td class="user_login"></td>
                    <#if user?? && user.admin>
                        <td class="user_admin_link">
                            <a href="#" class="admin-link">enable</a>
                        </td>
                    </#if>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            $.ajax({
                dataType: "json",
                data: {
                    action: "findAll"
                },
                success: function(response) {
                    response["users"].forEach(function(user) {
                        $noData.hide();
                        const $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user-id").text(user["id"]);
                        $tr.find(".user_login").text(user["login"]);
                        $tr.find("tr").attr("data-userId", user["id"]);
                        const $admin_link = $tr.find(".user_admin_link").find(".admin-link");
                        if (user?.admin) {
                            if (user.admin) {
                                $admin_link.text("disable");
                            }
                        }
                        $admin_link.click(function () {
                            const userId = $(this).closest("tr").attr("data-userId");
                            const action = user.admin;
                            $.ajax({
                                dataType: "json",
                                data: {
                                    action: "changeAdminStatus",
                                    userId: userId,
                                    curr_state: action
                                },
                                success: function (response) {
                                    if (response["changed"]) {
                                        $admin_link.text(action ? "enable" : "disable");
                                    } else if (response["message"]) {
                                        notify(response["message"]);
                                    }
                                },
                                type: "POST"
                            })
                        });
                        $(".users tbody").append($tr);
                    });
                    $(".users tr").click(function() {
                        $.ajax({
                            dataType: "json",
                            data: {
                                action: "findUser",
                                userId: $(this).attr("data-userId")
                            },
                            success: function (response) {
                                notify(response["user"].creationTime);
                            },
                            type: "POST"
                        })
                    });
                },
                type: "POST"
            });
        })
    </script>
</@c.page>
