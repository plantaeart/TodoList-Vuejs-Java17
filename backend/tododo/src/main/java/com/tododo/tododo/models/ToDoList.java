package com.tododo.tododo.models;

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
    @JsonProperty(value = "isCompleted", required = false)
    protected Boolean isCompleted = false;
    @JsonProperty(value = "tasks", required = false)
    private List<Task> tasks = new ArrayList<Task>();

    /************************* CONSTRUCTORS *************************/

    public ToDoList() {
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
        return "ToDoList [id=" + id + ", type=" + type + ", name=" + name + ", isCompleted=" + isCompleted + ", tasks="
                + tasks + "]";
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
}
