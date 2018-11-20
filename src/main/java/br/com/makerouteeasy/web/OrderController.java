package br.com.makerouteeasy.web;

import br.com.makerouteeasy.services.OrderService;
import br.com.makerouteeasy.web.representations.OrderRep;
import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity create(@RequestBody OrderRep orderRep) {
    orderService.createOrder(orderRep);
    return new ResponseEntity(HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<OrderRep> findOrderById(@PathVariable("id") Integer id) {
    return new ResponseEntity<>(orderService.findOrderById(id), HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<List<OrderRep>> findOrderByRestaurant(
      @PathParam("restaurantId") Integer restaurantId,
      @PathParam("orderByDeliveryTime") boolean orderByDeliveryTime) {
    return new ResponseEntity<>(orderService.findOrders(restaurantId, orderByDeliveryTime),
        HttpStatus.OK);
  }
  /*
   Bonus: para adicinar uma massa de teste mais facilmente
  */
  @PostMapping("/list")
  public ResponseEntity createList(@RequestBody List<OrderRep> orderReps) {
    orderService.createOrderAll(orderReps);
    return new ResponseEntity(HttpStatus.CREATED);
  }
}
