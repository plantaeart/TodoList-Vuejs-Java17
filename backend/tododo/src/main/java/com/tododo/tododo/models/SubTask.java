package com.tododo.tododo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubTask {
    public int id;
    @JsonProperty("taskContent")
    private String _taskContent;
    protected Boolean isCompleted = false;
    
    /************************* CONSTRUCTORS *************************/

    public SubTask(int id, String taskContent, Boolean isCompleted) {
        this.id = id;
        this._taskContent = taskContent;
        this.isCompleted = isCompleted;
    }

    /************************* GETTERS *************************/

    public int getId() {
        return id;
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

    public void setTaskContent(String taskContent) {
        this._taskContent = taskContent;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    
}
