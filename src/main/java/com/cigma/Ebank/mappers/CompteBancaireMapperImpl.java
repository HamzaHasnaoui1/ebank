package com.cigma.Ebank.mappers;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.cigma.Ebank.dtos.AgentGuichetDto;
import com.cigma.Ebank.dtos.ClientDto;
import com.cigma.Ebank.dtos.CompteBancaireDto;
import com.cigma.Ebank.dtos.CompteBancaireOperationDto;
import com.cigma.Ebank.entities.AgentGuichet;
import com.cigma.Ebank.entities.Client;
import com.cigma.Ebank.entities.CompteBancaire;
import com.cigma.Ebank.entities.CompteBancaireOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CompteBancaireMapperImpl {

   public ClientDto fromClient(Client client){
       ClientDto clientDto=new ClientDto();
       BeanUtils.copyProperties(client,clientDto);
       return clientDto;
   }

   public Client fromClientDto(ClientDto clientDto){
       Client client=new Client();
       BeanUtils.copyProperties(clientDto,client);
       return client;
   }

   public AgentGuichetDto fromAgentGuichet(AgentGuichet agentGuichet){
       AgentGuichetDto agentGuichetDto=new AgentGuichetDto();
       BeanUtils.copyProperties(agentGuichet,agentGuichetDto);
       return agentGuichetDto;
   }

   public AgentGuichet fromAgentGuichetDto(AgentGuichetDto agentGuichetDto){
       AgentGuichet agentGuichet=new AgentGuichet();
       BeanUtils.copyProperties(agentGuichetDto,agentGuichet);
       return agentGuichet;
   }

   public CompteBancaireDto fromCompteBancaire(CompteBancaire compteBancaire){
       CompteBancaireDto compteBancaireDto=new CompteBancaireDto();
       BeanUtils.copyProperties(compteBancaire,compteBancaireDto);
       compteBancaireDto.setClientDto(fromClient(compteBancaire.getClient()));
       return compteBancaireDto;
   }

   public CompteBancaire fromCompteBancaireDto(CompteBancaireDto compteBancaireDto){
       CompteBancaire compteBancaire=new CompteBancaire();
       BeanUtils.copyProperties(compteBancaireDto,compteBancaire);
        compteBancaire.setClient(fromClientDto(compteBancaireDto.getClientDto()));
       return  compteBancaire;
   }

   public CompteBancaireOperationDto fromCompteBancaireOperation (CompteBancaireOperation compteBancaireOperation){
       CompteBancaireOperationDto compteBancaireOperationDto=new CompteBancaireOperationDto();
       BeanUtils.copyProperties(compteBancaireOperation,compteBancaireOperationDto);
       return compteBancaireOperationDto;
   }
}
