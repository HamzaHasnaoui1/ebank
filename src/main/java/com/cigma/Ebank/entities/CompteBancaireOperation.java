package com.cigma.Ebank.entities;

import com.cigma.Ebank.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CompteBancaireOperation {
    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    private Double solde;
    private String description;
    @ManyToOne
    private CompteBancaire compteBancaire;
    @ManyToOne
    private Client client;


}