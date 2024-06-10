package com.cigma.Ebank.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Client {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String username;
    private String prenom;
    private String nom;
    private String password;
    @Column(unique = true)
    private String email;
    private String dateNaissance;
    private String adressePostal;
    @Column(unique = true)
    private String identityRef;
    @OneToMany(mappedBy = "client")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<CompteBancaire> compteBancaire;

}
