package com.cigma.Ebank.repositories;

import com.cigma.Ebank.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByIdentityRef(String identityRef);

    Optional<Client> findByUsername(String username);

    boolean existsByUsername(String username);

@Query(" select c from Client c where c.prenom like :kw")
    List<Client> searchClient(@Param("kw") String keyword);
}