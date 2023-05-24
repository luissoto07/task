package com.tcs.usaa.todotask.service;

import com.tcs.usaa.todotask.domain.Role;
import com.tcs.usaa.todotask.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);
    User getUser(String username);
    List<User> getUsers();
}
