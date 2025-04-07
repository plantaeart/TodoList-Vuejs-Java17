package com.tododo.tododo.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tododo.tododo.enums.ElementType;

public class Task {

    @JsonProperty(value = "id", required = false)
    public int id;
    @JsonProperty(value = "type", required = false)
    private ElementType type = ElementType.TASK;
    @JsonProperty("taskContent")
    private String taskContent;
    @JsonProperty(value = "description", required = false)
    private String description = "";
    @JsonProperty(value = "completionPercentage", required = false)
    private int completionPercentage = 0;
    @JsonProperty(value = "icon", required = false)
    private Icon icon = new Icon();
    @JsonProperty(value = "isCompleted", required = false)
    protected Boolean isCompleted = false;
    @JsonProperty(value = "subTasks", required = false)
    private List<SubTask> subTasks = new ArrayList<SubTask>();

    /************************* CONSTRUCTORS *************************/

    public Task(int id, String taskContent, Boolean isCompleted, List<SubTask> subTasks) {
        this.id = id;
        this.taskContent = taskContent;
        this.isCompleted = isCompleted;
        this.subTasks = subTasks;
    }

    public Task(ElementType type, String taskContent, String description, int completionPercentage, Icon icon,
            Boolean isCompleted, List<SubTask> subTasks) {
        this.type = type;
        this.taskContent = taskContent;
        this.description = description;
        this.completionPercentage = completionPercentage;
        this.icon = icon;
        this.isCompleted = isCompleted;
        this.subTasks = subTasks;
    }

    public Task(int id, ElementType type, String taskContent, int completionPercentage, Icon icon,
            Boolean isCompleted) {
        this.id = id;
        this.type = type;
        this.taskContent = taskContent;
        this.completionPercentage = completionPercentage;
        this.icon = icon;
        this.isCompleted = isCompleted;
    }

    public Task(String _taskContent, Boolean isCompleted, List<SubTask> _subTasks) {
        this.taskContent = _taskContent;
        this.isCompleted = isCompleted;
        this.subTasks = _subTasks;
    }

    public Task(String _taskContent, Boolean isCompleted) {
        this.taskContent = _taskContent;
        this.isCompleted = isCompleted;
    }

    public Task(String _taskContent) {
        this.taskContent = _taskContent;
    }

    public Task() {
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", type=" + type + ", taskContent=" + taskContent + ", description=" + description
                + ", completionPercentage=" + completionPercentage + ", icon=" + icon + ", isCompleted=" + isCompleted
                + ", subTasks=" + subTasks + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((taskContent == null) ? 0 : taskContent.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + completionPercentage;
        result = prime * result + ((icon == null) ? 0 : icon.hashCode());
        result = prime * result + ((isCompleted == null) ? 0 : isCompleted.hashCode());
        result = prime * result + ((subTasks == null) ? 0 : subTasks.hashCode());
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
        if (type != other.type)
            return false;
        if (taskContent == null) {
            if (other.taskContent != null)
                return false;
        } else if (!taskContent.equals(other.taskContent))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (completionPercentage != other.completionPercentage)
            return false;
        if (icon == null) {
            if (other.icon != null)
                return false;
        } else if (!icon.equals(other.icon))
            return false;
        if (isCompleted == null) {
            if (other.isCompleted != null)
                return false;
        } else if (!isCompleted.equals(other.isCompleted))
            return false;
        if (subTasks == null) {
            if (other.subTasks != null)
                return false;
        } else if (!subTasks.equals(other.subTasks))
            return false;
        return true;
    }

    /************************* GETTERS & SETTERS *************************/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
