package br.com.makerouteeasy.domain.repositories;

import br.com.makerouteeasy.domain.entities.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, Integer> {

}
