package com.tododo.tododo.models.dto;

import com.tododo.tododo.enums.ElementType;

public class SubTaskDTO {
    private int id;
    private ElementType type = ElementType.SUBTASK;
    private String taskContent;
    private String icon = "";
    private Boolean isCompleted;

    public SubTaskDTO() {
    }

    public SubTaskDTO(ElementType type, String taskContent, String icon, Boolean isCompleted) {
        this.type = type;
        this.taskContent = taskContent;
        this.icon = icon;
        this.isCompleted = isCompleted;
    }

    public SubTaskDTO(String taskContent) {
        this.taskContent = taskContent;
    }

    @Override
    public String toString() {
        return "SubTaskDTO [id=" + id + ", type=" + type + ", taskContent=" + taskContent + ", icon=" + icon
                + ", isCompleted=" + isCompleted + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((taskContent == null) ? 0 : taskContent.hashCode());
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
        SubTaskDTO other = (SubTaskDTO) obj;
        if (id != other.id)
            return false;
        if (type != other.type)
            return false;
        if (taskContent == null) {
            if (other.taskContent != null)
                return false;
        } else if (!taskContent.equals(other.taskContent))
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}