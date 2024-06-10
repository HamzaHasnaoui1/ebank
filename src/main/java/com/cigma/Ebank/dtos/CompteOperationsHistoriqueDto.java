package com.cigma.Ebank.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CompteOperationsHistoriqueDto {
    private Long compteId;
    private double solde;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List <CompteBancaireOperationDto> compteBancaireOperationDtos;
}
