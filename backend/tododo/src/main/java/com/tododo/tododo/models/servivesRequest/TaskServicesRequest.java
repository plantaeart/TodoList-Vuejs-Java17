package com.tododo.tododo.models.servivesRequest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tododo.tododo.models.dto.TaskDTO;

public class TaskServicesRequest extends ToDoListServicesRequest {
    @JsonProperty(value = "idsTask", required = false)
    protected int[] idsTask;
    @JsonProperty(value = "tasks", required = false)
    private List<TaskDTO> tasks;

    public TaskServicesRequest() {
    }

    public TaskServicesRequest(int[] idsList, int[] idsTask, List<TaskDTO> tasks, boolean isTest) {
        super(idsList, isTest);
        this.idsTask = idsTask;
        this.tasks = tasks;
    }

    public TaskServicesRequest(int[] idsList, int[] idsTask, List<TaskDTO> tasks) {
        this.idsList = idsList;
        this.idsTask = idsTask;
        this.tasks = tasks;
    }

    public TaskServicesRequest(int[] idsList, int idsTask[], boolean isTest) {
        super(idsList, isTest);
        this.idsTask = idsTask;
    }

    public TaskServicesRequest(int[] idsList, List<TaskDTO> tasks, boolean isTest) {
        super(idsList, isTest);
        this.tasks = tasks;
    }

    public TaskServicesRequest(int[] idsList, boolean isTest) {
        super(idsList, isTest);
    }

    public int[] getIdsList() {
        return idsList;
    }

    public int[] getIdsTask() {
        return idsTask;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setIdsList(int[] idsList) {
        this.idsList = idsList;
    }

    public void setIdsTask(int[] idsTask) {
        this.idsTask = idsTask;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

}
