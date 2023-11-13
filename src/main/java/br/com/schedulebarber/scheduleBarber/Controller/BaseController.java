package br.com.schedulebarber.scheduleBarber.Controller;

import br.com.schedulebarber.scheduleBarber.Util.PaginationParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface BaseController<T> {

    @GetMapping("/name/{name}")
    default ResponseEntity<?> findByName(@PathVariable("name") String name) throws Exception{
        return ResponseEntity.notFound().build();
    };

    @PostMapping("/findAll")
    default ResponseEntity<?> findAll(@RequestBody() PaginationParams params) throws Exception{
        return ResponseEntity.notFound().build();
    };

    @GetMapping("/id/{id}")
    ResponseEntity<?> findById(@PathVariable("id") Long id) throws Exception;

    @PutMapping("/update/{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody T body) throws Exception;

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) throws Exception;

    @GetMapping("/getscheduling/{id}")
    default ResponseEntity<?> getAgendamento(@PathVariable Long id) throws Exception{
        return ResponseEntity.notFound().build();
    };

}
