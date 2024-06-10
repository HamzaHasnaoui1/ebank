package com.cigma.Ebank.security.repositories;

import com.cigma.Ebank.security.entities.AppRole;
import com.cigma.Ebank.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findAppUserByUsername(String username);
    AppUser save(AppUser user);


}
