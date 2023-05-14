package br.com.schedulebarber.scheduleBarber.Service;


import br.com.schedulebarber.scheduleBarber.Exception.SchedulingNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Model.Scheduling;
import br.com.schedulebarber.scheduleBarber.Repository.SchedulingRepository;
import br.com.schedulebarber.scheduleBarber.Util.PaginationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchedulingService {

    @Autowired
    public SchedulingRepository schedulingRepository;

    public Optional<Scheduling> findSchedulingById(Long id) throws SchedulingNotExistsException {
        Optional<Scheduling> optionalScheduling = schedulingRepository.findById(id);
        if(optionalScheduling.isPresent()){
            return schedulingRepository.findById(id);
        } else {
            throw new SchedulingNotExistsException();
        }
    }

    public Page<Scheduling> findAllScheduling(PaginationParams params) {
        Sort sort = params.getSortOrder().equalsIgnoreCase("asc") ?
                Sort.by(params.getSortProperty()).ascending() : Sort.by(params.getSortProperty()).descending();
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sort);
        Page<Scheduling> schedulings = schedulingRepository.findAll((Pageable) pageable);
        return schedulings;
    }
}
