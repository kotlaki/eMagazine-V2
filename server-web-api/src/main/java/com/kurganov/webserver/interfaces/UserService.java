package com.kurganov.webserver.interfaces;


import com.kurganov.serverdb.entities.Role;
import com.kurganov.serverdb.entities.User;
import com.kurganov.webserver.controllers.dto.SystemUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User findByUserName(String username);

    boolean save(SystemUserDTO systemUserDTO);

    boolean saveUser(User user);

    List<User> findAll();

    UserDetails loadUserByUsername(String userName);

    Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles);

    public Page<User> getUsersWithPagingAndFiltering(int pageNumber, int pageSize,
                                                     Specification<User> userSpecification);


//    List<User> findAllUser();
//
        Optional<User> findById(Long id);

    void deleteById(Long id);
}
