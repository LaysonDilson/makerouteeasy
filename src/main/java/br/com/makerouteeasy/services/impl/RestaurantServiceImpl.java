package br.com.makerouteeasy.services.impl;

import br.com.makerouteeasy.commons.BusinessError;
import br.com.makerouteeasy.commons.BusinessException;
import br.com.makerouteeasy.domain.entities.Restaurant;
import br.com.makerouteeasy.domain.repositories.RestaurantRepository;
import br.com.makerouteeasy.services.RestaurantService;
import br.com.makerouteeasy.web.representations.RestaurantRep;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

  private final RestaurantRepository restaurantRepository;

  @Autowired
  public RestaurantServiceImpl(
      RestaurantRepository restaurantRepository) {
    this.restaurantRepository = restaurantRepository;
  }

  @Override
  @Transactional
  public void createRestaurant(RestaurantRep restaurantRep) {
    verifyRestaurant(restaurantRep);
    Restaurant res = new Restaurant.Builder()
        .id(restaurantRep.getId())
        .latitude(Float.valueOf(restaurantRep.getLat()))
        .longitude(Float.valueOf(restaurantRep.getLon()))
        .build();
    restaurantRepository.save(res);
  }

  @Override
  @Transactional
  public void createRestaurantAll(List<RestaurantRep> restaurantReps) {
    for (RestaurantRep rep : restaurantReps) {
      try {
        createRestaurant(rep);
      } catch (BusinessException e) {
        if (!e.getError().equals(BusinessError.RESTAURANT_ALREADY_CREATED)) {
          throw e;
        }
      }
    }
  }

  private void verifyRestaurant(RestaurantRep restaurantRep) {
    if (restaurantRep.getId() != null) {
      Optional<Restaurant> res = restaurantRepository.findById(restaurantRep.getId());
      if (res.isPresent()) {
        throw new BusinessException(BusinessError.RESTAURANT_ALREADY_CREATED,
            HttpStatus.BAD_REQUEST);
      }
    } else {
      throw new BusinessException(BusinessError.RESTAURANT_ID_REQUIRED, HttpStatus.BAD_REQUEST);
    }
    if (restaurantRep.getLat() == null || restaurantRep.getLat().isEmpty()) {
      throw new BusinessException(BusinessError.RESTAURANT_LAT_REQUIRED, HttpStatus.BAD_REQUEST);
    }
    if (restaurantRep.getLon() == null || restaurantRep.getLon().isEmpty()) {
      throw new BusinessException(BusinessError.RESTAURANT_LON_REQUIRED, HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public RestaurantRep findRestaurantById(Integer id) {
    Optional<Restaurant> resOp = restaurantRepository.findById(id);
    if (resOp.isEmpty()) {
      throw new BusinessException(BusinessError.RESTAURANT_NOT_FOUND, HttpStatus.NOT_FOUND);
    } else {
      Restaurant res = resOp.get();
      return new RestaurantRep(res.getId(), String.valueOf(res.getLatitude()),
          String.valueOf(res.getLongitude()));
    }
  }

  @Override
  @Transactional
  public RestaurantRep updateRestaurant(Integer id, RestaurantRep rep) {

    Optional<Restaurant> resOp = restaurantRepository.findById(id);

    if (resOp.isEmpty()) {
      throw new BusinessException(BusinessError.RESTAURANT_NOT_FOUND, HttpStatus.NOT_FOUND);
    } else {

      Restaurant res = resOp.get();

      Restaurant.Builder resBuilder = new Restaurant.Builder();
      resBuilder.id(res.getId());
      resBuilder.latitude(
          rep.getLat().isBlank() ? res.getLatitude() : Float.valueOf(rep.getLat())
      );
      resBuilder.longitude(
          rep.getLon().isBlank() ? res.getLongitude() : Float.valueOf(rep.getLon())
      );

      res = restaurantRepository.save(resBuilder.build());

      return new RestaurantRep(res.getId(), String.valueOf(res.getLatitude()),
          String.valueOf(res.getLongitude()));
    }

  }
}
