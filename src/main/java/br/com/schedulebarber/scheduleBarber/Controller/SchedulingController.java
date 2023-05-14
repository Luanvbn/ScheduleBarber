package br.com.schedulebarber.scheduleBarber.Controller;


import br.com.schedulebarber.scheduleBarber.Exception.*;
import br.com.schedulebarber.scheduleBarber.Model.Scheduling;
import br.com.schedulebarber.scheduleBarber.Service.SchedulingService;
import br.com.schedulebarber.scheduleBarber.Util.PaginationParams;
import br.com.schedulebarber.scheduleBarber.Util.SchedulingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {

    @Autowired
    private SchedulingService schedulingService;

    @PostMapping("/findAllScheduling")
    public ResponseEntity<Page<Scheduling>> findAllScheduling(@RequestBody PaginationParams params){
        Page<Scheduling> schedulings = schedulingService.findAllScheduling(params);
        return ResponseEntity.ok(schedulings);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Scheduling>> findById(@PathVariable("id") Long id) throws SchedulingNotExistsException {
        Optional<Scheduling> scheduling = schedulingService.findSchedulingById(id);
        return ResponseEntity.ok(scheduling);
    }

    @PostMapping("/createScheduling")
    public ResponseEntity<?> saveSchedulling (@RequestBody SchedulingRequest scheduling) throws AccessAlreadyExistsException, BarberNotExistsException, ClientNotExistsException, BarberDoesNotHaveService {
        Scheduling schedulingCreate = schedulingService.createScheduling(scheduling);
        return ResponseEntity.ok(schedulingCreate);
    }

}
