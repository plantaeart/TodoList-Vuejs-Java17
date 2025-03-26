package com.tododo.tododo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tododo.tododo.models.servicesResponse.TaskServicesResponse;
import com.tododo.tododo.models.servivesRequest.TaskServicesRequest;
import com.tododo.tododo.services.TaskServices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/task")
public class TaskRestController {

    private TaskServices service = new TaskServices();

    @GetMapping("getTasksByToDoListId/{idList}")
    public TaskServicesResponse getTasksByToDoListId(@PathVariable("idList") int idList) {
        return service.getAllTasksFromJSON(idList);
    }

    @PostMapping("updateTask")
    public TaskServicesResponse postMethodName(@RequestBody TaskServicesRequest req) {
        return service.updateTaskFromJSON(req);
    }

}
