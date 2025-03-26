package com.tododo.tododo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tododo.tododo.models.ToDoList;
import com.tododo.tododo.models.servicesResponse.ToDoListServicesResponse;
import com.tododo.tododo.services.ToDoListServices;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class ListRestController {

    public ToDoListServices service = new ToDoListServices();

    @GetMapping("/getAllToDoLists")
    public ToDoListServicesResponse getAllToDoLists() {
        return service.getAllToDoListsFromJSON();
    }

    @GetMapping("/getToDoListById/{id}")
    public ToDoListServicesResponse getToDoListById(@PathVariable("id") int id) {
        return service.getListByIdFromJSON(id);
    }

    @PostMapping("/updateToDoList")
    public ToDoListServicesResponse updateToDoList(@RequestBody ToDoList entity) {
        return service.updateToDoListFromJSON(entity);
    }

    @PostMapping("/addToDoList")
    public ToDoListServicesResponse addToDoList(@RequestBody ToDoList entity) {
        return service.addToDoListFromJSON(entity);
    }

    @DeleteMapping("deleteToDoListById/{id}")
    public ToDoListServicesResponse deleteToDoListById(@PathVariable int id) {
        return service.removeToDoListByIdFromJSON(id);
    }
}
