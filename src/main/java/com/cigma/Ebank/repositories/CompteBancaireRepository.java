package com.cigma.Ebank.repositories;

import com.cigma.Ebank.entities.CompteBancaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, Long> {
    Optional<CompteBancaire> findByRib(String rib);
    boolean existsByRib(String rib);

    Optional<CompteBancaire> findById(Long accountId);
}