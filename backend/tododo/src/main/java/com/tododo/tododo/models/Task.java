package com.tododo.tododo.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {

    public int id;
    @JsonProperty("taskContent")
    private String _taskContent;
    protected Boolean isCompleted = false;
    @JsonProperty("subTasks")
    private List<SubTask> _subTasks;

    /************************* CONSTRUCTORS *************************/

    public Task(int id, String taskContent, Boolean isCompleted, List<SubTask> subTasks) {
        this.id = id;
        this._taskContent = taskContent;
        this.isCompleted = isCompleted;
        this._subTasks = subTasks;
    }

    public Task() {
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", _taskContent=" + _taskContent + ", isCompleted=" + isCompleted + ", _subTasks="
                + _subTasks + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((_taskContent == null) ? 0 : _taskContent.hashCode());
        result = prime * result + ((isCompleted == null) ? 0 : isCompleted.hashCode());
        result = prime * result + ((_subTasks == null) ? 0 : _subTasks.hashCode());
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
        Task other = (Task) obj;
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
        if (_subTasks == null) {
            if (other._subTasks != null)
                return false;
        } else if (!_subTasks.equals(other._subTasks))
            return false;
        return true;
    }

    /************************* GETTERS *************************/

    public int getId() {
        return id;
    }

    public List<SubTask> getSubTasks() {
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

    public void setSubTasks(List<SubTask> subTasks) {
        this._subTasks = subTasks;
    }

    public void setTaskContent(String taskContent) {
        this._taskContent = taskContent;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
