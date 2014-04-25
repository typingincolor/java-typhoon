package com.laterooms.typhoon.entity;

import javax.persistence.*;

@Entity
@Table(name = "SCRIPT")
public class Script {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "_id")
    private Integer id;

    @Column(name = "script", nullable = false)
    private String script;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
