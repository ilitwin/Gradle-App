package com.app.core.api.controller;

import com.app.core.api.exception.AppInteractionException;
import com.app.core.api.exception.AppSchedulingException;
import com.app.core.api.exception.AppServiceException;
import com.app.core.api.service.utils.ClockUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Date;
import java.util.Objects;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * This method handles any validation error response
 *
 */
@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(AppInteractionException.class)
    public ResponseEntity<ErrorDetail> handleAppInteractionException(AppInteractionException ex) {
        ErrorDetail errorDetails = new ErrorDetail(ClockUtils.getNow(), "Action Request Failed", Objects.requireNonNull(ex).getMessage());
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), BAD_REQUEST);
    }

    @ExceptionHandler(AppServiceException.class)
    public ResponseEntity<ErrorDetail> handleAppServiceException(AppServiceException ex) {
        ErrorDetail errorDetails = new ErrorDetail(ClockUtils.getNow(), "Service Request Failed", Objects.requireNonNull(ex).getMessage());
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), BAD_REQUEST);
    }

    @ExceptionHandler(AppSchedulingException.class)
    public ResponseEntity<ErrorDetail> handleAppSchedulingException(AppSchedulingException ex) {
        ErrorDetail errorDetails = new ErrorDetail(ClockUtils.getNow(), "Scheduling Request Failed", Objects.requireNonNull(ex).getMessage());
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    public static class ErrorDetail {

        private Date timestamp;
        private String message;
        private String details;

    }
}
