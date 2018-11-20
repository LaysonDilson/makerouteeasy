package br.com.makerouteeasy.services;

import br.com.makerouteeasy.web.representations.ClientRep;
import java.util.List;


public interface ClientService {

  void createClient(ClientRep clientRep);

  ClientRep findClientById(Integer id);

  ClientRep updateClient(Integer id, ClientRep clientRep);

  void createClientAll(List<ClientRep> clientRep);
}
