package br.com.makerouteeasy.services;

import br.com.makerouteeasy.web.representations.OrderRep;
import java.util.List;

public interface OrderService {

  void createOrder(OrderRep orderRep);

  OrderRep findOrderById(Integer id);

  List<OrderRep> findOrders(Integer id, boolean orderByDeliveryTime);

  void createOrderAll(List<OrderRep> orderReps);
}
