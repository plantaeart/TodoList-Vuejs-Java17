package com.tododo.tododo.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.ToDoList;
import com.tododo.tododo.models.servicesResponse.ToDoListServicesResponse;
import com.tododo.tododo.utils.JsonUtils;

public class ToDoListServices {
    File jsonFile = new File("src\\main\\resources\\data.json");

    // Get all ToDoList in data.json
    public ToDoListServicesResponse getAllToDoListsFromJSON() {
        // Getting the json file
        ToDoListServicesResponse toDoListResp = new ToDoListServicesResponse();

        try {
            // creating the mapper json to List<ToDoList>
            ObjectMapper objectMapper = new ObjectMapper();
            toDoListResp.set_toDoListList(objectMapper.readValue(
                    jsonFile,
                    new TypeReference<List<ToDoList>>() {
                    }));

        } catch (Exception e) {
            String message = "error while parsing all todo lists : " + e.getMessage();
            System.err.println(message);
            toDoListResp.set_message(message);
            toDoListResp.set_currentResult(Result.ERROR);

            return toDoListResp;
        }

        toDoListResp.set_currentResult(Result.OK);
        return toDoListResp;
    }

    // Get one ToDoList by it's id in data.json
    public ToDoListServicesResponse getListByIdFromJSON(int listId) {
        // Getting the json file
        ToDoListServicesResponse toDoListResp = new ToDoListServicesResponse();

        try {
            // Getting the one that match the id passed as param
            toDoListResp.set_toDoListList(
                    getAllToDoListsFromJSON().get_toDoListList().stream().filter(x -> x.id == listId).toList());

        } catch (Exception e) {
            String message = "error while getting todo list number : " + listId + " - error : " + e.getMessage();
            System.err.println(message);
            toDoListResp.set_message(message);
            toDoListResp.set_currentResult(Result.ERROR);

            return toDoListResp;
        }

        toDoListResp.set_currentResult(toDoListResp.get_toDoListList().size() == 0 ? Result.NOT_EXISTING : Result.OK);
        return toDoListResp;
    }

    // Get update one ToDoList by it's id in data.json
    public ToDoListServicesResponse updateToDoListFromJSON(ToDoList newUpdatedToDoList) {
        // Getting the json file
        ToDoListServicesResponse toDolists = new ToDoListServicesResponse();
        ToDoList oldToDoList = new ToDoList();
        int currentIndex = 0;

        try {
            // Get all toDoLists
            toDolists.set_toDoListList(getAllToDoListsFromJSON().get_toDoListList());
            // Get the old one and it's index
            oldToDoList = getListByIdFromJSON(newUpdatedToDoList.getId()).get_toDoListList().get(0);
            currentIndex = toDolists.get_toDoListList().indexOf(oldToDoList);
            // Remove the old one
            toDolists.get_toDoListList().remove(currentIndex);
            // Insert the new one at the current old one position
            toDolists.get_toDoListList().add(currentIndex, newUpdatedToDoList);

            // overwrites the content of file
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(jsonFile),
                    StandardCharsets.UTF_8);

            writer.write(JsonUtils.toJson(toDolists.get_toDoListList()).toString());
            writer.close();
        } catch (Exception e) {
            String message = "error while updating todo list " + newUpdatedToDoList.getId() + " : " + e.getMessage();
            System.err.println(message);
            toDolists.set_message(message);
            toDolists.set_currentResult(Result.ERROR);

            return toDolists;
        }

        toDolists.set_currentResult(Result.OK);
        toDolists.set_message("Update completed");
        return toDolists;
    }

    // Get add one ToDoList in data.json
    public ToDoListServicesResponse addToDoListFromJSON(ToDoList newToDoList) {
        // Getting the json file
        List<ToDoList> toDolists = new ArrayList<ToDoList>();
        ToDoListServicesResponse resp = new ToDoListServicesResponse();
        List<ToDoList> listToResp = new ArrayList<ToDoList>();

        try {
            // Get all toDoLists
            toDolists = getAllToDoListsFromJSON().get_toDoListList();
            // Putting the right id
            newToDoList.setId(toDolists.size() + 1);
            // Insert the new To do list
            toDolists.add(newToDoList);

            // overwrites the content of file
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(jsonFile),
                    StandardCharsets.UTF_8);

            writer.write(JsonUtils.toJson(toDolists).toString());
            writer.close();

        } catch (Exception e) {
            String message = "error while adding todo list " + newToDoList.getId() + " : " + e.getMessage();
            System.err.println(message);
            resp.set_message(message);
            resp.set_currentResult(Result.ERROR);
            listToResp.add(newToDoList);
            resp.set_toDoListList(listToResp);

            return resp;
        }

        listToResp.add(newToDoList);
        resp.set_toDoListList(listToResp);
        resp.set_message("Added new Todo list");
        resp.set_currentResult(Result.OK);
        return resp;
    }

    // Get delete one ToDoList by it's id in data.json
    public ToDoListServicesResponse removeToDoListByIdFromJSON(int listId) {
        // Getting the json file
        List<ToDoList> toDolists = new ArrayList<ToDoList>();
        ToDoList toDoListToRemove = new ToDoList();
        ToDoListServicesResponse resp = new ToDoListServicesResponse();
        List<ToDoList> listToResp = new ArrayList<ToDoList>();

        try {
            // Get all toDoLists
            toDolists = getAllToDoListsFromJSON().get_toDoListList();
            // Get the todo list we want to remove
            toDoListToRemove = getListByIdFromJSON(listId).get_toDoListList().get(0);
            // Removing the todo list
            toDolists.remove(toDoListToRemove);

            // overwrites the content of file
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(jsonFile),
                    StandardCharsets.UTF_8);

            writer.write(JsonUtils.toJson(toDolists).toString());
            writer.close();

        } catch (Exception e) {
            String message = "error cannot remove todo list with the id " + listId + " : " + e.getMessage();
            System.err.println(message);
            resp.set_message(message);
            resp.set_currentResult(Result.ERROR);
            resp.set_toDoListList(listToResp);

            return resp;
        }

        listToResp.add(toDoListToRemove);
        resp.set_toDoListList(listToResp);
        resp.set_message("Removing the todo list number " + toDoListToRemove.getId() + " succesful");
        resp.set_currentResult(Result.OK);
        return resp;
    }
}
