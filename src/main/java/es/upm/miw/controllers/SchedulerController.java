package es.upm.miw.controllers;

import es.upm.miw.documents.core.Scheduler;
import es.upm.miw.dtos.SchedulerDto;
import es.upm.miw.repositories.core.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SchedulerController {

    @Autowired
    private SchedulerRepository schedulerRepository;

    public void create(SchedulerDto schedulerDto) {
        Scheduler scheduler = new Scheduler(schedulerDto.getDateTime(), schedulerDto.getTitle(), schedulerDto.getDescription());
        this.schedulerRepository.save(scheduler);
    }

    public List<SchedulerDto> readAll() {
        Sort sortDateTimeAsc = new Sort(Sort.Direction.ASC, "dateTime");
        List<Scheduler> schedulerList = this.schedulerRepository.findAll(sortDateTimeAsc);
        List<SchedulerDto> schedulerDtoList = new ArrayList<SchedulerDto>();

        for(Scheduler s: schedulerList){
            schedulerDtoList.add(new SchedulerDto(s));
        }

        return schedulerDtoList;
    }

    public void deleteAll() {
        this.schedulerRepository.deleteAll();
    }
}
