package com.tododo.tododo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubTask {

    @JsonProperty(value = "id", required = false)
    public int id;
    @JsonProperty("taskContent")
    private String _taskContent;
    @JsonProperty(value = "isCompleted", required = false)
    protected Boolean isCompleted = false;

    /************************* CONSTRUCTORS *************************/

    public SubTask(int id, String taskContent, Boolean isCompleted) {
        this.id = id;
        this._taskContent = taskContent;
        this.isCompleted = isCompleted;
    }

    public SubTask(String _taskContent) {
        this._taskContent = _taskContent;
    }

    public SubTask() {
    }

    @Override
    public String toString() {
        return "SubTask [id=" + id + ", _taskContent=" + _taskContent + ", isCompleted=" + isCompleted + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((_taskContent == null) ? 0 : _taskContent.hashCode());
        result = prime * result + ((isCompleted == null) ? 0 : isCompleted.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SubTask other = (SubTask) obj;
        if (id != other.id)
            return false;
        if (_taskContent == null) {
            if (other._taskContent != null)
                return false;
        } else if (!_taskContent.equals(other._taskContent))
            return false;
        if (isCompleted == null) {
            if (other.isCompleted != null)
                return false;
        } else if (!isCompleted.equals(other.isCompleted))
            return false;
        return true;
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
