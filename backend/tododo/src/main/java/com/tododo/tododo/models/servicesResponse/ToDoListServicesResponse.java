package com.tododo.tododo.models.servicesResponse;

import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.dto.ToDoListDTO;

public class ToDoListServicesResponse {

    private Result currentResult;
    private String message;
    private List<ToDoListDTO> toDoListList;

    public ToDoListServicesResponse(Result currentResult, String message, List<ToDoListDTO> toDoListList) {
        this.currentResult = currentResult;
        this.message = message;
        this.toDoListList = toDoListList;
    }

    public ToDoListServicesResponse() {
    }

    public Result getCurrentResult() {
        return currentResult;
    }

    public String getMessage() {
        return message;
    }

    public List<ToDoListDTO> getToDoListList() {
        return toDoListList;
    }

    public void setCurrentResult(Result currentResult) {
        this.currentResult = currentResult;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToDoListList(List<ToDoListDTO> toDoListList) {
        this.toDoListList = toDoListList;
    }

}
