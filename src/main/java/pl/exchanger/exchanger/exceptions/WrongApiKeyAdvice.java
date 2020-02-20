package pl.exchanger.exchanger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice

public class WrongApiKeyAdvice {

    @ResponseBody
    @ExceptionHandler(WrongApiKeyException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String wrongApiKeyHandler(WrongApiKeyException ex) {

        return ex.getMessage();
    }
}
