package com.cigma.Ebank.web;

import com.cigma.Ebank.dtos.CompteBancaireDto;
import com.cigma.Ebank.dtos.CompteBancaireOperationDto;
import com.cigma.Ebank.dtos.CompteOperationsHistoriqueDto;
import com.cigma.Ebank.exceptions.BankAccountNotFoundException;
import com.cigma.Ebank.exceptions.ClientNotFoundException;
import com.cigma.Ebank.services.CompteBancaireService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class CompteBancaireController {
    private CompteBancaireService compteBancaireService;

    @GetMapping("/comptes/{compteId}")
    public CompteBancaireDto getCompteBancaire (@PathVariable Long compteId)  throws BankAccountNotFoundException{
        return compteBancaireService.getCompteBancaire(compteId);
    }

    @GetMapping("/comptes")
    public List<CompteBancaireDto> listCompteBancaire() {
        return compteBancaireService.compteBancaireList();
    }

  @GetMapping("/comptes/{compteBancaireId}/operations")
    public List<CompteBancaireOperationDto> getHistorique(@PathVariable Long compteBancaireId){
        return compteBancaireService.compteBancaireHistorique(compteBancaireId);
    }

    @GetMapping("/comptes/{compteBancaireId}/pageOperations")
    public CompteOperationsHistoriqueDto getCompteHistorique(
            @PathVariable Long compteBancaireId,
            @RequestParam (name = "page" ,defaultValue = "0") int page,
            @RequestParam (name = "size" ,defaultValue = "10") int size) throws BankAccountNotFoundException {
        return compteBancaireService.getCompteHistorique(compteBancaireId,page,size);
    }

    @PostMapping ("/openAccount")
    public CompteBancaireDto createCompteBancaire(@RequestParam double initialAmount, @RequestParam Long clientId ) throws ClientNotFoundException {
        return compteBancaireService.SaveCompteBancaire(initialAmount,clientId);
    }

    /*@PostMapping("/create")
    public ResponseEntity<?> createCompteBancaire(@RequestParam double initialAmount, @RequestParam Long clientId ){
        try {
            CompteBancaireDto compteBancaireDto = compteBancaireService.SaveCompteBancaire(initialAmount, clientId);
            return new ResponseEntity<>(compteBancaireDto, HttpStatus.CREATED);
        } catch (ClientNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}
