package br.com.makerouteeasy.domain.repositories;

import br.com.makerouteeasy.domain.entities.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantRepository  extends MongoRepository<Restaurant, Integer> {

}
