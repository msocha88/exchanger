package pl.exchanger.exchanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.exchanger.exchanger.model.logs.Log;

public interface LogRepository extends JpaRepository<Log,Long> {



}
