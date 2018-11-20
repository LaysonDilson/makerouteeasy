package br.com.makerouteeasy.services;

import br.com.makerouteeasy.web.representations.RestaurantRep;
import java.util.List;

public interface RestaurantService {

  void createRestaurant(RestaurantRep restaurantRep);

  RestaurantRep findRestaurantById(Integer id);

  RestaurantRep updateRestaurant(Integer id, RestaurantRep rep);

  void createRestaurantAll(List<RestaurantRep> restaurantReps);
}
