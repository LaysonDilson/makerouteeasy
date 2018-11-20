package br.com.makerouteeasy.domain.entities;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Order {

  @Id
  private Integer id;
  @DBRef
  private Client client;
  @DBRef
  private Restaurant restaurant;
  private boolean assigned;
  private LocalDateTime pickUpTime;
  private LocalDateTime deliveryTime;

  private Order() {
  }

  private Order(Builder builder) {
    this.id = builder.id;
    this.client = builder.client;
    this.restaurant = builder.restaurant;
    this.pickUpTime=builder.pickUpTime;
    this.deliveryTime =builder.deliveryTime;
    this.assigned = builder.assigned;
  }

  public Integer getId() {
    return id;
  }

  public Client getClient() {
    return client;
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public LocalDateTime getPickUpTime() {
    return pickUpTime;
  }

  public LocalDateTime getDeliveryTime() {
    return deliveryTime;
  }

  public boolean isAssigned() {
    return assigned;
  }

  public static class Builder {

    private Integer id;
    private Client client;
    private Restaurant restaurant;
    private LocalDateTime pickUpTime;
    private LocalDateTime deliveryTime;
    private boolean assigned;

    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Builder client(Client client) {
      this.client = client;
      return this;
    }

    public Builder restaurant(Restaurant restaurant) {
      this.restaurant = restaurant;
      return this;
    }

    public Builder pickUpTime(LocalDateTime pickUpTime) {
      this.pickUpTime = pickUpTime;
      return this;
    }
    public Builder deliveryTime(LocalDateTime deliveryTime) {
      this.deliveryTime = deliveryTime;
      return this;
    }
    public Builder assigned(boolean assigned){
      this.assigned=assigned;
      return this;
    }

    public Order build(){
      return new Order(this);
    }

  }

}
