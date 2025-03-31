package com.tododo.tododo.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tododo.tododo.enums.ElementType;

public class ToDoList {

    @JsonProperty(value = "id", required = false)
    public int id;
    @JsonProperty(value = "type", required = false)
    private ElementType type = ElementType.TODOLIST;
    @JsonProperty("name")
    private String name;
    @JsonProperty(value = "description", required = false)
    private String description = "";
    @JsonProperty(value = "completionPercentage", required = false)
    private int completionPercentage = 0;
    @JsonProperty(value = "color", required = false)
    private String color = "#FFFFFF";
    @JsonProperty(value = "icon", required = false)
    private String icon = "";
    @JsonProperty(value = "isCompleted", required = false)
    protected Boolean isCompleted = false;
    @JsonProperty(value = "tasks", required = false)
    private List<Task> tasks = new ArrayList<Task>();
    @JsonProperty(value = "updateDate", required = false)
    private String updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

    /************************* CONSTRUCTORS *************************/

    public ToDoList() {
    }

    public ToDoList(ElementType type, String name, String description, int completionPercentage, String color,
            String icon, Boolean isCompleted, List<Task> tasks, String updateDate) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.completionPercentage = completionPercentage;
        this.color = color;
        this.icon = icon;
        this.isCompleted = isCompleted;
        this.tasks = tasks;
        this.updateDate = updateDate;
    }

    public ToDoList(ElementType type, String name, String description, int completionPercentage, String color,
            String icon, Boolean isCompleted, List<Task> tasks) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.completionPercentage = completionPercentage;
        this.color = color;
        this.icon = icon;
        this.isCompleted = isCompleted;
        this.tasks = tasks;
    }

    public ToDoList(int id, ElementType type, String name, int completionPercentage, String color, String icon,
            Boolean isCompleted) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.completionPercentage = completionPercentage;
        this.color = color;
        this.icon = icon;
        this.isCompleted = isCompleted;
    }

    public ToDoList(int id, String name, Boolean isCompleted, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.isCompleted = isCompleted;
        this.tasks = tasks;
    }

    public ToDoList(String name, Boolean isCompleted, List<Task> tasks) {
        this.name = name;
        this.isCompleted = isCompleted;
        this.tasks = tasks;
    }

    public ToDoList(String _name, Boolean isCompleted) {
        this.name = _name;
        this.isCompleted = isCompleted;
    }

    public ToDoList(String _name) {
        this.name = _name;
    }

    @Override
    public String toString() {
        return "ToDoList [id=" + id + ", type=" + type + ", name=" + name + ", description=" + description
                + ", completionPercentage=" + completionPercentage + ", color=" + color + ", icon=" + icon
                + ", isCompleted=" + isCompleted + ", tasks=" + tasks + ", updateDate=" + updateDate + "]";
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
        result = prime * result + ((updateDate == null) ? 0 : updateDate.hashCode());
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
        ToDoList other = (ToDoList) obj;
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
        if (updateDate == null) {
            if (other.updateDate != null)
                return false;
        } else if (!updateDate.equals(other.updateDate))
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

}
