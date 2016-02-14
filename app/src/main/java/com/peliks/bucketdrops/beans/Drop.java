package com.peliks.bucketdrops.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Felix on 2/13/2016.
 */
public class Drop extends RealmObject {

    private String goal;
    @PrimaryKey
    private long addedDate;
    private long dueDate;
    private boolean completed;

    public Drop(String goal, long addedDate, long dueDate, boolean completed) {
        this.goal = goal;
        this.addedDate = addedDate;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public Drop() {
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public long getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(long addedDate) {
        this.addedDate = addedDate;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
