package com.tododo.tododo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tododo.tododo.models.servicesResponse.SubTaskServicesResponse;
import com.tododo.tododo.models.servivesRequest.SubTaskServicesRequest;
import com.tododo.tododo.services.SubTaskServices;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/subTask")
public class SubTaskController {

    private SubTaskServices service = new SubTaskServices();

    @PostMapping("addSubTask")
    public SubTaskServicesResponse addSubTask(@RequestBody SubTaskServicesRequest req) {
        return service.addTaskFromJSON(req);
    }

}
