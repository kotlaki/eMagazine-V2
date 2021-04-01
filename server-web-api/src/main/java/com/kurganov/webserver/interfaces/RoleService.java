package com.kurganov.webserver.interfaces;

import com.kurganov.serverdb.entities.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

}
