package br.com.schedulebarber.scheduleBarber.Controller;


import br.com.schedulebarber.scheduleBarber.Exception.*;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Model.Client;
import br.com.schedulebarber.scheduleBarber.Model.Scheduling;
import br.com.schedulebarber.scheduleBarber.Service.SchedulingService;
import br.com.schedulebarber.scheduleBarber.Util.PaginationParams;
import br.com.schedulebarber.scheduleBarber.Util.SchedulingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/scheduling")
public class SchedulingController implements BaseController<SchedulingRequest> {

    @Autowired
    private SchedulingService schedulingService;

    @PostMapping("/findAllScheduling")
    public ResponseEntity<?>  findAll(@RequestBody PaginationParams params){
        Page<Scheduling> schedulings = schedulingService.findAllScheduling(params);
        return ResponseEntity.ok(schedulings);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?>  findById(@PathVariable("id") Long id) throws SchedulingNotExistsException {
        Optional<Scheduling> scheduling = schedulingService.findSchedulingById(id);
        return ResponseEntity.ok(scheduling);
    }

    @PostMapping("/createScheduling")
    public ResponseEntity<?> save (@RequestBody SchedulingRequest scheduling) throws BarberNotExistsException, ClientNotExistsException, BarberDoesNotHaveService, SchedulingConflictException {
        Scheduling schedulingCreate = schedulingService.createScheduling(scheduling);
        return ResponseEntity.ok(schedulingCreate);
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody SchedulingRequest schedulingRequest) throws SchedulingNotExistsException, BarberNotExistsException, ClientNotExistsException, BarberDoesNotHaveService, SchedulingConflictException {
        Scheduling schedulingUpdated = schedulingService.updateScheduling(id, schedulingRequest);
        return ResponseEntity.ok().body(schedulingUpdated);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) throws SchedulingNotExistsException {
        Scheduling scheduling = schedulingService.schedulingDelete(id);
        return ResponseEntity.ok().body(scheduling);
    }

}
