package com.tododo.tododo.models.servicesResponse;

import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.Task;

public class TaskServicesResponse {
    private Result _currentResult;
    private String _message;
    private List<Task> _taskList;

    public TaskServicesResponse() {
    }

    public TaskServicesResponse(Result _currentResult, String _message, List<Task> _taskList) {
        this._currentResult = _currentResult;
        this._message = _message;
        this._taskList = _taskList;
    }

    public Result get_currentResult() {
        return _currentResult;
    }

    public String get_message() {
        return _message;
    }

    public List<Task> get_taskList() {
        return _taskList;
    }

    public void set_currentResult(Result _currentResult) {
        this._currentResult = _currentResult;
    }

    public void set_message(String _message) {
        this._message = _message;
    }

    public void set_taskList(List<Task> _taskList) {
        this._taskList = _taskList;
    }

}
