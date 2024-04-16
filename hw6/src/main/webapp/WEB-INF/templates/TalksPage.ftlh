<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#-- @ftlvariable name="targetUserId" type="java.lang.Long" -->
<#-- @ftlvariable name="targetUsers" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="userService" type="ru.itmo.wp.model.service.UserService" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Send message</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="sendTalk"/>
                <div class="field">
                    <div class="name">
                        <label for="">User</label>
                    </div>
                    <div class="value">
                        <#if targetUsers??>
                            <select id="targetUserId" name="targetUserId"/>
                                <#list targetUsers as targetUser>
                                    <#if targetUser.id != user.id>
                                        <option value=${targetUser.id}>${targetUser.login}</option>
                                    </#if>
                                </#list>
                            </select>
                        </#if>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text" rows="5" cols="20" value="${text!}"/></textarea>
                    </div>
                </div>
                <#if error??>
                    <div class="error">
                        ${error!}
                    </div>
                </#if>
                <div class="button-field">
                    <input type="submit" value="Send">
                </div>
            </form>
        </div>
    </div>
    <p></p>
    <div class="datatable">
        <#if talks??>
            <div class="caption">Talk</div>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>From whom</th>
                    <th>To whom</th>
                    <th>Message</th>
                    <th>Created</th>
                </tr>
                </thead>
                <tbody>
                <#list talks as talk>
                    <tr>
                        <td>${talk.id}</td>
                        <td>${userService.find(talk.sourceUserId).login}</td>
                        <td>${userService.find(talk.targetUserId).login}</td>
                        <td>${talk.text}</td>
                        <td>${talk.creationTime}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            There is no talks still(
        </#if>
    </div>
</@c.page>
