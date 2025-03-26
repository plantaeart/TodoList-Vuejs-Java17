package com.tododo.tododo.models.servicesResponse;

import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.ToDoList;

public class ToDoListServicesResponse {

    private Result currentResult;
    private String _message;
    private List<ToDoList> _toDoListList;

    public ToDoListServicesResponse(Result currentResult, String message, List<ToDoList> toDoListList) {
        this.currentResult = currentResult;
        this._message = message;
        this._toDoListList = toDoListList;
    }

    public ToDoListServicesResponse() {
    }

    public Result getCurrentResult() {
        return currentResult;
    }

    public List<ToDoList> getToDoListList() {
        return _toDoListList;
    }

    public String getMessage() {
        return _message;
    }

    public void setCurrentResult(Result currentResult) {
        this.currentResult = currentResult;
    }

    public void setToDoListList(List<ToDoList> toDoListList) {
        this._toDoListList = toDoListList;
    }

    public void setMessage(String message) {
        this._message = message;
    }

}
