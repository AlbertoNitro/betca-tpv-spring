package es.upm.miw.resources;

import es.upm.miw.controllers.SchedulerController;
import es.upm.miw.dtos.SchedulerDto;
import es.upm.miw.resources.exceptions.SchedulerIdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(SchedulerResource.SCHEDULER)
public class SchedulerResource {
    public static final String SCHEDULER = "/schedule";

    public static final String ID_ID = "/{id}";


    @Autowired
    private SchedulerController schedulerController;

    @GetMapping(value = ID_ID)
    public SchedulerDto readScheduller(@PathVariable String id) throws SchedulerIdNotFoundException {
        return this.schedulerController.read(id).orElseThrow(()-> new SchedulerIdNotFoundException(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public SchedulerDto createScheduler(@RequestBody SchedulerDto schedulerDto){
        return this.schedulerController.create(schedulerDto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<SchedulerDto> readAllScheduler(){
        return this.schedulerController.readAll();
    }



}
