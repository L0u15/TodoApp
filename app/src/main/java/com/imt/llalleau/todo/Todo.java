package com.imt.llalleau.todo;

import java.io.Serializable;
import java.util.List;

public class Todo implements Serializable {

    private String id;
    private String description;
    private boolean done;
    private String 	deadline;
    private String priority;
    private List<String> tags;

    public Todo(){

    }
    public Todo (String id, String desc, String d, List<String> tags){
        this.id = id;
        this.description = desc;
        this.done = false;
        this.deadline = d;
        this.tags = tags;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String desc) {
        this.description = desc;
    }
    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }
    public String getDeadline() {
        return deadline;
    }
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getPriority() {
        return this.priority;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String toString() {
        return description;
    }
}

