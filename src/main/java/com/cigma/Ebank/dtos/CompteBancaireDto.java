package com.cigma.Ebank.dtos;

import com.cigma.Ebank.enums.AccountStatus;

import lombok.Data;

import java.util.Date;
import java.util.List;



@Data
public class CompteBancaireDto {

    private Long id;
    private double solde;
    private String rib;
    private Date createdAt;
    private AccountStatus accountStatus;
    private ClientDto clientDto;
}
