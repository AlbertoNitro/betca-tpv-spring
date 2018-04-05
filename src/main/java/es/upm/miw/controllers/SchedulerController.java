package es.upm.miw.controllers;

import es.upm.miw.documents.core.Scheduler;
import es.upm.miw.dtos.SchedulerDto;
import es.upm.miw.repositories.core.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SchedulerController {

    @Autowired
    private SchedulerRepository schedulerRepository;

    public SchedulerDto create(SchedulerDto schedulerDto) {
        Scheduler scheduler = new Scheduler(schedulerDto.getDateTime(), schedulerDto.getTitle(), schedulerDto.getDescription());
        scheduler = this.schedulerRepository.save(scheduler);
        return new SchedulerDto(scheduler);
    }

    public List<SchedulerDto> readAll() {
        Sort sortDateTimeAsc = new Sort(Sort.Direction.ASC, "dateTime");
        List<Scheduler> schedulerList = this.schedulerRepository.findAll(sortDateTimeAsc);
        List<SchedulerDto> schedulerDtoList = new ArrayList<SchedulerDto>();

        for (Scheduler s : schedulerList) {
            schedulerDtoList.add(new SchedulerDto(s));
        }

        return schedulerDtoList;
    }

    public void deleteAll() {
        this.schedulerRepository.deleteAll();
    }

    public Optional<SchedulerDto> read(String id) {
        Scheduler scheduler = this.schedulerRepository.findOne(id);
        if (scheduler != null) {
            return Optional.of(new SchedulerDto(scheduler));
        } else {
            return Optional.empty();
        }
    }

    public void delete(String id) {
        this.schedulerRepository.delete(id);
    }
}
