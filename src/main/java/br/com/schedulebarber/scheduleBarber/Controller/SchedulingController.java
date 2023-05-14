package br.com.schedulebarber.scheduleBarber.Controller;


import br.com.schedulebarber.scheduleBarber.Model.Scheduling;
import br.com.schedulebarber.scheduleBarber.Service.SchedulingService;
import br.com.schedulebarber.scheduleBarber.Util.PaginationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
