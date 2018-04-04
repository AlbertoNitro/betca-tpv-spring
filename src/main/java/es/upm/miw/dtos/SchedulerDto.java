package es.upm.miw.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.util.concurrent.AbstractScheduledService;
import es.upm.miw.documents.core.Scheduler;

import java.text.SimpleDateFormat;
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
        String dateTime = "";

        if (super.getDateTime() != null) {
            dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(super.getDateTime().getTime());
        }

        return "SchedulerDto{" +
                "id='" + super.getId() + '\'' +
                ", dateTime=" + dateTime +
                ", title='" + super.getTitle() + '\'' +
                "description='" + description + '\'' +
                '}';
    }


}
