package br.com.makerouteeasy.services.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.makerouteeasy.domain.entities.Client;
import br.com.makerouteeasy.domain.entities.Order;
import br.com.makerouteeasy.domain.entities.Restaurant;
import br.com.makerouteeasy.domain.repositories.OrderRepository;
import br.com.makerouteeasy.services.utils.DotUtils;
import br.com.makerouteeasy.web.representations.ClientRep;
import br.com.makerouteeasy.web.representations.OrderRep;
import br.com.makerouteeasy.web.representations.RestaurantRep;
import br.com.makerouteeasy.web.representations.RouteMapRep;
import br.com.makerouteeasy.web.representations.RouteRep;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RoutesServiceImplTest {

  @InjectMocks
  private RoutesServiceImpl routesService;

  @Mock
  private OrderRepository orderRepository;

  private List<Client> clients;
  private List<Restaurant> restaurants;
  private List<Order> orders;

  @Before
  public void Setup() {
    clients = new ArrayList<>();
    restaurants = new ArrayList<>();
    orders = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      clients.add(new Client.Builder().id(i).latitude((float) (new Random().nextInt(10) / (10.0)))
          .longitude((float) (new Random().nextInt(10) / (10.0))).build());
      restaurants
          .add(new Restaurant.Builder().id(i).latitude((float) (new Random().nextInt(10) / (10.0)))
              .longitude((float) (new Random().nextInt(10) / (10.0))).build());
    }

    for (int i = 0; i < 30; i++) {
      Client cl = clients.get(new Random().nextInt(10));
      Restaurant restaurant = restaurants.get(new Random().nextInt(10));
      LocalDateTime pickup = LocalDateTime.now().minusMinutes(new Random().nextInt(40));
      Order ob = new Order.Builder()
          .id(i)
          .client(cl)
          .restaurant(restaurant)
          .pickUpTime(pickup)
          .deliveryTime(pickup.plusSeconds(DotUtils.timeToDeliveryInSec(restaurant, cl)))
          .assigned(false)
          .build();
      orders.add(ob);
    }

  }

  @Test
  public void findRouteAvailableToDelivery() {
    when(orderRepository
        .findAllByPickUpTimeBeforeAndAssigned(any(LocalDateTime.class), any(boolean.class)))
        .thenReturn(orders);
    when(orderRepository.save(any(Order.class))).thenReturn(new Order.Builder().build());
    RouteRep routeRep = routesService.findRouteAvailableToDelivery();

    int count = 0;
    for (RouteMapRep r : routeRep.getRoutes()) {
      count += r.getOrders().size();
    }
    Assert.assertEquals(30, count);
  }

  /*
    Gera massa para restfull só mudar para public
   */
  @Test
  public void makeRestFullJson() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
    try {
      mapper.writeValue(Paths.get("massa/clients.json").toFile(),
          clients.stream().map(k -> new ClientRep(k.getId(), String.valueOf(k.getLatitude()),
              String.valueOf(k.getLongitude()))).collect(Collectors.toList()));
      mapper.writeValue(Paths.get("massa/restaurants.json").toFile(), restaurants.stream()
          .map(k -> new RestaurantRep(k.getId(), String.valueOf(k.getLatitude()),
              String.valueOf(k.getLongitude()))).collect(Collectors.toList()));
      mapper.writeValue(Paths.get("massa/orders.json").toFile(), orders.stream()
          .map(k -> new OrderRep(k.getId(), k.getRestaurant().getId(), k.getClient().getId(),
              k.getPickUpTime(), k.getDeliveryTime())).collect(Collectors.toList()));
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


}