package com.cigma.Ebank.dtos;

import lombok.Data;

@Data
public class ClientDto {
    private Long id;
    private String username;
    private String prenom;
    private String nom;
    private String password;
    private String email;
    private String dateNaissance;
    private String adressePostal;
    private String identityRef;
}
