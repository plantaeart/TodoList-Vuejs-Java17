package com.tododo.tododo.services;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tododo.tododo.enums.Result;
import com.tododo.tododo.mappers.ToDoListMapper;
import com.tododo.tododo.models.ToDoList;
import com.tododo.tododo.models.dto.ToDoListDTO;
import com.tododo.tododo.models.servicesResponse.ToDoListServicesResponse;
import com.tododo.tododo.models.servivesRequest.ToDoListServicesRequest;
import com.tododo.tododo.utils.JsonUtils;

public class ToDoListServices {
    // Get all ToDoList in data.json
    public ToDoListServicesResponse getAllToDoListsFromJSON(ToDoListServicesRequest req) {
        // Getting the json file
        ToDoListServicesResponse toDoListResp = new ToDoListServicesResponse();

        try {
            // creating the mapper json to List<ToDoList>
            ObjectMapper objectMapper = new ObjectMapper();
            // Get all list from json file
            List<ToDoList> toDoLists = objectMapper.readValue(JsonUtils.getJsonFile(req.getIsTest()),
                    new TypeReference<List<ToDoList>>() {
                    });

            // Convert them in DTO
            List<ToDoListDTO> allToDoListDTO = toDoLists.stream()
                    .map(ToDoListMapper::toDTO)
                    .collect(Collectors.toList());

            toDoListResp.setToDoListList(allToDoListDTO);

            if (toDoListResp.getToDoListList().isEmpty()) {
                toDoListResp.setMessage("TODOLIST GETALL : No todo list found");
                toDoListResp.setCurrentResult(Result.NOT_EXISTING);
                return toDoListResp;
            }

            toDoListResp.setMessage("Read all Todo lists");
            toDoListResp.setCurrentResult(Result.OK);
        } catch (Exception e) {
            String message = "error while parsing all todo lists : " + e.getMessage();
            System.err.println(message);
            toDoListResp.setMessage(message);
            toDoListResp.setCurrentResult(Result.ERROR);

            return toDoListResp;
        }

        return toDoListResp;
    }

    // Get one ToDoList by it's id in data.json
    public ToDoListServicesResponse getToDoListByIdFromJSON(ToDoListServicesRequest req) {
        // Getting the json file
        ToDoListServicesResponse toDoListResp = new ToDoListServicesResponse();

        try {
            // Getting the one that match the id passed as param
            toDoListResp.setToDoListList(
                    getAllToDoListsFromJSON(req).getToDoListList().stream()
                            .filter(x -> x.getId() == req.getIdsList()[0])
                            .toList());
            // Setting result reponse
            toDoListResp
                    .setCurrentResult(toDoListResp.getToDoListList().size() == 0 ? Result.NOT_EXISTING : Result.OK);
        } catch (Exception e) {
            String message = "error while getting todo list number : " + req.getIdsList()[0] + " - error : "
                    + e.getMessage();
            System.err.println(message);
            toDoListResp.setMessage(message);
            toDoListResp.setCurrentResult(Result.ERROR);

            return toDoListResp;
        }

        return toDoListResp;
    }

    // Get update one ToDoList by it's id in data.json
    public ToDoListServicesResponse updateToDoListFromJSON(ToDoListServicesRequest req) {
        // Getting the json file
        ToDoListServicesResponse resp = new ToDoListServicesResponse();

        try {
            // Get all toDoLists
            List<ToDoListDTO> currentToDoListList = getAllToDoListsFromJSON(req).getToDoListList();

            // Getting the task to update
            ToDoListDTO toDoListToUpdate = currentToDoListList.stream()
                    .filter(task -> task.getId() == req.getIdsList()[0])
                    .findFirst()
                    .orElse(null);

            if (toDoListToUpdate == null) {
                String message = "TODOLIST UPDATE : ToDoList with ID " + req.getIdsList()[0]
                        + " not found or not exist ";
                resp.setMessage(message);
                resp.setCurrentResult(Result.NOT_EXISTING);
                return resp;
            }

            // Updating task informations
            ToDoListDTO updatedToDoList = req.getToDoLists().get(0);
            toDoListToUpdate.setName(updatedToDoList.getName());
            toDoListToUpdate.setIsCompleted(updatedToDoList.getIsCompleted());
            if (!updatedToDoList.getTasks().isEmpty())
                toDoListToUpdate.setTasks(updatedToDoList.getTasks());

            // overwrites the content of file
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(JsonUtils.getJsonFile(req.getIsTest())),
                    StandardCharsets.UTF_8);

            writer.write(JsonUtils.toJson(currentToDoListList).toString());
            writer.close();

            // Setting the response
            resp.setToDoListList(new ArrayList<ToDoListDTO>(List.of(toDoListToUpdate)));
            resp.setCurrentResult(Result.OK);
            resp.setMessage("Update completed");
        } catch (Exception e) {
            String message = "error while updating todo list " + req.getIdsList()[0] + " : " + e.getMessage();
            System.err.println(message);
            resp.setMessage(message);
            resp.setCurrentResult(Result.ERROR);
            resp.setToDoListList(new ArrayList<ToDoListDTO>(List.of(req.getToDoLists().get(0))));

            return resp;
        }

        return resp;
    }

    // Get add one ToDoList in data.json
    public ToDoListServicesResponse addToDoListFromJSON(ToDoListServicesRequest req) {
        // Getting the json file
        List<ToDoListDTO> toDolists = new ArrayList<ToDoListDTO>();
        ToDoListServicesResponse resp = new ToDoListServicesResponse();
        List<ToDoListDTO> listToResp = new ArrayList<ToDoListDTO>();
        ToDoListDTO newToDoList = req.getToDoLists().get(0);

        try {
            // Get all toDoLists
            toDolists = getAllToDoListsFromJSON(req).getToDoListList();
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
            resp.setToDoListList(listToResp);
            resp.setMessage("Added new Todo list");
            resp.setCurrentResult(Result.OK);
        } catch (Exception e) {
            String message = "error while adding todo list " + newToDoList.getId() + " : " + e.getMessage();
            System.err.println(message);
            resp.setMessage(message);
            resp.setCurrentResult(Result.ERROR);
            listToResp.add(newToDoList);
            resp.setToDoListList(listToResp);

            return resp;
        }

        return resp;
    }

    // Get delete one ToDoList by it's id in data.json
    public ToDoListServicesResponse deleteToDoListByIdFromJSON(ToDoListServicesRequest req) {
        ToDoListServicesResponse resp = new ToDoListServicesResponse();

        try {
            // Get all toDoLists
            List<ToDoListDTO> currentToDoListList = getAllToDoListsFromJSON(req).getToDoListList();

            // Getting the task to update
            ToDoListDTO toDoListToDelete = currentToDoListList.stream()
                    .filter(task -> task.getId() == req.getIdsList()[0])
                    .findFirst()
                    .orElse(null);

            if (toDoListToDelete == null) {
                String message = "TODOLIST DELETE : ToDoList with ID " + req.getIdsList()[0]
                        + " not found or not exist ";
                resp.setMessage(message);
                resp.setCurrentResult(Result.NOT_EXISTING);
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
            resp.setToDoListList(new ArrayList<ToDoListDTO>(List.of(toDoListToDelete)));
            resp.setCurrentResult(Result.OK);
            resp.setMessage("Removing the todo list number " + req.getIdsList()[0] + " succesful");
        } catch (Exception e) {
            String message = "error cannot remove todo list with the id " + req.getIdsList()[0] + " - message : "
                    + e.getMessage();
            System.err.println(message);
            resp.setMessage(message);
            resp.setCurrentResult(Result.ERROR);
            resp.setToDoListList(new ArrayList<ToDoListDTO>(List.of(req.getToDoLists().get(0))));

            return resp;
        }

        return resp;
    }

    // Adding ids to 1 to N and sorting the list by id
    private List<ToDoListDTO> rearangeToDoListsIds(List<ToDoListDTO> toDoListsList) {
        if (!toDoListsList.isEmpty()) {
            for (int i = 0; i <= toDoListsList.size() - 1; i++) {
                toDoListsList.get(i).setId(i + 1);
            }

            Collections.sort(toDoListsList, Comparator.comparingInt(ToDoListDTO::getId));
        }

        return toDoListsList;
    }
}
