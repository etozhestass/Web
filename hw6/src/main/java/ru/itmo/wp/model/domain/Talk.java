package ru.itmo.wp.model.domain;

import java.io.Serializable;
import java.util.Date;

public class Talk extends Common implements Serializable {
    private long sourceUserId;
    private long targetUserId;
    private String text;

    public long getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
