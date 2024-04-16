<#-- @ftlvariable name="name" type="java.lang.String" -->
<#-- @ftlvariable name="uri" type="java.lang.String" -->
<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div clasmiddles="">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "index"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <a href="${item.href}">
                            <#if uri?? && item.href == uri>
                                <span style="border-bottom: 3px solid blue">${item.name}</span>
                            <#else>
                                ${item.name}
                            </#if>
                        </a>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts as p>
            <section>
                <div class="header">Post ${p.id}</div>
                <div class="body">
                    <@article title=p.title name=p.user_id text=p.text full=false/>
                </div>
                <div class="footer">
                    <a href="/post?post_id=${p.id}">View all</a>
                </div>
            </section>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly>
<#-- @ftlvariable name="user" type="ru.itmo.web.hw4.model.User" -->
    <a <#if !nameOnly >style="text-decoration: none; font-weight: bold; color: ${user.color} </#if>" href="/user?handle=${user.handle}">${user.name}</a>
</#macro>

<#macro article title name text full>
    <article>
        <div class="title">${title}</div>
        <div class="information">By user with id ${name}</div>
        <div class="body">
            <#if full>
                ${text}
            <#else>
                ${text?truncate(350, '...')}
            </#if>
        </div>
    </article>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
    <#return "">
</#function>

<#function findIndex items curr_item>
    <#list items as item>
        <#if item == curr_item>
            <#return item?index/>
        </#if>
    </#list>
    <#return -1>
</#function>
