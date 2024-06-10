package com.cigma.Ebank.repositories;

import com.cigma.Ebank.entities.CompteBancaireOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CompteBancaireOperationRepository extends JpaRepository<CompteBancaireOperation, Long> {
    //List<CompteBancaireOperation> findByCompteBancaire_RibAndCreatedAtBetween(String rib, Date from, Date to);
List<CompteBancaireOperation> findByCompteBancaire_id (Long compteBancaireId);
Page<CompteBancaireOperation> findByCompteBancaire_id (Long compteBancaireId, Pageable pageable);

}