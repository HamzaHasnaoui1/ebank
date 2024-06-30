package com.cigma.Ebank.services;

import com.cigma.Ebank.dtos.*;
import com.cigma.Ebank.entities.AgentGuichet;
import com.cigma.Ebank.exceptions.SoldeNotSufficientException;
import com.cigma.Ebank.exceptions.BankAccountNotFoundException;
import com.cigma.Ebank.exceptions.ClientNotFoundException;

import java.util.List;

public interface CompteBancaireService {
    ClientDto saveClient(ClientDto clientDto);
    AgentGuichetDto saveAgentGuichet (AgentGuichetDto agentGuichetDto);

    ClientDto updateClient(ClientDto clientDto);

    void deleteClient(Long clientId);

    CompteBancaireDto SaveCompteBancaire(double initialAmount, Long clientId ) throws ClientNotFoundException;
    List<ClientDto> listClients();
    CompteBancaireDto getCompteBancaire(Long accountId) throws BankAccountNotFoundException;
    void debit (Long accountId , double amountOperation , String description) throws BankAccountNotFoundException, SoldeNotSufficientException;
    void credit (Long accountId , double amountOperation , String description) throws BankAccountNotFoundException;
    void transfert (Long accountIdSource,Long accountIdDestination ,double amountOperation ) throws BankAccountNotFoundException, SoldeNotSufficientException;

    List<CompteBancaireDto> compteBancaireList();

    ClientDto getClientById(Long clientId) throws ClientNotFoundException;

    ClientDto getClientByIdentityRef(String clientIdentityRef) throws ClientNotFoundException;

    List<CompteBancaireOperationDto> compteBancaireHistorique(Long compteBancaireId);

    CompteOperationsHistoriqueDto getCompteHistorique(Long compteBancaireId, int page, int size) throws BankAccountNotFoundException;

    List<ClientDto> searchClients(String keyword) ;

   ClientDto getClientByUsername(String keyword, int page) throws ClientNotFoundException;

    ClientDto getClientByUsername(String username);

}
