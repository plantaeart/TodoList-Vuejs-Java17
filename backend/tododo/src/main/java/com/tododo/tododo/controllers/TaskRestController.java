package com.tododo.tododo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public TaskServicesResponse getTasksByToDoListId(@RequestParam int[] idsList, @RequestParam boolean isTest) {
        return service.getAllTasksFromJSON(idsList, isTest);
    }

    @GetMapping("getTaskById")
    public TaskServicesResponse getTaskById(@RequestParam int[] idsList, @RequestParam int[] idsTask,
            @RequestParam boolean isTest) {
        return service.getTaskByIdFromJSON(idsList, idsTask, isTest);
    }

    @PostMapping("updateTaskById")
    public TaskServicesResponse updateTaskById(@RequestBody TaskServicesRequest req) {
        return service.updateTaskByIdFromJSON(req);
    }

    @PostMapping("addTask")
    public TaskServicesResponse addTask(@RequestBody TaskServicesRequest req) {
        return service.addTaskFromJSON(req);
    }

    @DeleteMapping("deleteTaskById")
    public TaskServicesResponse deleteTaskById(@RequestParam int[] idsList, @RequestParam int[] idsTask,
            @RequestParam boolean isTest) {
        return service.deleteTaskByIdFromJSON(idsList, idsTask, isTest);
    }

    @PostMapping("switchTasks")
    public TaskServicesResponse switchTasks(@RequestBody TaskServicesRequest req) {
        return service.switchTaskPositionFromJSON(req);
    }
}
