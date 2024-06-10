package com.cigma.Ebank.security.services;

import com.cigma.Ebank.security.entities.AppRole;
import com.cigma.Ebank.security.entities.AppUser;
import com.cigma.Ebank.security.repositories.AppRoleRepository;
import com.cigma.Ebank.security.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // annotation service
@Slf4j //annotation lombok permet de donner un attribut qui s'appel log qui permet de logger
@Transactional //a la fin =>commit
@AllArgsConstructor
@NoArgsConstructor
public class CompteServiceImpl implements CompteService {
    @Lazy
    private AppUserRepository appUserRepository;
    @Lazy
    private AppRoleRepository appRoleRepository;
    @Lazy
    private PasswordEncoder passwordEncoder;



    /*@Override
    public AppUser addNewUser(String username, String password, String verifyPassword) {
        String pw = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(pw));
        return appUserRepository.save(appUser);
    }*/

    @Override
    public AppUser addNewUser(String username, String password, String verifyPassword) {
        if (!password.equals(verifyPassword)) throw new  RuntimeException("Mot de passe ne correspond pas");
        String hashedPWD = passwordEncoder.encode(password); // hasher le mdp
        AppUser appUser = new AppUser();
//        appUser.setId(UUID.randomUUID().toString()); //pour generer un id
        //UUID => genere des chaines de caractere aleatoire qui depend de la date systeme
        appUser.setUsername(username);
        appUser.setPassword(hashedPWD);
        AppUser savedAppUser = appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public void deleteUser(Long id) {
        appUserRepository.deleteById(id);
    }

  /*  @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }*/

    @Override
    public AppRole addNewRole(String roleName, String description) {
        AppRole appRole = appRoleRepository.findAppRoleByRoleName(roleName); // verifie si le role exist deja
        if (appRole != null) throw new RuntimeException("Role "+ roleName+" exist deja");
        appRole = new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
        AppRole savedAppRole = appRoleRepository.save(appRole);
        return savedAppRole;
    }

    /*@Override
    public void addRoleToUser(String username, String rolename) {
        AppUser appUser = appUserRepository.findAppUserByUsername(username);
        AppRole appRole = appRoleRepository.findAppRoleByRoleName(rolename);
        appUser.getRoles().add(appRole);
    }*/

    @Override // affecter le role a l'utilisateur
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findAppUserByUsername(username); // charger l'user
        if (appUser == null) throw new RuntimeException("User not found");
        AppRole appRole = appRoleRepository.findAppRoleByRoleName(roleName); // charger le role
        if (appRole == null) throw new RuntimeException("Role not found");
        appUser.getRoles().add(appRole);// ajouter le role dans la collection des roles de appUser
        appUserRepository.save(appUser);// n'est pas necessaire

    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findAppUserByUsername(username);
    }

    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser update(AppUser appUser) {
        String pw = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(pw));
        return appUserRepository.save(appUser);
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {

    }


}