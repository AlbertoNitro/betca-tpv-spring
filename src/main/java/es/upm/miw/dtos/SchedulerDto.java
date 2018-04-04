package es.upm.miw.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.util.concurrent.AbstractScheduledService;
import es.upm.miw.documents.core.Scheduler;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class SchedulerDto extends SchedulerMinimumDto {

    private String description;

    public SchedulerDto() {
        //Empty for framework
    }

    public SchedulerDto(Scheduler scheduler) {
        super(scheduler.getId(), scheduler.getDateTime(), scheduler.getTitle());
        this.description = scheduler.getDescription();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SchedulerDto{" +
                "id='" + super.getId() + '\'' +
                ", dateTime=" + super.getDateTime().toString() +
                ", title='" + super.getTitle() + '\'' +
                "description='" + description + '\'' +
                '}';
    }


}
