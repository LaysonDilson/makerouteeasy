package br.com.makerouteeasy.domain.entities;

import br.com.makerouteeasy.commons.Dot;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Client extends Dot {

  @Id
  private Integer id;

  private Client() {
  }

  private Client(Builder builder) {
    this.id = builder.id;
    this.latitude = builder.latitude;
    this.longitude = builder.longitude;
  }

  public Integer getId() {
    return id;
  }

  public Float getLatitude() {
    return latitude;
  }

  public Float getLongitude() {
    return longitude;
  }

  public static class Builder {

    private Integer id;
    private Float latitude;
    private Float longitude;

    public Builder id(Integer id){
      this.id = id;
      return this;
    }
    public Builder latitude(Float latitude){
      this.latitude = latitude;
      return this;
    }
    public Builder longitude(Float longitude){
      this.longitude = longitude;
      return this;
    }

    public Client build(){
      return new Client(this);
    }
  }

}
