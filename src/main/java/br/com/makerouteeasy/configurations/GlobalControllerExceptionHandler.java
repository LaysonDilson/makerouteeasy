package br.com.makerouteeasy.configurations;

import br.com.makerouteeasy.commons.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value
      = {BusinessException.class})
  protected ResponseEntity<Object> handleConflict(
      BusinessException ex, WebRequest request) {
    return handleExceptionInternal(ex, ex.getError(),
        new HttpHeaders(), ex.getStatus()!=null?ex.getStatus(): HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
