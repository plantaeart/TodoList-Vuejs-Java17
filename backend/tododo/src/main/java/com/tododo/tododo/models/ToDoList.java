package com.tododo.tododo.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ToDoList {

    @JsonProperty(value = "id", required = false)
    public int id;
    @JsonProperty("name")
    private String _name;
    @JsonProperty(value = "tasks", required = false)
    private List<Task> _tasks;

    /************************* CONSTRUCTORS *************************/

    public ToDoList(int id, String name, List<Task> tasks) {
        this.id = id;
        this._name = name;
        this._tasks = tasks;
    }

    public ToDoList() {
    }

    @Override
    public String toString() {
        return "ToDoList [id=" + id + ", _name=" + _name + ", _tasks=" + _tasks + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((_name == null) ? 0 : _name.hashCode());
        result = prime * result + ((_tasks == null) ? 0 : _tasks.hashCode());
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
        if (_tasks == null) {
            if (other._tasks != null)
                return false;
        } else if (!_tasks.equals(other._tasks))
            return false;
        return true;
    }

    /************************* GETTERS *************************/

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String get_name() {
        return _name;
    }

    public List<Task> get_tasks() {
        return _tasks;
    }

    /************************* SETTERS *************************/

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_tasks(List<Task> _tasks) {
        this._tasks = _tasks;
    }

}
