package com.cigma.Ebank.security.services;

import com.cigma.Ebank.security.entities.AppRole;
import com.cigma.Ebank.security.entities.AppUser;

import java.util.List;

public interface CompteService {

    AppUser addNewUser(String username, String password, String verifyPassword);

    void deleteUser(Long id);

    AppRole addNewRole(String roleName, String description);

    void addRoleToUser(String username, String roleName);

    AppUser loadUserByUsername(String username);

    List<AppUser> listUsers();

    AppUser update(AppUser appUser);

    void removeRoleFromUser(String username, String roleName);
}