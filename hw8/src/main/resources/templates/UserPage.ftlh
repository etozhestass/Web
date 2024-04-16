<#import "commons.ftlh" as c>

<@c.page>
    <#if user??>
        <div class="caption">User Information</div>
        <div class="user-info">
            <p><strong>User ID:</strong> ${user.id}</p>
            <p><strong>Login:</strong> ${user.login}</p>
            <p><strong>Creation Time:</strong> ${user.creationTime}</p>
        </div>
    <#else >
        <p>No such user</p>
    </#if>
</@c.page>