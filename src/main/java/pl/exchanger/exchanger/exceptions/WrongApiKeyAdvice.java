package pl.exchanger.exchanger.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.exchanger.exchanger.model.logs.Log;
import pl.exchanger.exchanger.repository.LogRepository;


@ControllerAdvice
public class WrongApiKeyAdvice {
    @Autowired
    LogRepository logRepository;


    @ResponseBody
    @ExceptionHandler(WrongApiKeyException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String wrongApiKeyHandler(WrongApiKeyException ex) {

        logRepository.save(new Log(
                ex.getMessage()));

        return ex.getMessage();
    }
}
