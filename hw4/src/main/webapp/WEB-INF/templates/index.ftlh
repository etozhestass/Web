<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as p>
        <p><@c.article title=p.title name=p.user_id text=p.text full=false/></p>
    </#list>
</@c.page>
