<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#assign p = c.findBy(posts, "id", post_id)/>
    <#if p?has_content>
        <@c.article title=p.title name=p.user_id text=p.text full=true/>
    <#else>
        No such post
    </#if>
</@c.page>
