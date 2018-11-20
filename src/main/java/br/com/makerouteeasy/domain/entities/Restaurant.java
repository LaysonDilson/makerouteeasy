package br.com.makerouteeasy.domain.entities;

import br.com.makerouteeasy.commons.Dot;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Restaurant extends Dot {

  @Id
  private Integer id;

  private Restaurant() {
  }

  private Restaurant(Builder builder) {
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

    public Restaurant build(){
      return new Restaurant(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Restaurant that = (Restaurant) o;
    return Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}
