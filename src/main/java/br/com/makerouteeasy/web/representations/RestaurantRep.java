package br.com.makerouteeasy.web.representations;

public class RestaurantRep {

  private Integer id;
  private String lat;
  private String lon;

  public RestaurantRep() {
  }

  public RestaurantRep(Integer id, String lat, String lon) {
    this.id = id;
    this.lat = lat;
    this.lon = lon;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLat() {
    return lat;
  }

  public void setLat(String lat) {
    this.lat = lat;
  }

  public String getLon() {
    return lon;
  }

  public void setLon(String lon) {
    this.lon = lon;
  }
}
