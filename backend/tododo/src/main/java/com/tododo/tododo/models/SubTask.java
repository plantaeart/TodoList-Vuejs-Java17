package com.tododo.tododo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tododo.tododo.enums.ElementType;

public class SubTask {

    @JsonProperty(value = "id", required = false)
    public int id;
    @JsonProperty(value = "type", required = false)
    private ElementType type = ElementType.SUBTASK;
    @JsonProperty("taskContent")
    private String taskContent;
    @JsonProperty(value = "description", required = false)
    private String description = "";
    @JsonProperty(value = "icon", required = false)
    private Icon icon = new Icon();
    @JsonProperty(value = "isCompleted", required = false)
    protected Boolean isCompleted = false;

    /************************* CONSTRUCTORS *************************/

    public SubTask(int id, String taskContent, Boolean isCompleted) {
        this.id = id;
        this.taskContent = taskContent;
        this.isCompleted = isCompleted;
    }

    public SubTask(ElementType type, String taskContent, String description, Icon icon, Boolean isCompleted) {
        this.type = type;
        this.taskContent = taskContent;
        this.description = description;
        this.icon = icon;
        this.isCompleted = isCompleted;
    }

    public SubTask(ElementType type, String taskContent, Icon icon, Boolean isCompleted) {
        this.type = type;
        this.taskContent = taskContent;
        this.icon = icon;
        this.isCompleted = isCompleted;
    }

    public SubTask(String _taskContent) {
        this.taskContent = _taskContent;
    }

    public SubTask() {
    }

    @Override
    public String toString() {
        return "SubTask [id=" + id + ", type=" + type + ", taskContent=" + taskContent + ", description=" + description
                + ", icon=" + icon + ", isCompleted=" + isCompleted + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((taskContent == null) ? 0 : taskContent.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((icon == null) ? 0 : icon.hashCode());
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
