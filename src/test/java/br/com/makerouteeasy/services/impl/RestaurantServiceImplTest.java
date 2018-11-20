package br.com.makerouteeasy.services.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.makerouteeasy.commons.BusinessException;
import br.com.makerouteeasy.domain.entities.Restaurant;
import br.com.makerouteeasy.domain.repositories.RestaurantRepository;
import br.com.makerouteeasy.web.representations.RestaurantRep;
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
public class RestaurantServiceImplTest {

  @InjectMocks
  private RestaurantServiceImpl restaurantService;
  @Mock
  private RestaurantRepository restaurantRepository;

  @Before
  public void Setup() {

  }

  @Test
  public void createRestaurantSuccess() {
    RestaurantRep rep = new RestaurantRep(0, "0.1", "0.3");
    Restaurant res = new Restaurant.Builder()
        .id(rep.getId())
        .latitude(Float.valueOf(rep.getLat()))
        .longitude(Float.valueOf(rep.getLon()))
        .build();

    when(restaurantRepository.findById(0)).thenReturn(Optional.empty());
    when(restaurantRepository.save(any(Restaurant.class))).thenReturn(null);

    restaurantService.createRestaurant(rep);

    verify(restaurantRepository, times(1)).save(res);
  }


  @Test(expected = BusinessException.class)
  public void createRestaurantErrorAlreadyCreated() {
    RestaurantRep rep = new RestaurantRep(0, "0.1", "0.3");

    when(restaurantRepository.findById(0))
        .thenReturn(Optional.of(new Restaurant.Builder().build()));

    restaurantService.createRestaurant(rep);

  }

  @Test(expected = BusinessException.class)
  public void createRestaurantErrorIdRequired() {
    RestaurantRep rep = new RestaurantRep(null, "0.1", "0.3");

    restaurantService.createRestaurant(rep);

  }

  @Test(expected = BusinessException.class)
  public void createRestaurantErrorLatRequired() {
    RestaurantRep rep = new RestaurantRep(0, null, "0.3");

    when(restaurantRepository.findById(0)).thenReturn(Optional.empty());

    restaurantService.createRestaurant(rep);

  }

  @Test(expected = BusinessException.class)
  public void createRestaurantErrorLonRequired() {
    RestaurantRep rep = new RestaurantRep(0, "0.1", null);

    when(restaurantRepository.findById(0)).thenReturn(Optional.empty());

    restaurantService.createRestaurant(rep);

  }

  @Test
  public void findRestaurantByIdSuccess() {
    Restaurant res = new Restaurant.Builder()
        .id(0)
        .latitude(Float.valueOf("0.1"))
        .longitude(Float.valueOf("0.2"))
        .build();

    when(restaurantRepository.findById(0)).thenReturn(Optional.of(res));
    RestaurantRep rep = restaurantService.findRestaurantById(0);
    Assert.assertEquals(res.getId(), rep.getId());
    Assert.assertEquals(res.getLatitude(), Float.valueOf(rep.getLat()));
    Assert.assertEquals(res.getLongitude(), Float.valueOf(rep.getLon()));
  }

  @Test(expected = BusinessException.class)
  public void findRestaurantByIdNotFound() {
    when(restaurantRepository.findById(0)).thenReturn(Optional.empty());
    RestaurantRep rep = restaurantService.findRestaurantById(0);

  }

  @Test
  public void updateRestaurantSuccess() {
    RestaurantRep rep = new RestaurantRep(0, "0.1", "0.3");

    Restaurant res = new Restaurant.Builder()
        .id(0)
        .latitude(Float.valueOf("0.2"))
        .longitude(Float.valueOf("0.2"))
        .build();

    when(restaurantRepository.findById(0)).thenReturn(Optional.of(res));
    when(restaurantRepository.save(any(Restaurant.class))).thenReturn(new Restaurant.Builder()
        .id(0)
        .latitude(Float.valueOf("0.1"))
        .longitude(Float.valueOf("0.3"))
        .build());
    RestaurantRep reps = restaurantService.updateRestaurant(0, rep);
    Assert.assertEquals(rep.getId(), reps.getId());
    Assert.assertEquals(rep.getLat(), reps.getLat());
    Assert.assertEquals(rep.getLon(), reps.getLon());
  }

  @Test(expected = BusinessException.class)
  public void updateRestaurantNotfound() {

    RestaurantRep rep = new RestaurantRep(0, "0.1", "0.3");
    when(restaurantRepository.findById(0)).thenReturn(Optional.empty());
    RestaurantRep reps = restaurantService.updateRestaurant(0, rep);

  }

  @Test
  public void createRestaurantAllSucess(){
    List<RestaurantRep> restaurantReps = Arrays.asList(new RestaurantRep(0, "0.1", "0.3"),new RestaurantRep(1, "1.1", "1.3"));

    when(restaurantRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
    when(restaurantRepository.save(any(Restaurant.class))).thenReturn(null);

    restaurantService.createRestaurantAll(restaurantReps);

    verify(restaurantRepository, times(2)).save(any(Restaurant.class));

  }



  @Test
  public void createRestaurantAllErrorAlreadyCreated() {
    List<RestaurantRep> restaurantReps = Arrays.asList(new RestaurantRep(0, "0.1", "0.3"),new RestaurantRep(1, "1.1", "1.3"));

    when(restaurantRepository.findById(0)).thenReturn(Optional.empty());
    when(restaurantRepository.findById(1)).thenReturn(Optional.of(new Restaurant.Builder().build()));
    when(restaurantRepository.save(any(Restaurant.class))).thenReturn(null);

    restaurantService.createRestaurantAll(restaurantReps);

    verify(restaurantRepository, times(1)).save(any(Restaurant.class));

  }

  @Test(expected = BusinessException.class)
  public void createRestaurantAllErrorIdRequired() {

    List<RestaurantRep> restaurantReps = Arrays.asList(new RestaurantRep(null, "0.1", "0.3"),new RestaurantRep(1, "1.1", "1.3"));
    restaurantService.createRestaurantAll(restaurantReps);

  }

  @Test(expected = BusinessException.class)
  public void createRestaurantAllErrorLatRequired() {
    List<RestaurantRep> restaurantReps = Arrays.asList(new RestaurantRep(0, null, "0.3"),new RestaurantRep(1, "1.1", "1.3"));

    when(restaurantRepository.findById(0)).thenReturn(Optional.empty());

    restaurantService.createRestaurantAll(restaurantReps);

  }

  @Test(expected = BusinessException.class)
  public void createRestaurantAllErrorLonRequired() {
    List<RestaurantRep> restaurantReps = Arrays.asList(new RestaurantRep(0, "0.1", null),new RestaurantRep(1, "1.1", "1.3"));

    when(restaurantRepository.findById(0)).thenReturn(Optional.empty());

    restaurantService.createRestaurantAll(restaurantReps);

  }

}