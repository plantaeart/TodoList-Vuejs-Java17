package com.tododo.tododo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {

    public int id;
    @JsonProperty("taskContent")
    private String _taskContent;
    protected Boolean isCompleted = false;
    @JsonProperty("subTasks")
    private SubTask[] _subTasks;

    /************************* CONSTRUCTORS *************************/ 

    public Task(int id, String taskContent, Boolean isCompleted, SubTask[] subTasks) {
        this.id = id;
        this._taskContent = taskContent;
        this.isCompleted = isCompleted;
        this._subTasks = subTasks;
    }

    public Task() {
    }

    /************************* GETTERS *************************/ 

    public int getId() {
        return id;
    }

    public SubTask[] getSubTasks() {
        return _subTasks;
    }

    public String getTaskContent() {
        return _taskContent;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    /************************* SETTERS *************************/ 

    public void setId(int id) {
        this.id = id;
    }

    public void setSubTasks(SubTask[] subTasks) {
        this._subTasks = subTasks;
    }

    public void setTaskContent(String taskContent) {
        this._taskContent = taskContent;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
