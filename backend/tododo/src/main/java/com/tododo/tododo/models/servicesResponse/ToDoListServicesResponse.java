package com.tododo.tododo.models.servicesResponse;

import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.dto.ToDoListDTO;

public class ToDoListServicesResponse {

    private Result currentResult;
    private String message;
    private List<ToDoListDTO> toDoLists;

    public ToDoListServicesResponse(Result currentResult, String message, List<ToDoListDTO> toDoLists) {
        this.currentResult = currentResult;
        this.message = message;
        this.toDoLists = toDoLists;
    }

    public ToDoListServicesResponse() {
    }

    public Result getCurrentResult() {
        return currentResult;
    }

    public String getMessage() {
        return message;
    }

    public List<ToDoListDTO> gettoDoLists() {
        return toDoLists;
    }

    public void setCurrentResult(Result currentResult) {
        this.currentResult = currentResult;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void settoDoLists(List<ToDoListDTO> toDoLists) {
        this.toDoLists = toDoLists;
    }

}
