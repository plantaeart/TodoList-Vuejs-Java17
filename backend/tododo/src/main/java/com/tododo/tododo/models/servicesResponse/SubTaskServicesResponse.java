package com.tododo.tododo.models.servicesResponse;

import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.dto.SubTaskDTO;

public class SubTaskServicesResponse {
    private Result currentResult;
    private String message;
    private List<SubTaskDTO> subTaskList;

    public SubTaskServicesResponse() {
    }

    public SubTaskServicesResponse(Result currentResult, String message, List<SubTaskDTO> subTaskList) {
        this.currentResult = currentResult;
        this.message = message;
        this.subTaskList = subTaskList;
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
        return subTaskList;
    }

    public void setSubTaskList(List<SubTaskDTO> subTaskList) {
        this.subTaskList = subTaskList;
    }

}
