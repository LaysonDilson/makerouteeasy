package br.com.makerouteeasy.web.representations;

import java.util.List;

public class RouteMapRep {

  List<Integer> orders;

  public RouteMapRep() {
  }

  public RouteMapRep(List<Integer> orders) {
    this.orders = orders;
  }

  public List<Integer> getOrders() {
    return orders;
  }

  public void setOrders(List<Integer> orders) {
    this.orders = orders;
  }
}
