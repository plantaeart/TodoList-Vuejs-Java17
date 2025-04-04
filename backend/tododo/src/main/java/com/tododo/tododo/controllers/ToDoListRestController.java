package com.tododo.tododo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ToDoListServicesResponse getAllToDoLists(@RequestParam boolean isTest) {
        return service.getAllToDoListsFromJSON(isTest);
    }

    @GetMapping("/getToDoListById")
    public ToDoListServicesResponse getToDoListById(@RequestParam int[] idsList, @RequestParam boolean isTest) {
        return service.getToDoListByIdFromJSON(idsList, isTest);
    }

    @PostMapping("/updateToDoListById")
    public ToDoListServicesResponse updateToDoListById(@RequestBody ToDoListServicesRequest req) {
        return service.updateToDoListByIdFromJSON(req);
    }

    @PostMapping("/addToDoList")
    public ToDoListServicesResponse addToDoList(@RequestBody ToDoListServicesRequest req) {
        return service.addToDoListFromJSON(req);
    }

    @DeleteMapping("deleteToDoListById")
    public ToDoListServicesResponse deleteToDoListById(@RequestParam int[] idsList, @RequestParam boolean isTest) {
        return service.deleteToDoListByIdFromJSON(idsList, isTest);
    }
}
