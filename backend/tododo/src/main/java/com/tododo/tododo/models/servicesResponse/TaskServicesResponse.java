package com.tododo.tododo.models.servicesResponse;

import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.dto.TaskDTO;

public class TaskServicesResponse {
    private Result currentResult;
    private String message;
    private List<TaskDTO> taskList;

    public TaskServicesResponse() {
    }

    public TaskServicesResponse(Result currentResult, String message, List<TaskDTO> taskList) {
        this.currentResult = currentResult;
        this.message = message;
        this.taskList = taskList;
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

    public List<TaskDTO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskDTO> taskList) {
        this.taskList = taskList;
    }

}
