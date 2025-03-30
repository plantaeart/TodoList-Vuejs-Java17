package com.tododo.tododo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tododo.tododo.models.servicesResponse.TaskServicesResponse;
import com.tododo.tododo.models.servivesRequest.TaskServicesRequest;
import com.tododo.tododo.services.TaskServices;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/task")
public class TaskRestController {

    private TaskServices service = new TaskServices();

    @GetMapping("getAllTasksByToDoListId")
    public TaskServicesResponse getTasksByToDoListId(@RequestBody TaskServicesRequest req) {
        return service.getAllTasksFromJSON(req);
    }

    @GetMapping("getTaskById")
    public TaskServicesResponse getTaskById(@RequestBody TaskServicesRequest req) {
        return service.getTaskByIdFromJSON(req);
    }

    @PostMapping("updateTask")
    public TaskServicesResponse updateTask(@RequestBody TaskServicesRequest req) {
        return service.updateTaskFromJSON(req);
    }

    @PostMapping("addTask")
    public TaskServicesResponse addTask(@RequestBody TaskServicesRequest req) {
        return service.addTaskFromJSON(req);
    }

    @DeleteMapping("deleteTask")
    public TaskServicesResponse deleteTask(@RequestBody TaskServicesRequest req) {
        return service.deleteTaskFromJSON(req);
    }

    @PostMapping("switchTasks")
    public TaskServicesResponse switchTasks(@RequestBody TaskServicesRequest req) {
        return service.switchTaskPositionFromJSON(req);
    }
}
