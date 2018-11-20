package br.com.makerouteeasy.commons;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

  private BusinessError error;
  private HttpStatus status;

  public BusinessException(BusinessError error, HttpStatus status) {
    this.error = error;
    this.status = status;
  }
  public BusinessError getError() {
    return error;
  }
  public HttpStatus getStatus() {
    return status;
  }
}

