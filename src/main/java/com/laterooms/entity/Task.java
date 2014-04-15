package com.laterooms.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TASK")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "_id")
    private Integer id;

    @Column(name = "at", nullable = false)
    private Date at;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "status_code")
    private Integer status_code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAt() {
        return at;
    }

    public void setAt(Date at) {
        this.at = at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }
}
