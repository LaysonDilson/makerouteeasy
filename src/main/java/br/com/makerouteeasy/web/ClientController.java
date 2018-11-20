package br.com.makerouteeasy.web;

import br.com.makerouteeasy.services.ClientService;
import br.com.makerouteeasy.web.representations.ClientRep;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/clients")
public class ClientController {

  private final ClientService clientService;

  @Autowired
  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping
  public ResponseEntity create(@RequestBody ClientRep clientRep) {
    clientService.createClient(clientRep);
    return new ResponseEntity(HttpStatus.CREATED);
  }

    @GetMapping(value="/{id}")
  public ResponseEntity<ClientRep> findClientById(@PathVariable("id") Integer id) {
    return new ResponseEntity<>(clientService.findClientById(id),HttpStatus.OK);
  }

  @PutMapping(value="/{id}")
  public ResponseEntity<ClientRep> updateClientById(@PathVariable("id") Integer id,@RequestBody ClientRep rep) {
    return new ResponseEntity<>(clientService.updateClient(id,rep),HttpStatus.OK);
  }

  /*
      Bonus: para adicinar uma massa de teste mais facilmente
     */
  @PostMapping("/list")
  public ResponseEntity createList(@RequestBody List<ClientRep> clientRep) {
    clientService.createClientAll(clientRep);
    return new ResponseEntity(HttpStatus.CREATED);
  }
}