package com.tododo.tododo.models.servivesRequest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tododo.tododo.models.Task;

public class TaskServicesRequest extends ToDoListServicesRequest {
    @JsonProperty(value = "idTask", required = false)
    protected int _idTask;
    @JsonProperty(value = "tasks", required = false)
    private List<Task> _tasks;

    public TaskServicesRequest() {
    }

    public TaskServicesRequest(int _idList, int _idTask, List<Task> _tasks, boolean isTest) {
        super(_idList, isTest);
        this._idTask = _idTask;
        this._tasks = _tasks;
    }

    public TaskServicesRequest(int _idList, int _idTask, List<Task> _tasks) {
        this._idList = _idList;
        this._idTask = _idTask;
        this._tasks = _tasks;
    }

    public TaskServicesRequest(int _idList, int _idTask, boolean isTest) {
        super(_idList, isTest);
        this._idTask = _idTask;
    }

    public TaskServicesRequest(int _idList, List<Task> _tasks, boolean isTest) {
        super(_idList, isTest);
        this._tasks = _tasks;
    }

    public TaskServicesRequest(int _idList, boolean isTest) {
        super(_idList, isTest);
    }

    public int get_idList() {
        return _idList;
    }

    public int get_idTask() {
        return _idTask;
    }

    public List<Task> get_tasks() {
        return _tasks;
    }

    public void set_idList(int _idList) {
        this._idList = _idList;
    }

    public void set_idTask(int _idTask) {
        this._idTask = _idTask;
    }

    public void set_tasks(List<Task> _tasks) {
        this._tasks = _tasks;
    }

}
