<#import "./common.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">UsersList</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>User</th>
                <th>Handle</th>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <tr>
                    <td>${u.id}</td>
                    <td>
                        <#if user?? && user.id == u.id>*</#if>
                        <@c.userlink u false/>
                    </td>
                    <td>${u.handle}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>

