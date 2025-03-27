package com.tododo.tododo.models.servivesRequest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tododo.tododo.models.SubTask;
import com.tododo.tododo.models.Task;

public class SubTaskServicesRequest extends TaskServicesRequest {

    @JsonProperty(value = "subTasks", required = false)
    private List<SubTask> _subTasks;

    public SubTaskServicesRequest(int _idList, int _idTask, List<Task> _tasks, List<SubTask> _subTasks) {
        super(_idList, _idTask, _tasks);
        this._subTasks = _subTasks;
    }

    public SubTaskServicesRequest(List<SubTask> _subTasks) {
        this._subTasks = _subTasks;
    }

    public SubTaskServicesRequest() {
    }

    public List<SubTask> get_subTasks() {
        return _subTasks;
    }

    public void set_subTasks(List<SubTask> _subTasks) {
        this._subTasks = _subTasks;
    }
}
