package com.tododo.tododo.models.servicesResponse;

import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.dto.TaskDTO;

public class TaskServicesResponse {
    private Result currentResult;
    private String message;
    private List<TaskDTO> tasks;

    public TaskServicesResponse() {
    }

    public TaskServicesResponse(Result currentResult, String message, List<TaskDTO> tasks) {
        this.currentResult = currentResult;
        this.message = message;
        this.tasks = tasks;
    }

    public Result getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(Result currentResult) {
        this.currentResult = currentResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

}
