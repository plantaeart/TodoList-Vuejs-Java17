package com.tododo.tododo.models.servicesResponse;

import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.ToDoList;

public class ToDoListServicesResponse {

    private Result _currentResult;
    private String _message;
    private List<ToDoList> _toDoListList;

    public ToDoListServicesResponse(Result currentResult, String message, List<ToDoList> toDoListList) {
        this._currentResult = currentResult;
        this._message = message;
        this._toDoListList = toDoListList;
    }

    public ToDoListServicesResponse() {
    }

    public Result get_currentResult() {
        return _currentResult;
    }

    public String get_message() {
        return _message;
    }

    public List<ToDoList> get_toDoListList() {
        return _toDoListList;
    }

    public void set_currentResult(Result _currentResult) {
        this._currentResult = _currentResult;
    }

    public void set_message(String _message) {
        this._message = _message;
    }

    public void set_toDoListList(List<ToDoList> _toDoListList) {
        this._toDoListList = _toDoListList;
    }

}
