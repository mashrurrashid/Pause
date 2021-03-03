package com.example.pause;

public class Note {

    private String title;
    private String description;
    private String datetime;
    private String dateOfTime;

    public Note() {

    }

    public Note(String title, String description, String datetime, String dateOfTime) {
        this.title = title;
        this.description = description;
        this.datetime = datetime;
        this.dateOfTime = dateOfTime;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDateOfTime() {
        return dateOfTime;
    }

    public void setDateOfTime(String dateOfTime) {
        this.dateOfTime = dateOfTime;
    }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", datetime='" + datetime + '\'' +
                ", dateOfTime='" + dateOfTime + '\'' +
                '}';
    }
}


