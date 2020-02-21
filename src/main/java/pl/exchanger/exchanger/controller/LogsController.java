package pl.exchanger.exchanger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.exchanger.exchanger.model.logs.Log;
import pl.exchanger.exchanger.repository.LogRepository;

import java.util.List;

@RequestMapping("/logs/")
@RestController
public class LogsController {

    @Autowired
    LogRepository logRepository;

    @GetMapping("/")
    public List<Log> printAllLogs() {
        return logRepository.findAll();
    }


}
