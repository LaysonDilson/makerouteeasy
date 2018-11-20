package br.com.makerouteeasy.web.representations;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class OrderRep {

  private Integer id;
  private Integer restaurantId;
  private Integer clientId;
  @DateTimeFormat(pattern = "yyyyMMddThh:mm:ssZZ")
  private LocalDateTime pickup;
  @DateTimeFormat(pattern = "yyyyMMddThh:mm:ssZZ")
  private LocalDateTime delivery;

  public OrderRep() {
  }

  public OrderRep(Integer id, Integer restaurantId, Integer clientId, LocalDateTime pickup,
      LocalDateTime delivery) {
    this.id = id;
    this.restaurantId = restaurantId;
    this.clientId = clientId;
    this.pickup = pickup;
    this.delivery = delivery;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(Integer restaurantId) {
    this.restaurantId = restaurantId;
  }

  public Integer getClientId() {
    return clientId;
  }

  public void setClientId(Integer clientId) {
    this.clientId = clientId;
  }

  public LocalDateTime getPickup() {
    return pickup;
  }

  public void setPickup(LocalDateTime pickup) {
    this.pickup = pickup;
  }

  public LocalDateTime getDelivery() {
    return delivery;
  }

  public void setDelivery(LocalDateTime delivery) {
    this.delivery = delivery;
  }

}
