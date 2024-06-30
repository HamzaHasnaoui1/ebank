package com.cigma.Ebank.services;

import com.cigma.Ebank.dtos.*;
import com.cigma.Ebank.entities.AgentGuichet;
import com.cigma.Ebank.entities.CompteBancaire;
import com.cigma.Ebank.entities.CompteBancaireOperation;
import com.cigma.Ebank.entities.Client;
import com.cigma.Ebank.enums.AccountStatus;
import com.cigma.Ebank.enums.OperationType;
import com.cigma.Ebank.exceptions.SoldeNotSufficientException;
import com.cigma.Ebank.exceptions.BankAccountNotFoundException;
import com.cigma.Ebank.exceptions.ClientNotFoundException;
import com.cigma.Ebank.mappers.CompteBancaireMapperImpl;
import com.cigma.Ebank.repositories.AgentGuichetRepository;
import com.cigma.Ebank.repositories.CompteBancaireOperationRepository;
import com.cigma.Ebank.repositories.CompteBancaireRepository;
import com.cigma.Ebank.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CompteBancaireServiceImpl implements CompteBancaireService {

    private CompteBancaireRepository compteBancaireRepository;
    private CompteBancaireOperationRepository compteBancaireOperationRepository;
    private ClientRepository clientRepository;
    private AgentGuichetRepository agentGuichetRepository;
    private CompteBancaireMapperImpl compteBancaireMapper;

    @Override
    public ClientDto saveClient(ClientDto clientDto) {
        log.info("Saving new Customer");
        Client client= compteBancaireMapper.fromClientDto(clientDto);
        Client savedClient = clientRepository.save(client);
        return compteBancaireMapper.fromClient(savedClient);
    }

    @Override
    public AgentGuichetDto saveAgentGuichet(AgentGuichetDto agentGuichetDto) {
        log.info("Savin new Agent Guichet");
        AgentGuichet agentGuichet=compteBancaireMapper.fromAgentGuichetDto(agentGuichetDto);
        AgentGuichet savedAgentGuichet=agentGuichetRepository.save(agentGuichet);
        return compteBancaireMapper.fromAgentGuichet(savedAgentGuichet);
    }

    @Override
    public ClientDto updateClient(ClientDto clientDto) {
        log.info("Saving new Customer");
        Client client= compteBancaireMapper.fromClientDto(clientDto);
        Client savedClient = clientRepository.save(client);
        return compteBancaireMapper.fromClient(savedClient);
    }

    @Override
    public void deleteClient(Long clientId){
        clientRepository.deleteById(clientId);
    }


    @Override
    public CompteBancaireDto SaveCompteBancaire(double initialAmount, Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            throw new ClientNotFoundException("Customer not found");
        }

        CompteBancaire compteBancaire = new CompteBancaire();
        compteBancaire.setAccountStatus(AccountStatus.OUVERT);
        String rib = generateUniqueRib(compteBancaireRepository);
        compteBancaire.setRib(rib);
        compteBancaire.setSolde(initialAmount);
        compteBancaire.setCreatedAt(new Date());
        compteBancaire.setClient(client);

        CompteBancaire savedCompteBancaire = compteBancaireRepository.save(compteBancaire);
        return compteBancaireMapper.fromCompteBancaire(savedCompteBancaire);
    }

    private String generateUniqueRib(CompteBancaireRepository compteBancaireRepository) {
        String rib;
        do {
            rib = generateRandomRib();
        } while (compteBancaireRepository.existsByRib(rib));
        return rib;
    }

    private String generateRandomRib() {
        String characters = "0123456789";
        StringBuilder rib = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 24; i++) {
            rib.append(characters.charAt(random.nextInt(characters.length())));
        }
        return rib.toString();
    }

    @Override
    public List<ClientDto> listClients() {

        List<Client> clients = clientRepository.findAll();
        List<ClientDto> clientDtos=clients.stream()
                .map(client -> compteBancaireMapper.fromClient(client))
                .collect(Collectors.toList());
        return clientDtos;
    }


    @Override
    public CompteBancaireDto getCompteBancaire(Long accountId) throws BankAccountNotFoundException {
        CompteBancaire compteBancaire = compteBancaireRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found exception"));
        return compteBancaireMapper.fromCompteBancaire(compteBancaire);
    }

    @Override
    public void debit(Long accountId, double amountOperation, String description) throws BankAccountNotFoundException, SoldeNotSufficientException {
        CompteBancaire compteBancaire = compteBancaireRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found exception"));
        if (compteBancaire.getSolde()<amountOperation)
            throw new SoldeNotSufficientException("Solde insuffisant");
        CompteBancaireOperation compteBancaireOperation =new CompteBancaireOperation();
        compteBancaireOperation.setOperationType(OperationType.DEBIT);
        compteBancaireOperation.setSolde(amountOperation);
        compteBancaireOperation.setDescription(description);
        compteBancaireOperation.setCreatedAt(new Date());
        compteBancaireOperation.setCompteBancaire(compteBancaire);
        compteBancaireOperationRepository.save(compteBancaireOperation);
        compteBancaire.setSolde(compteBancaire.getSolde()-amountOperation);
        compteBancaireRepository.save(compteBancaire);
    }

    @Override
    public void credit(Long accountId, double amountOperation, String description) throws BankAccountNotFoundException {
        CompteBancaire compteBancaire = compteBancaireRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found exception"));
        CompteBancaireOperation compteBancaireOperation =new CompteBancaireOperation();
        compteBancaireOperation.setOperationType(OperationType.CREDIT);
        compteBancaireOperation.setSolde(amountOperation);
        compteBancaireOperation.setDescription(description);
        compteBancaireOperation.setCreatedAt(new Date());
        compteBancaireOperation.setCompteBancaire(compteBancaire);
        compteBancaireOperationRepository.save(compteBancaireOperation);
        compteBancaire.setSolde(compteBancaire.getSolde()+amountOperation);
        compteBancaireRepository.save(compteBancaire);
    }

    @Override
    public void transfert(Long accountIdSource, Long accountIdDestination, double amountOperation) throws BankAccountNotFoundException, SoldeNotSufficientException {
        debit(accountIdSource,amountOperation,"Transfer to "+accountIdDestination);
        credit(accountIdDestination,amountOperation,"Transfer from "+accountIdSource);
    }

    @Override
    public List<CompteBancaireDto> compteBancaireList() {
        List<CompteBancaire> compteBancaires = compteBancaireRepository.findAll();
        return compteBancaires.stream()
                .map(compteBancaireMapper::fromCompteBancaire)
                .collect(Collectors.toList());
    }


    @Override
    public ClientDto getClientById(Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException("Client non trouve"));
        return compteBancaireMapper.fromClient(client);
    }

    @Override
    public ClientDto getClientByIdentityRef(String clientIdentityRef) throws ClientNotFoundException {
        Client client= clientRepository.findByIdentityRef(clientIdentityRef).orElseThrow(()->new ClientNotFoundException("Client non trouve"));
        return compteBancaireMapper.fromClient(client);
    }

   @Override
    public List<CompteBancaireOperationDto> compteBancaireHistorique (Long compteBancaireId){
        List<CompteBancaireOperation> compteBancaireOperations= compteBancaireOperationRepository.findByCompteBancaire_id(compteBancaireId);
        return compteBancaireOperations.stream().map(compteBancaireOperation -> compteBancaireMapper.fromCompteBancaireOperation(compteBancaireOperation)).collect(Collectors.toList());
  }

    @Override
    public CompteOperationsHistoriqueDto getCompteHistorique(Long compteBancaireId, int page, int size) throws BankAccountNotFoundException {
        CompteBancaire compteBancaire = compteBancaireRepository.findById(compteBancaireId).orElse(null);
        if (compteBancaire==null) throw new BankAccountNotFoundException("Compte bancaire non trouve");
        Page<CompteBancaireOperation> compteBancaireOperations = compteBancaireOperationRepository.findByCompteBancaire_id(compteBancaireId, PageRequest.of(page, size));
        CompteOperationsHistoriqueDto compteOperationsHistoriqueDto=new CompteOperationsHistoriqueDto();
        List<CompteBancaireOperationDto> compteBancaireOperationDtos= compteBancaireOperations.getContent().stream().map(op->compteBancaireMapper.fromCompteBancaireOperation(op)).collect(Collectors.toList());
        compteOperationsHistoriqueDto.setCompteBancaireOperationDtos(compteBancaireOperationDtos);
        compteOperationsHistoriqueDto.setCompteId(compteBancaire.getId());
        compteOperationsHistoriqueDto.setSolde(compteBancaire.getSolde());
        compteOperationsHistoriqueDto.setCurrentPage(page);
        compteOperationsHistoriqueDto.setPageSize(size);
        compteOperationsHistoriqueDto.setTotalPages(compteBancaireOperations.getTotalPages());
        return compteOperationsHistoriqueDto;
    }

    @Override
    public List<ClientDto> searchClients(String keyword) {
        List<Client> clients=clientRepository.searchClient(keyword);
        List<ClientDto> clientDtos = clients.stream().map(client -> compteBancaireMapper.fromClient(client)).collect(Collectors.toList());
        return clientDtos;
    }





//    @Override
//    public ClientDto getClientByName(String keyword, int page) throws ClientNotFoundException {
//        Page<Client> customers ;
//
//        customers = clientRepository.searchByName(keyword,PageRequest.of(page,5));
//        List<ClientDto> clientDtos=clients.getContent().stream().map(c->compteBancaireMapper.fromClient(c)).collect(Collectors.toList());
//        if (clients == null)
//            throw new ClientNotFoundException("customer not fount");
//
//        ClientDto clientDto= new ClientDto();
//        clientDto.setClientDto(clientDto);
//        clientDto.setTotalpage(clients.getTotalPages());
//        return clientDto;
//    }

    @Override
    public ClientDto getClientByUsername(String username) {
        Client client = clientRepository.getClientByUsername(username);
        return  compteBancaireMapper.fromClient(client);
    }
    @Override
    public ClientDto getClientByUsername(String keyword, int page) throws ClientNotFoundException {
        return null;
    }
}
