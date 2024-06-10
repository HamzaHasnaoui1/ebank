package com.cigma.Ebank.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AgentGuichet {
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

}
