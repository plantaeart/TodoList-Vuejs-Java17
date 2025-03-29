package com.tododo.tododo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tododo.tododo.models.servicesResponse.ToDoListServicesResponse;
import com.tododo.tododo.models.servivesRequest.ToDoListServicesRequest;
import com.tododo.tododo.services.ToDoListServices;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/toDoList")
public class ToDoListRestController {

    private ToDoListServices service = new ToDoListServices();

    @GetMapping("/getAllToDoLists")
    public ToDoListServicesResponse getAllToDoLists(@RequestBody ToDoListServicesRequest req) {
        return service.getAllToDoListsFromJSON(req);
    }

    @GetMapping("/getToDoListById")
    public ToDoListServicesResponse getToDoListById(@RequestBody ToDoListServicesRequest req) {
        return service.getListByIdFromJSON(req);
    }

    @PostMapping("/updateToDoList")
    public ToDoListServicesResponse updateToDoList(@RequestBody ToDoListServicesRequest req) {
        return service.updateToDoListFromJSON(req);
    }

    @PostMapping("/addToDoList")
    public ToDoListServicesResponse addToDoList(@RequestBody ToDoListServicesRequest req) {
        return service.addToDoListFromJSON(req);
    }

    @DeleteMapping("deleteToDoListById")
    public ToDoListServicesResponse deleteToDoListById(@RequestBody ToDoListServicesRequest req) {
        return service.deleteToDoListByIdFromJSON(req);
    }
}
