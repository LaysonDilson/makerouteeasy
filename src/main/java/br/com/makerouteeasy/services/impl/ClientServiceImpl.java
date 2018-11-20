package br.com.makerouteeasy.services.impl;

import br.com.makerouteeasy.commons.BusinessError;
import br.com.makerouteeasy.commons.BusinessException;
import br.com.makerouteeasy.domain.entities.Client;
import br.com.makerouteeasy.domain.repositories.ClientRepository;
import br.com.makerouteeasy.services.ClientService;
import br.com.makerouteeasy.web.representations.ClientRep;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientServiceImpl implements ClientService {

  private final ClientRepository clientRepository;

  @Autowired
  public ClientServiceImpl(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }


  @Override
  @Transactional
  public void createClient(ClientRep clientRep) {
    verifyClient(clientRep);
    Client cl = new Client.Builder()
        .id(clientRep.getId())
        .latitude(Float.valueOf(clientRep.getLat()))
        .longitude(Float.valueOf(clientRep.getLon()))
        .build();
    clientRepository.save(cl);
  }

  @Override
  @Transactional
  public void createClientAll(List<ClientRep> clientReps) {
    for (ClientRep rep : clientReps) {
      try {
        createClient(rep);
      } catch (BusinessException e) {
        if (!e.getError().equals(BusinessError.CLIENT_ALREADY_CREATED)) {
          throw e;
        }
      }
    }
  }

  private void verifyClient(ClientRep clientRep) {
    if (clientRep.getId() != null) {
      Optional<Client> client = clientRepository.findById(clientRep.getId());
      if (client.isPresent()) {
        throw new BusinessException(BusinessError.CLIENT_ALREADY_CREATED, HttpStatus.BAD_REQUEST);
      }
    } else {
      throw new BusinessException(BusinessError.CLIENT_ID_REQUIRED, HttpStatus.BAD_REQUEST);
    }
    if (clientRep.getLat()==null||clientRep.getLat().isEmpty()) {
      throw new BusinessException(BusinessError.CLIENT_LAT_REQUIRED, HttpStatus.BAD_REQUEST);
    }
    if (clientRep.getLon()==null||clientRep.getLon().isEmpty()) {
      throw new BusinessException(BusinessError.CLIENT_LON_REQUIRED, HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ClientRep findClientById(Integer id) {
    Optional<Client> clientOp = clientRepository.findById(id);
    if (clientOp.isEmpty()) {
      throw new BusinessException(BusinessError.CLIENT_NOT_FOUND, HttpStatus.NOT_FOUND);
    } else {
      Client client = clientOp.get();
      return new ClientRep(client.getId(), String.valueOf(client.getLatitude()),
          String.valueOf(client.getLongitude()));
    }
  }

  @Override
  @Transactional
  public ClientRep updateClient(Integer id, ClientRep clientRep) {

    Optional<Client> client = clientRepository.findById(id);

    if (client.isEmpty()) {
      throw new BusinessException(BusinessError.CLIENT_NOT_FOUND, HttpStatus.NOT_FOUND);
    } else {

      Client cl = client.get();

      Client.Builder clBuilder = new Client.Builder();
      clBuilder.id(cl.getId());
      clBuilder.latitude(
          clientRep.getLat().isBlank() ? cl.getLatitude() : Float.valueOf(clientRep.getLat())
      );
      clBuilder.longitude(
          clientRep.getLon().isBlank() ? cl.getLongitude() : Float.valueOf(clientRep.getLon())
      );

      cl = clientRepository.save(clBuilder.build());

      return new ClientRep(cl.getId(), String.valueOf(cl.getLatitude()),
          String.valueOf(cl.getLongitude()));
    }
  }
}
