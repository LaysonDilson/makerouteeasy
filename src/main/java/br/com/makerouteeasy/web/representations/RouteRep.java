package br.com.makerouteeasy.web.representations;

import java.util.ArrayList;
import java.util.List;

public class RouteRep {

  List<RouteMapRep> routes;

  public RouteRep() {
    this.routes=new ArrayList<>();
  }

  public RouteRep(List<RouteMapRep> routes) {
    this.routes = routes;
  }

  public List<RouteMapRep> getRoutes() {
    return routes;
  }

  public void setRoutes(List<RouteMapRep> routes) {
    this.routes = routes;
  }

  public void add(RouteMapRep routeMapRep){
    this.routes.add(routeMapRep);
  }
  public RouteMapRep last(){
    return this.routes.get(this.routes.size()-1);
  }
}
