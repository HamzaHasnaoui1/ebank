package com.cigma.Ebank;

import com.cigma.Ebank.dtos.AgentGuichetDto;
import com.cigma.Ebank.dtos.ClientDto;
import com.cigma.Ebank.dtos.CompteBancaireDto;
import com.cigma.Ebank.entities.AgentGuichet;
import com.cigma.Ebank.entities.CompteBancaire;
import com.cigma.Ebank.entities.CompteBancaireOperation;
import com.cigma.Ebank.entities.Client;
import com.cigma.Ebank.enums.AccountStatus;
import com.cigma.Ebank.enums.OperationType;
import com.cigma.Ebank.exceptions.SoldeNotSufficientException;
import com.cigma.Ebank.exceptions.BankAccountNotFoundException;
import com.cigma.Ebank.exceptions.ClientNotFoundException;
import com.cigma.Ebank.repositories.CompteBancaireOperationRepository;
import com.cigma.Ebank.repositories.CompteBancaireRepository;
import com.cigma.Ebank.repositories.ClientRepository;
import com.cigma.Ebank.repositories.AgentGuichetRepository;
import com.cigma.Ebank.security.services.CompteService;
import com.cigma.Ebank.services.CompteBancaireService;
import lombok.AllArgsConstructor;
import org.springdoc.core.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
@AllArgsConstructor
public class EbankApplication {
    CompteBancaireRepository compteBancaireRepository;

    public static void main(String[] args) {

        SpringApplication.run(EbankApplication.class, args);
    }

    //@Bean
    /*CommandLineRunner start(CompteBancaireService compteBancaireService, AgentGuichetRepository agentGuichetRepository) {
        return args -> {
            Stream.of("Salim", "Soufiane", "Abdo").forEach(prenom -> {
                ClientDto client = new ClientDto();
                client.setPrenom(prenom);
                client.setEmail(prenom + "@gmail.com");
                String identityRef = generateRandomIdentityRef();
                client.setIdentityRef(identityRef);
                String username;
                do {
                    username = generateRandomUsername();
                } while (agentGuichetRepository.existsByUsername(username));  // Vérification de l'unicité

                client.setUsername(prenom + username);

                String password = generateRandomPassword();
                client.setPassword(password);

                client.setDateNaissance("01/01/1998");
                client.setAdressePostal("40000");

                compteBancaireService.saveClient(client);
            });
            compteBancaireService.listClients().forEach(customer -> {
                try {
                    String rib = generateUniqueRib(compteBancaireRepository);  // Utiliser bankAccountRepository ici
                    compteBancaireService.SaveCompteBancaire(Math.random() * 90000, rib, customer.getIdentityRef());


                } catch (ClientNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<CompteBancaireDto> compteBancaires = compteBancaireService.compteBancaireList();
            for (CompteBancaireDto compteBancaire : compteBancaires) {
                for (int i = 0; i < 10; i++) {
                    compteBancaireService.debit(compteBancaire.getId(), 1000 * Math.random() * 12, "Debit");
                    compteBancaireService.credit(compteBancaire.getId(), 1000 * Math.random() * 90000, "Credit");

                }
            }
        };
    }*/


    //@Bean
    CommandLineRunner start(CompteBancaireRepository compteBancaireRepository) {
        return args -> {
            CompteBancaire compteBancaire =
                    compteBancaireRepository.findById(102L).orElse(null);
            System.out.println("**************");
            System.out.println(compteBancaire.getId());
            System.out.println(compteBancaire.getAccountStatus());
            System.out.println(compteBancaire.getSolde());
            System.out.println(compteBancaire.getCreatedAt());
            System.out.println(compteBancaire.getClient().getUsername());
            compteBancaire.getCompteBancaireOperation().forEach(compteBancaireOperation -> {
                System.out.println(compteBancaireOperation.getOperationType() + "\t" + compteBancaireOperation.getCreatedAt() + "\t" + compteBancaireOperation.getSolde());
            });
        };


    }

    //@Bean
   /* CommandLineRunner start(AgentGuichetRepository agentGuichetRepository, ClientRepository clientRepository, CompteBancaireRepository compteBancaireRepository, CompteBancaireOperationRepository compteBancaireOperationRepository) {
        return args -> {
            Stream.of("Simo", "Said", "Omar","hamza").forEach(name -> {
                Client client = new Client();
                client.setPrenom(name);
                client.setEmail(name + "@gmail.com");
                String identityRef = generateRandomIdentityRef();
                client.setIdentityRef(identityRef);
                String username;
                do {
                    username = generateRandomUsername();
                } while (agentGuichetRepository.existsByUsername(username));  // Vérification de l'unicité

                client.setUsername(name + username);

                String password = generateRandomPassword();
                client.setPassword(password);

                client.setDateNaissance("01/01/2000");
                client.setAdressePostal("22000");

                clientRepository.save(client);
            });
            clientRepository.findAll().forEach(customer -> {
                CompteBancaire compteBancaire = new CompteBancaire();
                compteBancaire.setSolde(Math.random() * 90000);
                compteBancaire.setCreatedAt(new Date());
                compteBancaire.setAccountStatus(AccountStatus.OUVERT);
                String rib = generateUniqueRib(compteBancaireRepository);
                compteBancaire.setRib(rib);
                compteBancaire.setClient(customer);
                compteBancaireRepository.save(compteBancaire);
            });

        };
    }
*/
    //@Bean
   /* CommandLineRunner start(CompteBancaireService compteBancaireService, AgentGuichetRepository agentGuichetRepository) {
        return args -> {
            Stream.of("simo", "Soufiane", "hamza").forEach(prenom -> {
                AgentGuichetDto agentGuichet = new AgentGuichetDto();
                agentGuichet.setPrenom(prenom);
                agentGuichet.setEmail(prenom + "@gmail.com");

                String username;
                do {
                    username = generateRandomUsername();
                } while (agentGuichetRepository.existsByUsername(username));  // Vérification de l'unicité

                agentGuichet.setUsername(prenom + username);

                String password = generateRandomPassword();
                agentGuichet.setPassword(password);

                compteBancaireService.saveAgentGuichet(agentGuichet);
            });


        };
    }*/



    private String generateRandomUsername() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder username = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {  // Par exemple, un nom d'utilisateur de 8 caractères
            username.append(characters.charAt(random.nextInt(characters.length())));
        }
        return username.toString();
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {  // Par exemple, un mot de passe de 12 caractères
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

    private String generateRandomRib() {
        String characters = "0123456789";
        StringBuilder rib = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 24; i++) {  // Par exemple, un mot de passe de 12 caractères
            rib.append(characters.charAt(random.nextInt(characters.length())));
        }
        return rib.toString();
    }

    private String generateRandomIdentityRef() {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        StringBuilder identityRef = new StringBuilder();
        Random random = new Random();

        // Ajouter les 2 premières lettres
        for (int i = 0; i < 2; i++) {
            identityRef.append(letters.charAt(random.nextInt(letters.length())));
        }

        // Générer 4 chiffres
        for (int i = 0; i < 4; i++) {
            identityRef.append(digits.charAt(random.nextInt(digits.length())));
        }

        return identityRef.toString();
    }

    private String generateUniqueRib(CompteBancaireRepository compteBancaireRepository) {
        String rib;
        do {
            rib = generateRandomRib();
        } while (compteBancaireRepository.existsByRib(rib));  // Vérification de l'unicité
        return rib;
    }

    //@Bean
    CommandLineRunner start(CompteBancaireRepository compteBancaireRepository, CompteBancaireOperationRepository compteBancaireOperationRepository) {
        return args -> {
            compteBancaireRepository.findAll().forEach(acc -> {
                for (int i = 0; i < 10; i++) {
                    CompteBancaireOperation compteBancaireOperation = new CompteBancaireOperation();
                    compteBancaireOperation.setCreatedAt(new Date());
                    compteBancaireOperation.setSolde(Math.random() * 12000);
                    compteBancaireOperation.setOperationType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    compteBancaireOperation.setCompteBancaire(acc);
                    compteBancaireOperationRepository.save(compteBancaireOperation);
                }
            });
        };
    }
    //@Bean
	CommandLineRunner saveUsers(CompteService compteService) {
        return args -> {
            compteService.addNewUser("zanfar", "1234", "1234");
            compteService.addNewUser("hamza", "1234", "1234");
            compteService.addNewUser("ali", "1234", "1234");


            compteService.addNewRole("CLIENT", "");
            compteService.addNewRole("AGENT_GUICHET", "");

            compteService.addRoleToUser("hamza", "CLIENT");
            compteService.addRoleToUser("ali", "AGENT_GUICHET");
            compteService.addRoleToUser("zanfar", "CLIENT");
            compteService.addRoleToUser("hamza", "AGENT_GUICHET");
        };
    }
}
