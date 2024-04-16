<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>


<@c.page>
    <#if handle??>
        <#assign u = c.findBy(users, "handle", handle)!/>
    <#else>
        <#assign u = c.findBy(users, "id", user_id)!/>
    </#if>
    <#assign u_index = c.findIndex(users, u)/>

    <#if u?has_content>
        <#assign posts_number = 0>
        <#list posts as p>
            <#if p.user_id == u.id>
                <#assign posts_number = posts_number + 1>
            </#if>
        </#list>

        <div style="border: 1px rgb(185, 185, 185) solid; border-radius: 8px; display: flex; align-items: center">
            <div style="text-align: left; padding-left: 20px">
                <u style="color: darkslateblue; font-size: xx-large">${u.name}</u>
                <ul>
                    <li>id: ${u.id}</li>
                    <li>handle: ${u.handle}</li>
                    <li>name: ${u.name}</li>
                    <li>posts: ${posts_number} <a href="/posts?user_id=${u.id}">All posts</a></li>
                </ul>
            </div>
            <img src="../../img/user_image.jpg" height="120px" style="margin-left: auto; padding-right: 1px" alt="user">
        </div>
        <div style="margin-top: 20px; font-size: xx-large">
            <#if (u_index > 0)>
                <a href="?handle=${users[u_index - 1].handle}" style="text-decoration: none; color: blue">&larr;</a>
            <#else>
                <a style="color: grey"> &larr;</a>
            </#if>
            <#if u_index < users?size - 1>
                <a href="?handle=${users[u_index + 1].handle}" style="text-decoration: none; color: blue">&rarr;</a>
            <#else>
                <a style="color: grey"> &rarr;</a>
            </#if>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
