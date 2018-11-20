package br.com.makerouteeasy.commons;

public class BusinessError {

  public static final BusinessError CLIENT_ALREADY_CREATED = BusinessError
      .of("1001", "Client already created");
  public static final BusinessError CLIENT_NOT_FOUND = BusinessError
      .of("1002", "Client not found");
  public static final BusinessError CLIENT_ID_REQUIRED = BusinessError
      .of("1003", "Client id required");
  public static final BusinessError CLIENT_LAT_REQUIRED = BusinessError
      .of("1005", "Client lat required");
  public static final BusinessError CLIENT_LON_REQUIRED = BusinessError
      .of("1006", "Client lon required");

  public static final BusinessError RESTAURANT_ALREADY_CREATED = BusinessError
      .of("1005", "Restaurant already created");
  public static final BusinessError RESTAURANT_NOT_FOUND = BusinessError
      .of("1006", "Restaurant not found");
  public static final BusinessError RESTAURANT_ID_REQUIRED = BusinessError
      .of("1007", "Restaurant id required");
  public static final BusinessError RESTAURANT_LAT_REQUIRED = BusinessError
      .of("1008", "Restaurant lat required");
  public static final BusinessError RESTAURANT_LON_REQUIRED = BusinessError
      .of("1009", "Restaurant lon required");

  public static final BusinessError ORDER_ALREADY_CREATED = BusinessError
      .of("1010", "Order already created");
  public static final BusinessError ORDER_NOT_FOUND = BusinessError
      .of("1011", "Order not found");
  public static final BusinessError ORDER_ID_REQUIRED = BusinessError
      .of("1007", "Order id required");
  public static final BusinessError ORDER_PICKUP_REQUIRED = BusinessError
      .of("1008", "Order pickup required");
  public static final BusinessError ORDER_DELIVERY_REQUIRED = BusinessError
      .of("1009", "Order delivery required");

  private String code;
  private String details;

  private BusinessError(String code, String details) {
    this.code = code;
    this.details = details;
  }

  public String getCode() {
    return code;
  }

  public String getDetails() {
    return details;
  }

  private static BusinessError of(String s, String sa) {
    return new BusinessError(s, sa);
  }


}
