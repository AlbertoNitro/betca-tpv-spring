package es.upm.miw.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.upm.miw.documents.core.Scheduler;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class SchedulerMinimumDto {

    private String id;

    private Date dateTime;

    private String title;


    public SchedulerMinimumDto() {
        // Empty for framework
    }

    public SchedulerMinimumDto(Scheduler scheduler) {

    }

    public SchedulerMinimumDto(String id, Date dateTime, String title) {
        this.id = id;
        this.dateTime = dateTime;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SchedulerMinimumDto{" +
                "id='" + id + '\'' +
                ", dateTime=" + dateTime.toString() +
                ", title='" + title + '\'' +
                '}';
    }
}
