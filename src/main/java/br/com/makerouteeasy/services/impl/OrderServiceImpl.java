package br.com.makerouteeasy.services.impl;

import br.com.makerouteeasy.commons.BusinessError;
import br.com.makerouteeasy.commons.BusinessException;
import br.com.makerouteeasy.domain.entities.Client;
import br.com.makerouteeasy.domain.entities.Order;
import br.com.makerouteeasy.domain.entities.Restaurant;
import br.com.makerouteeasy.domain.repositories.ClientRepository;
import br.com.makerouteeasy.domain.repositories.OrderRepository;
import br.com.makerouteeasy.domain.repositories.RestaurantRepository;
import br.com.makerouteeasy.services.OrderService;
import br.com.makerouteeasy.web.representations.OrderRep;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final ClientRepository clientRepository;
  private final RestaurantRepository restaurantRepository;

  @Autowired
  public OrderServiceImpl(OrderRepository orderRepository,
      ClientRepository clientRepository,
      RestaurantRepository restaurantRepository) {
    this.orderRepository = orderRepository;
    this.clientRepository = clientRepository;
    this.restaurantRepository = restaurantRepository;
  }


  @Override
  @Transactional
  public void createOrder(OrderRep orderRep) {
    verifyOrder(orderRep);
    Restaurant res = restaurantRepository.findById(orderRep.getRestaurantId()).get();
    Client cl = clientRepository.findById(orderRep.getClientId()).get();

    Order order = new Order.Builder()
        .id(orderRep.getId())
        .restaurant(res)
        .client(cl)
        .deliveryTime(orderRep.getDelivery())
        .pickUpTime(orderRep.getPickup())
        .build();
    orderRepository.save(order);
  }

  @Override
  @Transactional
  public void createOrderAll(List<OrderRep> orderReps) {
    for (OrderRep rep : orderReps) {
      try {
        createOrder(rep);
      } catch (BusinessException e) {
        if (!e.getError().equals(BusinessError.ORDER_ALREADY_CREATED)) {
          throw e;
        }
      }
    }
  }
  private void verifyOrder(OrderRep orderRep) {

    if (orderRep.getId() != null) {
      Optional<Order> order = orderRepository.findById(orderRep.getId());
      if (order.isPresent()) {
        throw new BusinessException(BusinessError.ORDER_ALREADY_CREATED, HttpStatus.BAD_REQUEST);
      }
    } else {
      throw new BusinessException(BusinessError.ORDER_ID_REQUIRED, HttpStatus.BAD_REQUEST);
    }

    if (orderRep.getClientId() != null) {
      Optional<Client> client = clientRepository.findById(orderRep.getClientId());
      if (client.isEmpty()) {
        throw new BusinessException(BusinessError.CLIENT_NOT_FOUND, HttpStatus.BAD_REQUEST);
      }

    } else {
      throw new BusinessException(BusinessError.CLIENT_ID_REQUIRED, HttpStatus.BAD_REQUEST);
    }

    if (orderRep.getRestaurantId() != null) {
      Optional<Restaurant> restaurant = restaurantRepository.findById(orderRep.getRestaurantId());
      if (restaurant.isEmpty()) {
        throw new BusinessException(BusinessError.RESTAURANT_NOT_FOUND, HttpStatus.BAD_REQUEST);
      }
    } else {
      throw new BusinessException(BusinessError.RESTAURANT_ID_REQUIRED, HttpStatus.BAD_REQUEST);
    }

    if (orderRep.getDelivery() == null) {
      throw new BusinessException(BusinessError.ORDER_DELIVERY_REQUIRED, HttpStatus.BAD_REQUEST);
    }

    if (orderRep.getPickup() == null) {
      throw new BusinessException(BusinessError.ORDER_PICKUP_REQUIRED, HttpStatus.BAD_REQUEST);
    }


  }

  @Override
  public OrderRep findOrderById(Integer id) {
    Optional<Order> orderOp = orderRepository.findById(id);
    if (orderOp.isEmpty()) {
      throw new BusinessException(BusinessError.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND);
    } else {
      Order order = orderOp.get();
      return new OrderRep(order.getId(), order.getRestaurant().getId(), order.getClient().getId(),
          order.getPickUpTime(), order.getDeliveryTime());
    }
  }

  @Override
  public List<OrderRep> findOrders(Integer restaurantId, boolean orderByDeliveryTime) {
    List<Order> orders;
    if (restaurantId != null) {
      orders = orderByDeliveryTime ?
          orderRepository.findAllByRestaurantIdOrderByDeliveryTime(restaurantId)
          : orderRepository.findAllByRestaurantId(restaurantId);
    } else {
      orders = orderRepository.findAll();
    }

    return orders.stream().map(k -> new OrderRep(k.getId(), k.getRestaurant().getId(), k.getClient().getId(),
        k.getPickUpTime(), k.getDeliveryTime())).collect(Collectors.toList());
  }
}

