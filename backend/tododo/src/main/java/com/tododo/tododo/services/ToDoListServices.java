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

    // Parsing data.json to List[]
    public ToDoListServicesResponse getAllToDoListsFromJSON() {
        // Getting the json file
        ToDoListServicesResponse toDoListsResp = new ToDoListServicesResponse();

        try {
            // creating the mapper json to List<ToDoList>
            ObjectMapper objectMapper = new ObjectMapper();
            toDoListsResp.setToDoListList(objectMapper.readValue(
                    jsonFile,
                    new TypeReference<List<ToDoList>>() {
                    }));

        } catch (Exception e) {
            String message = "error while parsing all todo lists : " + e.getMessage();
            System.err.println(message);
            toDoListsResp.setMessage(message);
            toDoListsResp.setCurrentResult(Result.ERROR);

            return toDoListsResp;
        }

        toDoListsResp.setCurrentResult(Result.OK);
        return toDoListsResp;
    }

    public ToDoListServicesResponse getListByIdFromJSON(int listId) {
        // Getting the json file
        ToDoListServicesResponse toDoListResp = new ToDoListServicesResponse();

        try {
            // Getting the one that match the id passed as param
            toDoListResp.setToDoListList(
                    getAllToDoListsFromJSON().getToDoListList().stream().filter(x -> x.id == listId).toList());

        } catch (Exception e) {
            String message = "error while getting todo list number : " + listId + " - error : " + e.getMessage();
            System.err.println(message);
            toDoListResp.setMessage(message);
            toDoListResp.setCurrentResult(Result.ERROR);

            return toDoListResp;
        }

        toDoListResp.setCurrentResult(toDoListResp.getToDoListList().size() == 0 ? Result.NOT_EXISTING : Result.OK);
        return toDoListResp;
    }

    public ToDoListServicesResponse updateToDoListFromJSON(ToDoList newUpdatedToDoList) {
        // Getting the json file
        ToDoListServicesResponse toDolists = new ToDoListServicesResponse();
        ToDoList oldToDoList = new ToDoList();
        int currentIndex = 0;

        try {
            // Get all toDoLists
            toDolists.setToDoListList(getAllToDoListsFromJSON().getToDoListList());
            // Get the old one and it's index
            oldToDoList = getListByIdFromJSON(newUpdatedToDoList.getId()).getToDoListList().get(0);
            currentIndex = toDolists.getToDoListList().indexOf(oldToDoList);
            // Remove the old one
            toDolists.getToDoListList().remove(currentIndex);
            // Insert the new one at the current old one position
            toDolists.getToDoListList().add(currentIndex, newUpdatedToDoList);

            // overwrites the content of file
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(jsonFile),
                    StandardCharsets.UTF_8);

            writer.write(JsonUtils.toJson(toDolists.getToDoListList()).toString());
            writer.close();
        } catch (Exception e) {
            String message = "error while updating todo list " + newUpdatedToDoList.getId() + " : " + e.getMessage();
            System.err.println(message);
            toDolists.setMessage(message);
            toDolists.setCurrentResult(Result.ERROR);

            return toDolists;
        }

        toDolists.setCurrentResult(Result.OK);
        toDolists.setMessage("Update completed");
        return toDolists;
    }

    public ToDoListServicesResponse addToDoListFromJSON(ToDoList newToDoList) {
        // Getting the json file
        List<ToDoList> toDolists = new ArrayList<ToDoList>();
        ToDoListServicesResponse resp = new ToDoListServicesResponse();
        List<ToDoList> listToResp = new ArrayList<ToDoList>();

        try {
            // Get all toDoLists
            toDolists = getAllToDoListsFromJSON().getToDoListList();
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
            resp.setMessage(message);
            resp.setCurrentResult(Result.ERROR);
            listToResp.add(newToDoList);
            resp.setToDoListList(listToResp);

            return resp;
        }

        listToResp.add(newToDoList);
        resp.setToDoListList(listToResp);
        resp.setMessage("Added new Todo list");
        resp.setCurrentResult(Result.OK);
        return resp;
    }

    public ToDoListServicesResponse removeToDoListByIdFromJSON(int listId) {
        // Getting the json file
        List<ToDoList> toDolists = new ArrayList<ToDoList>();
        ToDoList toDoListToRemove = new ToDoList();
        ToDoListServicesResponse resp = new ToDoListServicesResponse();
        List<ToDoList> listToResp = new ArrayList<ToDoList>();

        try {
            // Get all toDoLists
            toDolists = getAllToDoListsFromJSON().getToDoListList();
            // Get the todo list we want to remove
            toDoListToRemove = getListByIdFromJSON(listId).getToDoListList().get(0);
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
            resp.setMessage(message);
            resp.setCurrentResult(Result.ERROR);
            resp.setToDoListList(listToResp);

            return resp;
        }

        listToResp.add(toDoListToRemove);
        resp.setToDoListList(listToResp);
        resp.setMessage("Removing the todo list number " + toDoListToRemove.getId() + " succesful");
        resp.setCurrentResult(Result.OK);
        return resp;
    }
}
