package swin.swe4006.c6g1.controller.handler;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import swin.swe4006.c6g1.dto.ResponseRestDto;
import swin.swe4006.c6g1.exception.AppEntityNotFound;

@RestControllerAdvice
@AllArgsConstructor
public class ApiExceptionHandler {
    @ExceptionHandler(AppEntityNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseRestDto handleUsernameNotFound(AppEntityNotFound ex) {
        return ResponseRestDto.builder().message(ex.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseRestDto handleUsernameNotFound(Exception ex) {
        return ResponseRestDto.builder().message(ex.getMessage()).build();
    }

}
