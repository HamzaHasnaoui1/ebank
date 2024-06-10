package com.cigma.Ebank.repositories;

import com.cigma.Ebank.entities.AgentGuichet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentGuichetRepository extends JpaRepository<AgentGuichet, Long> {
    Optional<AgentGuichet> findByUsername(String username);

    boolean existsByUsername(String username);
}