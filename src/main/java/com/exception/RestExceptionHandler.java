package com.exception;

import com.commons.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity handleBusinessException(BusinessException ex) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ErrorDTO.build().message(ex.getExceptionMessages().getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BusinessRuntimeException.class})
    public ResponseEntity handleBusinessRuntimeException(BusinessRuntimeException ex) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ErrorDTO.build().message(ex.getExceptionMessages().getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleTechnicalException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ErrorDTO.build().message(ExceptionMessages.GENERIC.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}