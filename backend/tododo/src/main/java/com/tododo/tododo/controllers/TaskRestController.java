package com.tododo.tododo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tododo.tododo.models.servicesResponse.TaskServicesResponse;
import com.tododo.tododo.models.servivesRequest.TaskServicesRequest;
import com.tododo.tododo.services.TaskServices;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/task")
public class TaskRestController {

    private TaskServices service = new TaskServices();

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("getAllTasksByToDoListId")
    public TaskServicesResponse getTasksByToDoListId(@RequestParam int[] idsList, @RequestParam boolean isTest) {
        return service.getAllTasksFromJSON(idsList, isTest);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("getTaskById")
    public TaskServicesResponse getTaskById(@RequestParam int[] idsList, @RequestParam int[] idsTask,
            @RequestParam boolean isTest) {
        return service.getTaskByIdFromJSON(idsList, idsTask, isTest);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("updateTask")
    public TaskServicesResponse updateTask(@RequestBody TaskServicesRequest req) {
        return service.updateTaskFromJSON(req);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("addTask")
    public TaskServicesResponse addTask(@RequestBody TaskServicesRequest req) {
        return service.addTaskFromJSON(req);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @DeleteMapping("deleteTask")
    public TaskServicesResponse deleteTask(@RequestBody TaskServicesRequest req) {
        return service.deleteTaskFromJSON(req);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("switchTasks")
    public TaskServicesResponse switchTasks(@RequestBody TaskServicesRequest req) {
        return service.switchTaskPositionFromJSON(req);
    }
}
