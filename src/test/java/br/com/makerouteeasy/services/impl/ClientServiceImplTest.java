package br.com.makerouteeasy.services.impl;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.makerouteeasy.commons.BusinessException;
import br.com.makerouteeasy.domain.entities.Client;
import br.com.makerouteeasy.domain.repositories.ClientRepository;
import br.com.makerouteeasy.web.representations.ClientRep;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {

  @InjectMocks
  private ClientServiceImpl clientService;
  @Mock
  private ClientRepository clientRepository;

  @Before
  public void Setup() {

  }

  @Test
  public void createClientSuccess() {
    ClientRep rep = new ClientRep(0, "0.1", "0.3");

    when(clientRepository.findById(0)).thenReturn(Optional.empty());
    when(clientRepository.save(any(Client.class))).thenReturn(null);

    clientService.createClient(rep);

    verify(clientRepository, times(1)).save(any(Client.class));
  }


  @Test(expected = BusinessException.class)
  public void createClientErrorAlreadyCreated() {
    ClientRep rep = new ClientRep(0, "0.1", "0.3");

    when(clientRepository.findById(0))
        .thenReturn(Optional.of(new Client.Builder().build()));

    clientService.createClient(rep);

  }

  @Test(expected = BusinessException.class)
  public void createClientErrorIdRequired() {
    ClientRep rep = new ClientRep(null, "0.1", "0.3");

    clientService.createClient(rep);

  }

  @Test(expected = BusinessException.class)
  public void createClientErrorLatRequired() {
    ClientRep rep = new ClientRep(0, null, "0.3");

    when(clientRepository.findById(0)).thenReturn(Optional.empty());

    clientService.createClient(rep);

  }

  @Test(expected = BusinessException.class)
  public void createClientErrorLonRequired() {
    ClientRep rep = new ClientRep(0, "0.1", null);

    when(clientRepository.findById(0)).thenReturn(Optional.empty());

    clientService.createClient(rep);

  }

  @Test
  public void findClientByIdSuccess() {
    Client res = new Client.Builder()
        .id(0)
        .latitude(Float.valueOf("0.1"))
        .longitude(Float.valueOf("0.2"))
        .build();

    when(clientRepository.findById(0)).thenReturn(Optional.of(res));
    ClientRep rep = clientService.findClientById(0);
    Assert.assertEquals(res.getId(), rep.getId());
    Assert.assertEquals(res.getLatitude(), Float.valueOf(rep.getLat()));
    Assert.assertEquals(res.getLongitude(), Float.valueOf(rep.getLon()));
  }

  @Test(expected = BusinessException.class)
  public void findClientByIdNotFound() {
    when(clientRepository.findById(0)).thenReturn(Optional.empty());
    ClientRep rep = clientService.findClientById(0);

  }

  @Test
  public void updateClientSuccess() {
    ClientRep rep = new ClientRep(0, "0.1", "0.3");

    Client res = new Client.Builder()
        .id(0)
        .latitude(Float.valueOf("0.2"))
        .longitude(Float.valueOf("0.2"))
        .build();

    when(clientRepository.findById(0)).thenReturn(Optional.of(res));
    when(clientRepository.save(any(Client.class))).thenReturn(new Client.Builder()
        .id(0)
        .latitude(Float.valueOf("0.1"))
        .longitude(Float.valueOf("0.3"))
        .build());
    ClientRep reps = clientService.updateClient(0, rep);
    Assert.assertEquals(rep.getId(), reps.getId());
    Assert.assertEquals(rep.getLat(), reps.getLat());
    Assert.assertEquals(rep.getLon(), reps.getLon());
  }

  @Test(expected = BusinessException.class)
  public void updateClientNotfound() {

    ClientRep rep = new ClientRep(0, "0.1", "0.3");
    when(clientRepository.findById(0)).thenReturn(Optional.empty());
    ClientRep reps = clientService.updateClient(0, rep);

  }

  @Test
  public void createClientAllSucess(){
    List<ClientRep> clientReps = Arrays
        .asList(new ClientRep(0, "0.1", "0.3"),new ClientRep(1, "1.1", "1.3"));

    when(clientRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
    when(clientRepository.save(any(Client.class))).thenReturn(null);

    clientService.createClientAll(clientReps);

    verify(clientRepository, times(2)).save(any(Client.class));

  }



  @Test
  public void createClientAllErrorAlreadyCreated() {
    List<ClientRep> clientReps = Arrays.asList(new ClientRep(0, "0.1", "0.3"),new ClientRep(1, "1.1", "1.3"));

    when(clientRepository.findById(0)).thenReturn(Optional.empty());
    when(clientRepository.findById(1)).thenReturn(Optional.of(new Client.Builder().build()));
    when(clientRepository.save(any(Client.class))).thenReturn(null);

    clientService.createClientAll(clientReps);

    verify(clientRepository, times(1)).save(any(Client.class));

  }

  @Test(expected = BusinessException.class)
  public void createClientAllErrorIdRequired() {

    List<ClientRep> clientReps = Arrays.asList(new ClientRep(null, "0.1", "0.3"),new ClientRep(1, "1.1", "1.3"));
    clientService.createClientAll(clientReps);

  }

  @Test(expected = BusinessException.class)
  public void createClientAllErrorLatRequired() {
    List<ClientRep> clientReps = Arrays.asList(new ClientRep(0, null, "0.3"),new ClientRep(1, "1.1", "1.3"));

    when(clientRepository.findById(0)).thenReturn(Optional.empty());

    clientService.createClientAll(clientReps);

  }

  @Test(expected = BusinessException.class)
  public void createClientAllErrorLonRequired() {
    List<ClientRep> clientReps = Arrays.asList(new ClientRep(0, "0.1", null),new ClientRep(1, "1.1", "1.3"));

    when(clientRepository.findById(0)).thenReturn(Optional.empty());

    clientService.createClientAll(clientReps);

  }
  
}