package com.tododo.tododo.models.servivesRequest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tododo.tododo.models.dto.SubTaskDTO;
import com.tododo.tododo.models.dto.TaskDTO;

public class SubTaskServicesRequest extends TaskServicesRequest {

    @JsonProperty(value = "subTasks", required = false)
    private List<SubTaskDTO> subTasks;
    @JsonProperty(value = "idsSubTask", required = false)
    private int[] idsSubTask;

    public SubTaskServicesRequest(List<SubTaskDTO> subTasks, int[] idsSubTask) {
        this.subTasks = subTasks;
        this.idsSubTask = idsSubTask;
    }

    public SubTaskServicesRequest(int[] idsList, int[] idsTask, int[] idsSubTask, List<TaskDTO> tasks,
            List<SubTaskDTO> subTasks) {
        super(idsList, idsTask, tasks);
        this.subTasks = subTasks;
        this.idsSubTask = idsSubTask;
    }

    public SubTaskServicesRequest(int[] idsList, int[] idsTask, int[] idsSubTask, List<SubTaskDTO> subTasks,
            boolean isTest) {
        super(idsList, idsTask, isTest);
        this.subTasks = subTasks;
        this.idsSubTask = idsSubTask;
    }

    public SubTaskServicesRequest(int[] idsList, int[] idsTask, List<TaskDTO> tasks, List<SubTaskDTO> subTasks) {
        super(idsList, idsTask, tasks);
        this.subTasks = subTasks;
    }

    public SubTaskServicesRequest(int[] idsList, int[] idsTask, int[] idsSubTask, boolean isTest) {
        super(idsList, idsTask, isTest);
        this.idsSubTask = idsSubTask;
    }

    public SubTaskServicesRequest(int[] idsList, int[] idsTask, List<SubTaskDTO> subTasks, boolean isTest) {
        super(idsList, idsTask, isTest);
        this.subTasks = subTasks;
    }

    public SubTaskServicesRequest(List<SubTaskDTO> subTasks) {
        this.subTasks = subTasks;
    }

    public SubTaskServicesRequest() {
    }

    public List<SubTaskDTO> getSubTasks() {
        return subTasks;
    }

    public int[] getIdsSubTask() {
        return idsSubTask;
    }

    public void setSubTasks(List<SubTaskDTO> subTasks) {
        this.subTasks = subTasks;
    }

    public void setIdsSubTask(int[] idsSubTask) {
        this.idsSubTask = idsSubTask;
    }

}
