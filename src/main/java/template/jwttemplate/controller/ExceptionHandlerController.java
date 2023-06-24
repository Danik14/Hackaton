package template.jwttemplate.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import template.jwttemplate.dto.ErrorDto;
import template.jwttemplate.exception.UserAlreadyExistsException;
import template.jwttemplate.exception.UserNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerController {
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorDto> handleIllegalArgument(HttpServletRequest request, IllegalArgumentException e) {
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(ErrorDto.builder()
                                                .timestamp(new Date())
                                                .status(HttpStatus.BAD_REQUEST.value())
                                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                                .path(request.getServletPath())
                                                .message(e.getMessage())
                                                .build());
        }

        @ExceptionHandler({ UserNotFoundException.class })
        public ResponseEntity<?> handleNotFound(HttpServletRequest request, RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                ErrorDto.builder()
                                                .timestamp(new Date())
                                                .status(HttpStatus.NOT_FOUND.value())
                                                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                                                .path(request.getServletPath())
                                                .message(e.getMessage())
                                                .build());
        }

        @ExceptionHandler({ UserAlreadyExistsException.class })
        public ResponseEntity<?> handleAlreadyExists(HttpServletRequest request, RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                ErrorDto.builder()
                                                .timestamp(new Date())
                                                .status(HttpStatus.BAD_REQUEST.value())
                                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                                .path(request.getServletPath())
                                                .message(e.getMessage())
                                                .build());
        }
}