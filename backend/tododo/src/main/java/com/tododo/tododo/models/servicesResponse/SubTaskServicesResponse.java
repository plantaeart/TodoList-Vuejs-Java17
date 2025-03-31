package com.tododo.tododo.models.servicesResponse;

import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.dto.SubTaskDTO;

public class SubTaskServicesResponse {
    private Result currentResult;
    private String message;
    private List<SubTaskDTO> subTasks;

    public SubTaskServicesResponse() {
    }

    public SubTaskServicesResponse(Result currentResult, String message, List<SubTaskDTO> subTasks) {
        this.currentResult = currentResult;
        this.message = message;
        this.subTasks = subTasks;
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

    public List<SubTaskDTO> getSubTaskList() {
        return subTasks;
    }

    public void setSubTaskList(List<SubTaskDTO> subTasks) {
        this.subTasks = subTasks;
    }

}
