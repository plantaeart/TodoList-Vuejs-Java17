package com.tododo.tododo.models;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ToDoList {

    @JsonProperty(value = "id", required = false)
    public int id;
    @JsonProperty("name")
    private String _name;
    @JsonProperty("tasks")
    private Task[] _tasks;
    
    /************************* CONSTRUCTORS *************************/

    public ToDoList(int id, String name, Task[] tasks) {
        this.id = id;
        this._name = name;
        this._tasks = tasks;
    }

    public ToDoList() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((_name == null) ? 0 : _name.hashCode());
        result = prime * result + Arrays.hashCode(_tasks);
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
        if (_name == null) {
            if (other._name != null)
                return false;
        } else if (!_name.equals(other._name))
            return false;
        if (!Arrays.equals(_tasks, other._tasks))
            return false;
        return true;
    }

    /************************* GETTERS *************************/

    public int getId() {
        return id;
    }

    public String getName() {
        return _name;
    }

    public Task[] getTasks() {
        return _tasks;
    }

    /************************* SETTERS *************************/

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this._name = name;
    }

    public void setTasks(Task[] tasks) {
        this._tasks = tasks;
    }

    
}
