package com.tododo.tododo.services;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.ToDoList;
import com.tododo.tododo.models.servicesResponse.ToDoListServicesResponse;
import com.tododo.tododo.models.servivesRequest.ToDoListRequest;
import com.tododo.tododo.utils.JsonUtils;

public class ToDoListServices {
    // Get all ToDoList in data.json
    public ToDoListServicesResponse getAllToDoListsFromJSON(ToDoListRequest req) {
        // Getting the json file
        ToDoListServicesResponse toDoListResp = new ToDoListServicesResponse();

        try {
            // creating the mapper json to List<ToDoList>
            ObjectMapper objectMapper = new ObjectMapper();
            toDoListResp.set_toDoListList(objectMapper.readValue(
                    JsonUtils.getJsonFile(req.getIsTest()),
                    new TypeReference<List<ToDoList>>() {
                    }));

            if (toDoListResp.get_toDoListList().isEmpty()) {
                toDoListResp.set_message(
                        "TODOLIST GETALL : No todo list found in : "
                                + JsonUtils.getJsonFile(req.getIsTest()).getPath());
                toDoListResp.set_currentResult(Result.NOT_EXISTING);
                return toDoListResp;
            }

            toDoListResp.set_message("Read all Todo lists");
            toDoListResp.set_currentResult(Result.OK);
        } catch (Exception e) {
            String message = "error while parsing all todo lists : " + e.getMessage();
            System.err.println(message);
            toDoListResp.set_message(message);
            toDoListResp.set_currentResult(Result.ERROR);

            return toDoListResp;
        }

        return toDoListResp;
    }

    // Get one ToDoList by it's id in data.json
    public ToDoListServicesResponse getListByIdFromJSON(ToDoListRequest req) {
        // Getting the json file
        ToDoListServicesResponse toDoListResp = new ToDoListServicesResponse();

        try {
            // Getting the one that match the id passed as param
            toDoListResp.set_toDoListList(
                    getAllToDoListsFromJSON(req).get_toDoListList().stream().filter(x -> x.id == req.get_idList())
                            .toList());
            // Setting result reponse
            toDoListResp
                    .set_currentResult(toDoListResp.get_toDoListList().size() == 0 ? Result.NOT_EXISTING : Result.OK);
        } catch (Exception e) {
            String message = "error while getting todo list number : " + req.get_idList() + " - error : "
                    + e.getMessage();
            System.err.println(message);
            toDoListResp.set_message(message);
            toDoListResp.set_currentResult(Result.ERROR);

            return toDoListResp;
        }

        return toDoListResp;
    }

    // Get update one ToDoList by it's id in data.json
    public ToDoListServicesResponse updateToDoListFromJSON(ToDoListRequest req) {
        // Getting the json file
        ToDoListServicesResponse resp = new ToDoListServicesResponse();

        try {
            // Get all toDoLists
            List<ToDoList> currentToDoListList = getAllToDoListsFromJSON(req).get_toDoListList();

            // Getting the task to update
            ToDoList toDoListToUpdate = currentToDoListList.stream()
                    .filter(task -> task.getId() == req.get_idList())
                    .findFirst()
                    .orElse(null);

            if (toDoListToUpdate == null) {
                String message = "TODOLIST UPDATE : ToDoList with ID " + req.get_idList() + " not found or not exist ";
                resp.set_message(message);
                resp.set_currentResult(Result.NOT_EXISTING);
                return resp;
            }

            // Updating task informations
            ToDoList updatedToDoList = req.get_toDoLists().get(0);
            toDoListToUpdate.set_name(updatedToDoList.get_name());
            toDoListToUpdate.setIsCompleted(updatedToDoList.getIsCompleted());
            toDoListToUpdate.set_tasks(updatedToDoList.get_tasks());

            // overwrites the content of file
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(JsonUtils.getJsonFile(req.getIsTest())),
                    StandardCharsets.UTF_8);

            writer.write(JsonUtils.toJson(currentToDoListList).toString());
            writer.close();

            // Setting the response
            resp.set_toDoListList(new ArrayList<ToDoList>(List.of(toDoListToUpdate)));
            resp.set_currentResult(Result.OK);
            resp.set_message("Update completed");
        } catch (Exception e) {
            String message = "error while updating todo list " + req.get_idList() + " : " + e.getMessage();
            System.err.println(message);
            resp.set_message(message);
            resp.set_currentResult(Result.ERROR);
            resp.set_toDoListList(new ArrayList<ToDoList>(List.of(req.get_toDoLists().get(0))));

            return resp;
        }

        return resp;
    }

    // Get add one ToDoList in data.json
    public ToDoListServicesResponse addToDoListFromJSON(ToDoListRequest req) {
        // Getting the json file
        List<ToDoList> toDolists = new ArrayList<ToDoList>();
        ToDoListServicesResponse resp = new ToDoListServicesResponse();
        List<ToDoList> listToResp = new ArrayList<ToDoList>();
        ToDoList newToDoList = req.get_toDoLists().get(0);

        try {
            // Get all toDoLists
            toDolists = getAllToDoListsFromJSON(req).get_toDoListList();
            // Putting the right id
            newToDoList.setId(toDolists.size() + 1);
            // Insert the new To do list
            toDolists.add(newToDoList);

            // overwrites the content of file with new updated ToDoList
            FileWriter fileWriter = new FileWriter(JsonUtils.getJsonFile(req.getIsTest()), StandardCharsets.UTF_8);

            fileWriter.write(JsonUtils.toJson(toDolists).toString());
            fileWriter.flush();
            fileWriter.close();

            // Updating the response
            listToResp.add(newToDoList);
            resp.set_toDoListList(listToResp);
            resp.set_message("Added new Todo list");
            resp.set_currentResult(Result.OK);
        } catch (Exception e) {
            String message = "error while adding todo list " + newToDoList.getId() + " : " + e.getMessage();
            System.err.println(message);
            resp.set_message(message);
            resp.set_currentResult(Result.ERROR);
            listToResp.add(newToDoList);
            resp.set_toDoListList(listToResp);

            return resp;
        }

        return resp;
    }

    // Get delete one ToDoList by it's id in data.json
    public ToDoListServicesResponse deleteToDoListByIdFromJSON(ToDoListRequest req) {
        ToDoListServicesResponse resp = new ToDoListServicesResponse();

        try {
            // Get all toDoLists
            List<ToDoList> currentToDoListList = getAllToDoListsFromJSON(req).get_toDoListList();

            // Getting the task to update
            ToDoList toDoListToDelete = currentToDoListList.stream()
                    .filter(task -> task.getId() == req.get_idList())
                    .findFirst()
                    .orElse(null);

            if (toDoListToDelete == null) {
                String message = "TODOLIST DELETE : ToDoList with ID " + req.get_idList() + " not found or not exist ";
                resp.set_message(message);
                resp.set_currentResult(Result.NOT_EXISTING);
                return resp;
            }

            // Removing the todo list
            currentToDoListList.remove(toDoListToDelete);

            // Rearanging the ids and sorting the list
            currentToDoListList = rearangeToDoListsIds(currentToDoListList);

            // overwrites the content of file with new updated ToDoList
            FileWriter fileWriter = new FileWriter(JsonUtils.getJsonFile(req.getIsTest()), StandardCharsets.UTF_8);

            fileWriter.write(JsonUtils.toJson(currentToDoListList).toString());
            fileWriter.flush();
            fileWriter.close();

            // Setting the response
            resp.set_toDoListList(new ArrayList<ToDoList>(List.of(toDoListToDelete)));
            resp.set_currentResult(Result.OK);
            resp.set_message("Removing the todo list number " + req.get_idList() + " succesful");
        } catch (Exception e) {
            String message = "error cannot remove todo list with the id " + req.get_idList() + " - message : "
                    + e.getMessage();
            System.err.println(message);
            resp.set_message(message);
            resp.set_currentResult(Result.ERROR);
            resp.set_toDoListList(new ArrayList<ToDoList>(List.of(req.get_toDoLists().get(0))));

            return resp;
        }

        return resp;
    }

    // Adding ids to 1 to N and sorting the list by id
    private List<ToDoList> rearangeToDoListsIds(List<ToDoList> toDoListsList) {
        if (!toDoListsList.isEmpty()) {
            for (int i = 0; i <= toDoListsList.size() - 1; i++) {
                toDoListsList.get(i).setId(i + 1);
            }

            Collections.sort(toDoListsList, Comparator.comparingInt(ToDoList::getId));
        }

        return toDoListsList;
    }
}
