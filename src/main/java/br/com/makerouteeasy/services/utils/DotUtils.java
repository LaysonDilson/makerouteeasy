package br.com.makerouteeasy.services.utils;

import br.com.makerouteeasy.commons.Dot;
import java.time.LocalDateTime;

public class DotUtils {

  private static final double DISTANCE_TO_TRAVEL = 0.1d;
  private static final double TIME_TO_TRAVEL = 300d;

  public static double calcDistance(Dot x, Dot y) {
    return Math.sqrt(Math.pow(y.latitude - x.latitude, 2) + Math.pow(y.longitude - x.longitude, 2));
  }

  public static long timeToDeliveryInSec(Dot x, Dot y) {
    double distance = calcDistance(x, y);
    return (long) ((distance / DISTANCE_TO_TRAVEL) * TIME_TO_TRAVEL);
  }

}
