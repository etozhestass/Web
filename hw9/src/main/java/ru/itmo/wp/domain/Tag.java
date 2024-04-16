package ru.itmo.wp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(indexes = @Index(columnList = "name", unique = true))
public class Tag {
    @Id
    @GeneratedValue
    private long id;


    @NotBlank
    private String name;

    public Tag() {};

    public Tag(String tag) {
        this.name = tag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
