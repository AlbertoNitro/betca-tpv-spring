package es.upm.miw.documents.core;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document
public class Scheduler {

    @Id
    private String id;

    private Date dateTime;

    private String title;

    private String description;

    public Scheduler(Date dateTime, String title, String description) {
        this.dateTime = dateTime;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scheduler scheduler = (Scheduler) o;
        return Objects.equals(id, scheduler.id) &&
                Objects.equals(dateTime, scheduler.dateTime) &&
                Objects.equals(title, scheduler.title) &&
                Objects.equals(description, scheduler.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, dateTime, title, description);
    }

    @Override
    public String toString() {
        return "Scheduler{" +
                "id='" + id + '\'' +
                ", dateTime=" + dateTime +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
