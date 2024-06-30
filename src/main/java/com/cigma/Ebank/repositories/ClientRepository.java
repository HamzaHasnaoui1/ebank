package com.cigma.Ebank.repositories;

import com.cigma.Ebank.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client, Long> {

   List<Client> findByNomContains(String keyword);

    Optional<Client> findByUsername(String username);

    boolean existsByUsername(String username);

   Optional<Client> findByIdentityRef(String identityRef);

    @Query(" select c from Client c where c.prenom like :kw")
    List<Client> searchClient(@Param("kw") String keyword);

    Client getClientByUsername(String username);

    Page<Client> findAll(Pageable pageable);

   @Query("select c from Client c where c.prenom like :kw")
    Page<Client> searchByName(@Param("kw") String keyword, Pageable pageable);
}