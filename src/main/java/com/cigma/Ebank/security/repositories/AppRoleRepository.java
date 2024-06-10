package com.cigma.Ebank.security.repositories;

import com.cigma.Ebank.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository <AppRole,Long>{

    AppRole findAppRoleByRoleName(String rolename);
}
