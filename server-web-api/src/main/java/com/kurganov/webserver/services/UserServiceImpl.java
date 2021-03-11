package com.kurganov.webserver.services;

import com.kurganov.serverdb.entities.Role;
import com.kurganov.serverdb.entities.User;
import com.kurganov.serverdb.repositories.RoleRepository;
import com.kurganov.serverdb.repositories.UserRepository;
import com.kurganov.webserver.controllers.dto.SystemUserDTO;
import com.kurganov.webserver.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoderWeb;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoderWeb) {
        this.passwordEncoderWeb = passwordEncoderWeb;
    }

//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }

    @Override
    @Transactional
    public User findByUserName(String username) {
        return userRepository.findOneByUserName(username);
    }

    @Override
    @Transactional
    public boolean save(SystemUserDTO systemUserDTO) {
        User user = new User();

        if (findByUserName(systemUserDTO.getUserName()) != null) {
            return false;
        }

        user.setUserName(systemUserDTO.getUserName());
        user.setPassword(passwordEncoderWeb.encode(systemUserDTO.getPassword()));
        user.setFirstName(systemUserDTO.getFirstName());
        user.setLastName(systemUserDTO.getLastName());
        user.setEmail(systemUserDTO.getEmail());
        user.setPhone(systemUserDTO.getPhone());

        user.setRoles(Arrays.asList(roleRepository.findOneByName("ROLE_EMPLOYEE")));

        userRepository.save(user);
        return true;
    }

    @Override
    public boolean saveUser(User user) {
        user.setPassword(passwordEncoderWeb.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findOneByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public Page<User> getUsersWithPagingAndFiltering(int pageNumber, int pageSize, Specification<User> userSpecification) {
        return userRepository.findAll(userSpecification, PageRequest.of(pageNumber, pageSize));

    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
       userRepository.deleteById(id);
    }
}
