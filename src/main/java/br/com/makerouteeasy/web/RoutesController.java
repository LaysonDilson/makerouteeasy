package br.com.makerouteeasy.web;

import br.com.makerouteeasy.services.RoutesService;
import br.com.makerouteeasy.web.representations.RouteRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/routes")
public class RoutesController {

  private final RoutesService routesService;

  @Autowired
  public RoutesController(RoutesService routesService) {
    this.routesService = routesService;
  }

  @GetMapping(value = "")
  public ResponseEntity<RouteRep> findRouteAvailableToDelivery() {
    return new ResponseEntity<RouteRep>(routesService.findRouteAvailableToDelivery(), HttpStatus.OK);
  }

}
