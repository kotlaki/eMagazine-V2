package com.kurganov.webserver.controllers.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RoleDTO implements Serializable {

    private Long id;

    @NotEmpty
    private String name;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
