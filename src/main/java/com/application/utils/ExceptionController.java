package com.application.utils;

import com.application.exceptions.ApplicationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

//With this class we get the information regarding the errors that can happen and more details information
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<Object> resourceNotFoundError(final ApplicationException e, final WebRequest request) {
        return handleExceptionInternal(e, applicationStatus(e), new HttpHeaders(), e.getExceptionType().getHttpStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException e, final HttpHeaders headers,
                                                                  final HttpStatus status, final WebRequest request) {
        return handleExceptionInternal(e, applicationStatus(e), headers, BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException e, final HttpHeaders headers,
                                                                  final HttpStatus status, final WebRequest request) {
        return handleExceptionInternal(e, applicationStatus(e), headers, BAD_REQUEST, request);
    }

    private ApplicationStatus applicationStatus(final ApplicationException e) {
        final String message = e.getExceptionType().getMessage() == null ? e.getClass().getSimpleName() : e.getExceptionType().getMessage();
        return new ApplicationStatus(e.getExceptionType().getHttpStatus().toString(), message);
    }

    private ApplicationStatus applicationStatus(final Exception e) {
        final String message = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
        return new ApplicationStatus(BAD_REQUEST.toString(), message);
    }
}
