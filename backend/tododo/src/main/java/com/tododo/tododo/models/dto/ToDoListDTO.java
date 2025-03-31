package com.tododo.tododo.models.dto;

import java.util.ArrayList;
import java.util.List;

import com.tododo.tododo.enums.ElementType;

public class ToDoListDTO {
    private int id;
    private ElementType type = ElementType.TODOLIST;
    private String name;
    private String description = "";
    private int completionPercentage = 0;
    private String color = "#FFFFFF";
    private String icon = "";
    private Boolean isCompleted;
    private List<TaskDTO> tasks = new ArrayList<TaskDTO>();

    public ToDoListDTO(ElementType type, String name, String description, int completionPercentage, String color,
            String icon, Boolean isCompleted, List<TaskDTO> tasks) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.completionPercentage = completionPercentage;
        this.color = color;
        this.icon = icon;
        this.isCompleted = isCompleted;
        this.tasks = tasks;
    }

    public ToDoListDTO(String name) {
        this.name = name;
    }

    public ToDoListDTO() {
    }

    @Override
    public String toString() {
        return "ToDoListDTO [id=" + id + ", type=" + type + ", name=" + name + ", description=" + description
                + ", completionPercentage=" + completionPercentage + ", color=" + color + ", icon=" + icon
                + ", isCompleted=" + isCompleted + ", tasks=" + tasks + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + completionPercentage;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((icon == null) ? 0 : icon.hashCode());
        result = prime * result + ((isCompleted == null) ? 0 : isCompleted.hashCode());
        result = prime * result + ((tasks == null) ? 0 : tasks.hashCode());
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
        ToDoListDTO other = (ToDoListDTO) obj;
        if (id != other.id)
            return false;
        if (type != other.type)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (completionPercentage != other.completionPercentage)
            return false;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
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
        if (tasks == null) {
            if (other.tasks != null)
                return false;
        } else if (!tasks.equals(other.tasks))
            return false;
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public void taskIsCompleted() {
        for (TaskDTO task : this.tasks) {
            task.setIsCompleted(true);
        }
    }
}