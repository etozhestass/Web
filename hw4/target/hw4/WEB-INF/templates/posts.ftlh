<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>


<@c.page>
    <#assign u = c.findBy(users, "id", user_id)!/>
    <#assign u_index = c.findIndex(users, u)/>
    <#list posts as p>
        <#if p.user_id == u.id>
            <p><@c.article title=p.title name=p.user_id text=p.text full=true/></p>
        </#if>
    </#list>
</@c.page>