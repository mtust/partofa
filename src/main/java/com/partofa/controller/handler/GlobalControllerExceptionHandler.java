package com.partofa.controller.handler;

import com.partofa.dto.RestMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.spi.LocationAwareLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound() {
        return "index.html";
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<RestMessageDTO> exceptionInternal(Exception e) {
        log.error("Internal server exception: ", e);
        return new ResponseEntity<>(new RestMessageDTO("There is some error. Please try again.", false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<RestMessageDTO> exceptionInternal(RuntimeException e) {
        log.error(this.getClass().getName(), LocationAwareLogger.ERROR_INT, e);
        return new ResponseEntity<>(new RestMessageDTO(e.getMessage(), false), HttpStatus.valueOf(500));
    }
}
