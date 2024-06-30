package com.cigma.Ebank.web;

import com.cigma.Ebank.dtos.ClientDto;
import com.cigma.Ebank.exceptions.ClientNotFoundException;
import com.cigma.Ebank.security.entities.AppUser;
import com.cigma.Ebank.services.CompteBancaireService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
//@CrossOrigin("*") //Pour autoriser tout les domaines d'envoyer des requets(front)
public class ClientRestController {
    private CompteBancaireService compteBancaireService;

    @GetMapping("/clients")
    public List<ClientDto> clients(){
        return compteBancaireService.listClients();
    }

    @GetMapping("/clients/search")
    public List<ClientDto> searchClients(@RequestParam(name = "keyword", defaultValue = "")String keyword){

        return compteBancaireService.searchClients("%"+keyword+"%");
    }

  /*  @PostAuthorize("hasAuthority('AGENT_GUICHET')")
    @GetMapping("/clients/search")
    public ClientDto getCustomerByName(@RequestParam(name = "keyword", defaultValue = "") String keyword, @RequestParam(name = "page", defaultValue = "0") int page) throws ClientNotFoundException {
        ClientDto customersDTO = compteBancaireService.getClientByUsername("%" + keyword + "%", page);
        return customersDTO;
    }*/

    @GetMapping("/clients/{id}")
    public ClientDto getClientById (@PathVariable(name = "id") Long clientId) throws ClientNotFoundException {
        return compteBancaireService.getClientById(clientId);
    }

//    @GetMapping("/clients/{identityRef}")
//    public ClientDto getClientByIdentityRef (@PathVariable(name = "identityRef") String clientIdentityRef ) throws ClientNotFoundException {
//        return compteBancaireService.getClientByIdentityRef(clientIdentityRef);
//    }

    @PostMapping ("/clients")
    public ClientDto saveClient(@RequestBody ClientDto clientDto){
        return compteBancaireService.saveClient(clientDto);
    }

    @PutMapping ("/clients/{clientId}")
    public ClientDto updateClient(@PathVariable Long clientId , @RequestBody ClientDto clientDto){
        clientDto.setId(clientId);
        return compteBancaireService.updateClient(clientDto);
    }
@GetMapping("/clients/name/{username}")
    public ClientDto getIdfromUsername(@PathVariable String username){
        return compteBancaireService.getClientByUsername(username);
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable Long id){
        compteBancaireService.deleteClient(id);
    }
}
