package com.tododo.tododo.models.servivesRequest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tododo.tododo.models.SubTask;
import com.tododo.tododo.models.Task;

public class SubTaskServicesRequest extends TaskServicesRequest {

    @JsonProperty(value = "subTasks", required = false)
    private List<SubTask> _subTasks;
    @JsonProperty(value = "idSubTask", required = false)
    private int _idSubTask;

    public SubTaskServicesRequest(List<SubTask> _subTasks, int _idSubTask) {
        this._subTasks = _subTasks;
        this._idSubTask = _idSubTask;
    }

    public SubTaskServicesRequest(int _idList, int _idTask, List<Task> _tasks, List<SubTask> _subTasks,
            int _idSubTask) {
        super(_idList, _idTask, _tasks);
        this._subTasks = _subTasks;
        this._idSubTask = _idSubTask;
    }

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

    public int get_idSubTask() {
        return _idSubTask;
    }

    public void set_subTasks(List<SubTask> _subTasks) {
        this._subTasks = _subTasks;
    }

    public void set_idSubTask(int _idSubTask) {
        this._idSubTask = _idSubTask;
    }

}
