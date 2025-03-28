package com.tododo.tododo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tododo.tododo.models.servicesResponse.ToDoListServicesResponse;
import com.tododo.tododo.models.servivesRequest.ToDoListRequest;
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
    public ToDoListServicesResponse getAllToDoLists(@RequestBody ToDoListRequest req) {
        return service.getAllToDoListsFromJSON(req);
    }

    @GetMapping("/getToDoListById")
    public ToDoListServicesResponse getToDoListById(@RequestBody ToDoListRequest req) {
        return service.getListByIdFromJSON(req);
    }

    @PostMapping("/updateToDoList")
    public ToDoListServicesResponse updateToDoList(@RequestBody ToDoListRequest req) {
        return service.updateToDoListFromJSON(req);
    }

    @PostMapping("/addToDoList")
    public ToDoListServicesResponse addToDoList(@RequestBody ToDoListRequest req) {
        return service.addToDoListFromJSON(req);
    }

    @DeleteMapping("deleteToDoListById")
    public ToDoListServicesResponse deleteToDoListById(@RequestBody ToDoListRequest req) {
        return service.deleteToDoListByIdFromJSON(req);
    }
}
