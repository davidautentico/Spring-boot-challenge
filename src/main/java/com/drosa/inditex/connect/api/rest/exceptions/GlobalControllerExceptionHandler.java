package com.drosa.inditex.connect.api.rest.exceptions;

import com.drosa.inditex.connect.domain.exceptions.InditexConnectServicePriceNotFoundException;
import com.drosa.inditex.connect.domain.exceptions.InditexConnectServiceRepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public final class GlobalControllerExceptionHandler {

  @ExceptionHandler(InditexConnectServicePriceNotFoundException.class)
  public ResponseEntity<?> handleInditexConnectServicePriceNotFoundException(InditexConnectServicePriceNotFoundException exception, WebRequest request) {
    return new ResponseEntity(new InditexConnectServiceErrorResponse(request.getParameterMap(), "error"), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InditexConnectServiceRepositoryException.class)
  public ResponseEntity<?> handleInditexConnectServiceRepositoryException(InditexConnectServiceRepositoryException exception, WebRequest request) {
    return new ResponseEntity(new InditexConnectServiceErrorResponse(request.getParameterMap(), "error"), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception exception, WebRequest request) {
    log.error("Exception received, message: <{}>", exception.getMessage());
    return new ResponseEntity(new InditexConnectServiceErrorResponse(request.getParameterMap(), "error"), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
