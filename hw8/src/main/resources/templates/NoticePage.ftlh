<#-- @ftlvariable name="notice" type="ru.itmo.wp.domain.Notice" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="notice form-box">
        <div class="header">Add notice</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value=""/>
                <div class="field">
                    <div class="name">
                        <label for="content">Content</label>
                    </div>
                    <div class="value">
                        <textarea id="content" name="content" rows=5 cols="30"> ${notice.content!} </textarea>
                    </div>
                    <@c.error "notice.content"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </div>
</@c.page>
