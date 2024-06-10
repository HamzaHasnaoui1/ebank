package com.cigma.Ebank.entities;

import com.cigma.Ebank.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CompteBancaire {
    @Id
    @GeneratedValue
    private Long id;
    private double solde;
    @Column(unique = true)
    private String rib;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @ManyToOne
    private Client client;
    @OneToMany(mappedBy = "compteBancaire",fetch = FetchType.LAZY)
    private List<CompteBancaireOperation> compteBancaireOperation;
}
