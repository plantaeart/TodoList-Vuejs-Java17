package com.tododo.tododo.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {

    @JsonProperty(value = "id", required = false)
    public int id;
    @JsonProperty("taskContent")
    private String _taskContent;
    @JsonProperty(value = "isCompleted", required = false)
    protected Boolean isCompleted = false;
    @JsonProperty(value = "subTasks", required = false)
    private List<SubTask> _subTasks = new ArrayList<SubTask>();

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

    public List<SubTask> get_subTasks() {
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

    public void set_subTasks(List<SubTask> subTasks) {
        this._subTasks = subTasks;
    }

    public void setTaskContent(String taskContent) {
        this._taskContent = taskContent;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /************************* OTHERS *************************/

    public void taskIsCompleted() {
        for (SubTask subTask : _subTasks) {
            subTask.setIsCompleted(true);
        }
    }
}
