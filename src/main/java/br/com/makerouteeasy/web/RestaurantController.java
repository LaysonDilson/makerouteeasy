package br.com.makerouteeasy.web;

import br.com.makerouteeasy.services.RestaurantService;
import br.com.makerouteeasy.web.representations.RestaurantRep;
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
@RequestMapping("/api/restaurants")
public class RestaurantController {


  private final RestaurantService restaurantService;

  @Autowired
  public RestaurantController(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  @PostMapping
  public ResponseEntity create(@RequestBody RestaurantRep restaurantRep) {
    restaurantService.createRestaurant(restaurantRep);
    return new ResponseEntity(HttpStatus.CREATED);
  }

  @GetMapping(value="/{id}")
  public ResponseEntity<RestaurantRep> findRestaurantById(@PathVariable("id") Integer id) {
    return new ResponseEntity<>(restaurantService.findRestaurantById(id),HttpStatus.OK);
  }

  @PutMapping(value="/{id}")
  public ResponseEntity<RestaurantRep> updateRestaurantsById(@PathVariable("id") Integer id,@RequestBody RestaurantRep rep) {
    return new ResponseEntity<>(restaurantService.updateRestaurant(id,rep),HttpStatus.OK);
  }
  /*
    Bonus: para adicinar uma massa de teste mais facilmente
   */
  @PostMapping("/list")
  public ResponseEntity createList(@RequestBody List<RestaurantRep> restaurantReps) {
    restaurantService.createRestaurantAll(restaurantReps);
    return new ResponseEntity(HttpStatus.CREATED);
  }
}
