package br.com.makerouteeasy.services.impl;

import static br.com.makerouteeasy.services.utils.DotUtils.calcDistance;
import static java.lang.Double.compare;
import static java.util.Comparator.comparing;

import br.com.makerouteeasy.domain.entities.Order;
import br.com.makerouteeasy.domain.entities.Restaurant;
import br.com.makerouteeasy.domain.repositories.OrderRepository;
import br.com.makerouteeasy.services.RoutesService;
import br.com.makerouteeasy.web.representations.RouteMapRep;
import br.com.makerouteeasy.web.representations.RouteRep;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoutesServiceImpl implements RoutesService {

  private final OrderRepository orderRepository;

  @Autowired
  public RoutesServiceImpl(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public RouteRep findRouteAvailableToDelivery() {
    RouteRep routeRep = new RouteRep();

    List<Order> orderList = orderRepository
        .findAllByPickUpTimeBeforeAndAssigned(LocalDateTime.now(), false);

    Map<Restaurant, List<Order>> restaurantListMap = orderList.stream()
        .collect(Collectors.groupingBy(Order::getRestaurant));

    for (Restaurant res : restaurantListMap.keySet()) {
      makeRouteMap(restaurantListMap.get(res), routeRep);
    }

    return routeRep;
  }

  /*
    Organiza uma lista de orders em Rota(s).
   */
  private void makeRouteMap(List<Order> orders, RouteRep routeRep) {
    orders.sort(comparing(Order::getPickUpTime));
    makeOrders(orders, routeRep, new ArrayList<>(), null);
  }

  /*
     Busquei utilizar um calculo simples, assim a partir da lista ordenada pelos pickups
     calculei as rotas baseadas se a distancia de um cliente y a partir do
     restaurante é maior doque a distancia ja traçada do restaurante a um client x - a
     distancia do cliente x ao client y.


     se ( distancia (res,y) > distancia ( res,x) - distancia ( x, y ) )
      adiciono a rota
     senao
      deixo para outra rota

   */
  private void makeOrders(List<Order> orders, RouteRep routeRep, ArrayList<Order> ordersToIgnore,
      Order last) {
    for (Iterator<Order> i = orders.iterator(); i.hasNext(); ) {
      Order o = i.next();
      if (ordersToIgnore.contains(o)) {
        continue;
      }
      if (last == null || routeRep.getRoutes().isEmpty()
          || routeRep.last().getOrders().size() == 3) {
        routeRep.add(new RouteMapRep(new ArrayList<>()));
        routeRep.last().getOrders().add(o.getId());
        ordersToIgnore.add(o);
        updateToAssigned(o);
        makeOrders(
            orders.stream()
                .sorted(comparing((Order k) -> calcDistance(o.getClient(), k.getClient()))
                    .thenComparing(k -> calcDistance(o.getClient(), k.getClient())))
                .collect(Collectors.toList()), routeRep, ordersToIgnore, o);
      } else {
        if (compare(calcDistance(o.getRestaurant(), o.getClient()),
            calcDistance(last.getRestaurant(), last.getClient()) - calcDistance(last.getClient(),
                o.getClient())) > 0) {
          routeRep.last().getOrders().add(o.getId());
          ordersToIgnore.add(o);
          updateToAssigned(o);
          last = o;
        }

      }
    }
  }

  private void updateToAssigned(Order o) {
    Order ob = new Order.Builder()
        .id(o.getId())
        .client(o.getClient())
        .restaurant(o.getRestaurant())
        .deliveryTime(o.getDeliveryTime())
        .pickUpTime(o.getPickUpTime())
        .assigned(true)
        .build();
    orderRepository.save(ob);
  }
}
