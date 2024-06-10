package com.cigma.Ebank.dtos;

import com.cigma.Ebank.entities.Client;
import com.cigma.Ebank.entities.CompteBancaire;
import com.cigma.Ebank.enums.OperationType;
import lombok.Data;

import java.util.Date;


@Data
public class CompteBancaireOperationDto {
    private Long id;
    private Date createdAt;
    private OperationType operationType;
    private Double solde;
    private String description;
//    private CompteBancaire compteBancaire;
//    private Client client;


}