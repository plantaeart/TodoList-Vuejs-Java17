package com.tododo.tododo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tododo.tododo.models.servicesResponse.SubTaskServicesResponse;
import com.tododo.tododo.models.servivesRequest.SubTaskServicesRequest;
import com.tododo.tododo.services.SubTaskServices;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/subTask")
public class SubTaskController {

    private SubTaskServices service = new SubTaskServices();

    @GetMapping("getSubTaskById")
    public SubTaskServicesResponse getTasksById(@RequestBody SubTaskServicesRequest req) {
        return service.getSubTaskByIdFromJSON(req);
    }

    @PostMapping("addSubTask")
    public SubTaskServicesResponse addSubTask(@RequestBody SubTaskServicesRequest req) {
        return service.addSubTaskFromJSON(req);
    }

    @PostMapping("updateSubTask")
    public SubTaskServicesResponse updateSubTask(@RequestBody SubTaskServicesRequest req) {
        return service.updateSubTaskFromJSON(req);
    }

    @DeleteMapping("deleteSubTask")
    public SubTaskServicesResponse deleteSubTask(@RequestBody SubTaskServicesRequest req) {
        return service.deleteSubTaskFromJSON(req);
    }
}
