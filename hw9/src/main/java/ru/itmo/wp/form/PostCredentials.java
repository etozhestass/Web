package ru.itmo.wp.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostCredentials {
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 60)
    private String tags;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 60)
    private String title;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 65000)
    private String text;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
