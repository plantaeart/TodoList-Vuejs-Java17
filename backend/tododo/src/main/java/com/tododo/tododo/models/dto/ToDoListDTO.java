package com.tododo.tododo.models.dto;

import java.util.ArrayList;
import java.util.List;

import com.tododo.tododo.enums.ElementType;

public class ToDoListDTO {
    private int id;
    private ElementType type = ElementType.TODOLIST;
    private String name;
    private Boolean isCompleted;
    private List<TaskDTO> tasks = new ArrayList<TaskDTO>();

    public ToDoListDTO(String name) {
        this.name = name;
    }

    public ToDoListDTO() {
    }

    @Override
    public String toString() {
        return "ToDoListDTO [id=" + id + ", type=" + type + ", name=" + name + ", isCompleted=" + isCompleted
                + ", tasks=" + tasks + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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

    /************************* OTHERS *************************/

    public void taskIsCompleted() {
        for (TaskDTO task : this.tasks) {
            task.setIsCompleted(true);
        }
    }

}