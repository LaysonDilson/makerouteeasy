package br.com.makerouteeasy.services.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.makerouteeasy.commons.BusinessException;
import br.com.makerouteeasy.domain.entities.Client;
import br.com.makerouteeasy.domain.entities.Order;
import br.com.makerouteeasy.domain.entities.Restaurant;
import br.com.makerouteeasy.domain.repositories.ClientRepository;
import br.com.makerouteeasy.domain.repositories.OrderRepository;
import br.com.makerouteeasy.domain.repositories.RestaurantRepository;
import br.com.makerouteeasy.web.representations.OrderRep;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

  @InjectMocks
  private OrderServiceImpl orderService;
  @Mock
  private OrderRepository orderRepository;
  @Mock
  private ClientRepository clientRepository;
  @Mock
  private RestaurantRepository restaurantRepository;

  @Test
  public void createOrderSuccess() {
    OrderRep rep = new OrderRep(0, 0, 0, LocalDateTime.now(), LocalDateTime.now().plusMinutes(20));
    when(orderRepository.findById(0)).thenReturn(Optional.empty());
    when(clientRepository.findById(0)).thenReturn(Optional.of(new Client.Builder().build()));
    when(restaurantRepository.findById(0))
        .thenReturn(Optional.of(new Restaurant.Builder().build()));

    orderService.createOrder(rep);

    verify(orderRepository, times(1)).save(any(Order.class));
  }

  @Test(expected = BusinessException.class)
  public void createOrderErrorAlreadyCreated() {
    OrderRep rep = new OrderRep(0, 0, 0, LocalDateTime.now(), LocalDateTime.now().plusMinutes(20));
    when(orderRepository.findById(0)).thenReturn(Optional.of(new Order.Builder().build()));
    orderService.createOrder(rep);
  }

  @Test(expected = BusinessException.class)
  public void createOrderErrorOrderIdRequired() {
    OrderRep rep = new OrderRep(null, 0, 0, LocalDateTime.now(),
        LocalDateTime.now().plusMinutes(20));
    orderService.createOrder(rep);
  }

  @Test(expected = BusinessException.class)
  public void createOrderErrorClientNotFound() {
    OrderRep rep = new OrderRep(0, 0, 0, LocalDateTime.now(), LocalDateTime.now().plusMinutes(20));
    when(orderRepository.findById(0)).thenReturn(Optional.empty());
    when(clientRepository.findById(0)).thenReturn(Optional.empty());
    orderService.createOrder(rep);
  }

  @Test(expected = BusinessException.class)
  public void createOrderErrorClientIdRequired() {
    OrderRep rep = new OrderRep(0, 0, null, LocalDateTime.now(),
        LocalDateTime.now().plusMinutes(20));
    when(orderRepository.findById(0)).thenReturn(Optional.empty());
    orderService.createOrder(rep);
  }

  @Test(expected = BusinessException.class)
  public void createOrderErrorRestaurantNotFound() {
    OrderRep rep = new OrderRep(0, 0, 0, LocalDateTime.now(), LocalDateTime.now().plusMinutes(20));
    when(orderRepository.findById(0)).thenReturn(Optional.empty());
    when(clientRepository.findById(0)).thenReturn(Optional.of(new Client.Builder().build()));
    when(restaurantRepository.findById(0)).thenReturn(Optional.empty());
    orderService.createOrder(rep);
  }

  @Test(expected = BusinessException.class)
  public void createOrderErrorRestauranIdRequired() {
    OrderRep rep = new OrderRep(0, null, 0, LocalDateTime.now(),
        LocalDateTime.now().plusMinutes(20));
    when(orderRepository.findById(0)).thenReturn(Optional.empty());
    when(clientRepository.findById(0)).thenReturn(Optional.of(new Client.Builder().build()));
    orderService.createOrder(rep);
  }

  @Test(expected = BusinessException.class)
  public void createOrderErrorPickUpRequired() {
    OrderRep rep = new OrderRep(0, 0, 0, null, LocalDateTime.now().plusMinutes(20));
    when(orderRepository.findById(0)).thenReturn(Optional.empty());
    when(clientRepository.findById(0)).thenReturn(Optional.of(new Client.Builder().build()));
    when(restaurantRepository.findById(0))
        .thenReturn(Optional.of(new Restaurant.Builder().build()));
    orderService.createOrder(rep);
  }

  @Test(expected = BusinessException.class)
  public void createOrderErrorDeliveryRequired() {
    OrderRep rep = new OrderRep(0, 0, 0, LocalDateTime.now(), null);
    when(orderRepository.findById(0)).thenReturn(Optional.empty());
    when(clientRepository.findById(0)).thenReturn(Optional.of(new Client.Builder().build()));
    when(restaurantRepository.findById(0))
        .thenReturn(Optional.of(new Restaurant.Builder().build()));
    orderService.createOrder(rep);
  }

  @Test
  public void createOrderAllSuccess() {
    List<OrderRep> rep = Arrays
        .asList(new OrderRep(0, 0, 0, LocalDateTime.now(), LocalDateTime.now().plusMinutes(20)));
    when(orderRepository.findById(0)).thenReturn(Optional.empty());
    when(clientRepository.findById(0)).thenReturn(Optional.of(new Client.Builder().build()));
    when(restaurantRepository.findById(0))
        .thenReturn(Optional.of(new Restaurant.Builder().build()));

    orderService.createOrderAll(rep);

    verify(orderRepository, times(1)).save(any(Order.class));
  }

  @Test(expected = BusinessException.class)
  public void createOrderAllErrorOrderIdRequired() {
    List<OrderRep> rep = Arrays
        .asList(new OrderRep(0, 0, 0, LocalDateTime.now(), LocalDateTime.now().plusMinutes(20)));
    orderService.createOrderAll(rep);
  }

  @Test(expected = BusinessException.class)
  public void createOrderAllErrorClientNotFound() {
    List<OrderRep> rep = Arrays
        .asList(new OrderRep(0, 0, 0, LocalDateTime.now(), LocalDateTime.now().plusMinutes(20)));
    when(orderRepository.findById(0)).thenReturn(Optional.empty());
    when(clientRepository.findById(0)).thenReturn(Optional.empty());
    orderService.createOrderAll(rep);
  }

  @Test(expected = BusinessException.class)
  public void createOrderAllErrorClientIdRequired() {
    List<OrderRep> rep = Arrays
        .asList(new OrderRep(0, 0, 0, LocalDateTime.now(), LocalDateTime.now().plusMinutes(20)));
    when(orderRepository.findById(0)).thenReturn(Optional.empty());
    orderService.createOrderAll(rep);
  }

  @Test(expected = BusinessException.class)
  public void createOrderAllErrorRestaurantNotFound() {
    List<OrderRep> rep = Arrays
        .asList(new OrderRep(0, 0, 0, LocalDateTime.now(), LocalDateTime.now().plusMinutes(20)));
    when(orderRepository.findById(0)).thenReturn(Optional.empty());
    when(clientRepository.findById(0)).thenReturn(Optional.of(new Client.Builder().build()));
    when(restaurantRepository.findById(0)).thenReturn(Optional.empty());
    orderService.createOrderAll(rep);
  }

  @Test(expected = BusinessException.class)
  public void createOrderAllErrorRestauranIdRequired() {
    List<OrderRep> rep = Arrays
        .asList(new OrderRep(0, 0, 0, LocalDateTime.now(), LocalDateTime.now().plusMinutes(20)));
    when(orderRepository.findById(0)).thenReturn(Optional.empty());
    when(clientRepository.findById(0)).thenReturn(Optional.of(new Client.Builder().build()));
    orderService.createOrderAll(rep);
  }

  @Test(expected = BusinessException.class)
  public void createOrderAllErrorPickUpRequired() {
    List<OrderRep> rep = Arrays
        .asList(new OrderRep(0, 0, 0, null, LocalDateTime.now().plusMinutes(20)));
    when(orderRepository.findById(0)).thenReturn(Optional.empty());
    when(clientRepository.findById(0)).thenReturn(Optional.of(new Client.Builder().build()));
    when(restaurantRepository.findById(0))
        .thenReturn(Optional.of(new Restaurant.Builder().build()));
    orderService.createOrderAll(rep);
  }

  @Test(expected = BusinessException.class)
  public void createOrderAllErrorDeliveryRequired() {
    List<OrderRep> rep = Arrays.asList(new OrderRep(0, 0, 0, LocalDateTime.now(), null));
    when(orderRepository.findById(0)).thenReturn(Optional.empty());
    when(clientRepository.findById(0)).thenReturn(Optional.of(new Client.Builder().build()));
    when(restaurantRepository.findById(0))
        .thenReturn(Optional.of(new Restaurant.Builder().build()));
    orderService.createOrderAll(rep);
  }

  @Test
  public void findOrderById() {
    when(orderRepository.findById(any(Integer.class))).thenReturn(Optional
        .of(new Order.Builder().restaurant(new Restaurant.Builder().build())
            .client(new Client.Builder().build()).build()));
    orderService.findOrderById(0);

  }
  @Test(expected = BusinessException.class)
  public void findOrderErrorNotFound() {
    when(orderRepository.findById(any(Integer.class))).thenReturn(Optional
        .empty());
    orderService.findOrderById(0);
  }
  @Test
  public void findOrdersSuccessWithOutParameters(){
    when(orderRepository.findAll()).thenReturn(Arrays.asList(new Order.Builder().restaurant(new Restaurant.Builder().build())
        .client(new Client.Builder().build()).build()));
    orderService.findOrders(null,false);
  }
  @Test
  public void findOrdersSuccessByRestaurant(){
    when(orderRepository.findAllByRestaurantId(0)).thenReturn(Arrays.asList(new Order.Builder().restaurant(new Restaurant.Builder().build())
        .client(new Client.Builder().build()).build()));
    orderService.findOrders(0,false);
  }
  @Test
  public void findOrdersSuccessByRestaurantOrderByDelivery(){
    when(orderRepository.findAllByRestaurantIdOrderByDeliveryTime(0)).thenReturn(Arrays.asList(new Order.Builder().restaurant(new Restaurant.Builder().build())
        .client(new Client.Builder().build()).build()));
    orderService.findOrders(0,true);
  }

}