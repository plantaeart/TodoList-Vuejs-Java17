package com.tododo.tododo.models.servicesResponse;

import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.SubTask;

public class SubTaskServicesResponse {
    private Result _currentResult;
    private String _message;
    private List<SubTask> _subTaskList;

    public SubTaskServicesResponse() {
    }

    public SubTaskServicesResponse(Result _currentResult, String _message, List<SubTask> _subTaskList) {
        this._currentResult = _currentResult;
        this._message = _message;
        this._subTaskList = _subTaskList;
    }

    public Result get_currentResult() {
        return _currentResult;
    }

    public String get_message() {
        return _message;
    }

    public List<SubTask> get_subTaskList() {
        return _subTaskList;
    }

    public void set_currentResult(Result _currentResult) {
        this._currentResult = _currentResult;
    }

    public void set_message(String _message) {
        this._message = _message;
    }

    public void set_subTaskList(List<SubTask> _subTaskList) {
        this._subTaskList = _subTaskList;
    }
}
