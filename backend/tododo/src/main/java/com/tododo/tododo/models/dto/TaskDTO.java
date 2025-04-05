package com.tododo.tododo.models.dto;

import java.util.ArrayList;
import java.util.List;

import com.tododo.tododo.enums.ElementType;

public class TaskDTO {
    private int id;
    private ElementType type = ElementType.TASK;
    private String taskContent;
    private String description = "";
    private int completionPercentage = 0;
    private String icon = "";
    private Boolean isCompleted = false;
    private List<SubTaskDTO> subTasks = new ArrayList<SubTaskDTO>();

    public TaskDTO() {
    }

    public TaskDTO(ElementType type, String taskContent, String description, int completionPercentage, String icon,
            Boolean isCompleted, List<SubTaskDTO> subTasks) {
        this.type = type;
        this.taskContent = taskContent;
        this.description = description;
        this.completionPercentage = completionPercentage;
        this.icon = icon;
        this.isCompleted = isCompleted;
        this.subTasks = subTasks;
    }

    public TaskDTO(String taskContent) {
        this.taskContent = taskContent;
    }

    @Override
    public String toString() {
        return "TaskDTO [id=" + id + ", type=" + type + ", taskContent=" + taskContent + ", description=" + description
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
        TaskDTO other = (TaskDTO) obj;
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

    public List<SubTaskDTO> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<SubTaskDTO> subTasks) {
        this.subTasks = subTasks;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /************************* OTHERS *************************/

    public void setTaskDTO(TaskDTO task) {
        if (task.getId() != 0) {
            this.id = task.getId();
        }
        if (task.getType() != null) {
            this.type = task.getType();
        }
        if (task.getTaskContent() != null) {
            this.taskContent = task.getTaskContent();
        }
        if (task.getDescription() != null) {
            this.description = task.getDescription();
        }
        if (task.getCompletionPercentage() != 0) {
            this.completionPercentage = task.getCompletionPercentage();
        }
        if (task.getIcon() != null) {
            this.icon = task.getIcon();
        }
        if (task.getIsCompleted() != null) {
            this.isCompleted = task.getIsCompleted();
        }
        if (task.getSubTasks() != null && !task.getSubTasks().isEmpty()) {
            this.subTasks = task.getSubTasks();
        }
    }
}