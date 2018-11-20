package br.com.makerouteeasy.domain.repositories;

import br.com.makerouteeasy.domain.entities.Order;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order,Integer> {

  List<Order> findAllByRestaurantIdOrderByDeliveryTime(Integer id);

  List<Order> findAllByRestaurantId(Integer id);

  List<Order> findAllByPickUpTimeBeforeAndAssigned(LocalDateTime now, boolean assign);
}
