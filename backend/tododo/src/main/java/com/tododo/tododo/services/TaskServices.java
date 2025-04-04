package com.tododo.tododo.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.dto.TaskDTO;
import com.tododo.tododo.models.dto.ToDoListDTO;
import com.tododo.tododo.models.servicesResponse.TaskServicesResponse;
import com.tododo.tododo.models.servivesRequest.TaskServicesRequest;
import com.tododo.tododo.models.servivesRequest.ToDoListServicesRequest;

public class TaskServices {
        ToDoListServices tdls = new ToDoListServices();

        // Get all tasks by id todo lists from data.json
        public TaskServicesResponse getAllTasksFromJSON(int[] idsList, boolean isTest) {
                TaskServicesResponse resp = new TaskServicesResponse();
                List<ToDoListDTO> allToDoLists = new ArrayList<ToDoListDTO>();

                try {
                        // gettin all list the mapper json to List<ToDoList>
                        allToDoLists = tdls.getAllToDoListsFromJSON(isTest).gettoDoLists();
                        resp.setTaskList(
                                        allToDoLists.stream().filter(x -> x.getId() == idsList[0]).toList()
                                                        .get(0)
                                                        .getTasks());

                        boolean isListEmpty = resp.getTaskList().size() == 0;
                        resp.setCurrentResult(isListEmpty ? Result.NOT_EXISTING : Result.OK);
                        resp.setMessage(isListEmpty
                                        ? "No task found for the todo list with the id " + idsList[0]
                                        : "");

                } catch (Exception e) {
                        String message = "error while parsing all task from the todo list with the id : "
                                        + idsList[0]
                                        + " - message : "
                                        + e.getMessage();
                        System.err.println(message);
                        resp.setMessage(message);
                        resp.setCurrentResult(Result.ERROR);

                        return resp;
                }

                return resp;
        }

        // Get all tasks by id todo lists from data.json
        public TaskServicesResponse getTaskByIdFromJSON(int[] idsList, int[] idsTask,
                        boolean isTest) {
                TaskServicesResponse taskResp = new TaskServicesResponse();
                ToDoListDTO currentToDoList = new ToDoListDTO();

                try {
                        // Getting the todo list asked
                        currentToDoList = tdls
                                        .getToDoListByIdFromJSON(
                                                        idsList, isTest)
                                        .gettoDoLists().get(0);
                        // Getting the task asked
                        taskResp.setTaskList(
                                        currentToDoList.getTasks().stream()
                                                        .filter(x -> x.getId() == idsTask[0])
                                                        .toList());

                        boolean isListEmpty = taskResp.getTaskList().size() == 0;
                        taskResp.setCurrentResult(isListEmpty ? Result.NOT_EXISTING : Result.OK);
                        taskResp.setMessage(
                                        isListEmpty
                                                        ? "No task with the id : " + idsTask[0]
                                                                        + " found for the todo list with the id "
                                                                        + idsList[0]
                                                        : "");
                } catch (Exception e) {
                        String message = "error while getting task id : " + idsTask[0]
                                        + " from the todo list with the id : "
                                        + idsList[0] + " - message : "
                                        + e.getMessage();
                        System.err.println(message);
                        taskResp.setMessage(message);
                        taskResp.setCurrentResult(Result.ERROR);

                        return taskResp;
                }

                return taskResp;
        }

        // Update a task
        public TaskServicesResponse updateTaskByIdFromJSON(TaskServicesRequest req) {
                TaskServicesResponse resp = new TaskServicesResponse();
                ToDoListDTO currentToDoList = new ToDoListDTO();

                try {
                        // getting the todo list
                        currentToDoList = tdls
                                        .getToDoListByIdFromJSON(
                                                        req.getIdsList(), req.getIsTest())
                                        .gettoDoLists().get(0);

                        // Getting the task to update
                        TaskDTO taskToUpdate = currentToDoList.getTasks().stream()
                                        .filter(task -> task.getId() == req.getIdsTask()[0])
                                        .findFirst()
                                        .orElse(null);

                        if (taskToUpdate == null) {
                                String message = "TASK UPDATE : Task with ID " + req.getIdsTask()[0]
                                                + " not found or not exist from Todo List ID "
                                                + req.getIdsList()[0];
                                System.err.println(message);
                                resp.setMessage(message);
                                resp.setCurrentResult(Result.NOT_EXISTING);
                                return resp;
                        }

                        // Updating task informations
                        TaskDTO updatedTask = req.getTasks().get(0);
                        taskToUpdate.setTaskDTO(updatedTask);

                        // update the todo list
                        tdls.updateToDoListByIdFromJSON(
                                        new ToDoListServicesRequest(req.getIdsList(), List.of(currentToDoList),
                                                        req.getIsTest()));

                        resp.setTaskList(List.of(taskToUpdate));
                        resp.setCurrentResult(Result.OK);
                        resp.setMessage("The task id : " + req.getIdsTask()[0] + " from the todo list with the id : "
                                        + req.getIdsList()[0] + " is updated");
                } catch (Exception e) {
                        String message = "error while updating the task id : " + req.getIdsTask()[0]
                                        + " from the todo list with the id : " + req.getIdsList()[0] + " - message : "
                                        + e.getMessage();
                        System.err.println(message);
                        resp.setMessage(message);
                        resp.setCurrentResult(Result.ERROR);
                        resp.setTaskList(List.of(req.getTasks().get(0)));

                        return resp;
                }

                return resp;
        }

        // Add a task
        public TaskServicesResponse addTaskFromJSON(TaskServicesRequest req) {
                TaskServicesResponse taskResp = new TaskServicesResponse();
                ToDoListDTO currentToDoList = new ToDoListDTO();
                // 1 if the added task id the first one of the todo list
                int lastId = 1;

                try {
                        // getting the todo list
                        currentToDoList = tdls
                                        .getToDoListByIdFromJSON(
                                                        req.getIdsList(), req.getIsTest())
                                        .gettoDoLists().get(0);

                        boolean isEmptyTasks = currentToDoList.getTasks().isEmpty();
                        // Get the greatest id if the todo list has task(s)
                        if (!isEmptyTasks) {
                                // Sort the tasks in descending order based on their id
                                Collections.sort(currentToDoList.getTasks(),
                                                Comparator.comparingInt(TaskDTO::getId).reversed());
                                lastId = currentToDoList.getTasks().get(0).getId();
                        }

                        // Setting the id to the last id + 1 or if it's the first tasks to 1
                        req.getTasks().get(0).setId(isEmptyTasks ? lastId : lastId + 1);
                        // The task is not yet completed
                        req.getTasks().get(0).setIsCompleted(false);
                        // Adding the task to the current todo list
                        currentToDoList.getTasks().add(req.getTasks().get(0));
                        // Sorting the tasks in ascending order
                        Collections.sort(currentToDoList.getTasks(), Comparator.comparingInt(TaskDTO::getId));
                        // update the todo list
                        tdls.updateToDoListByIdFromJSON(
                                        new ToDoListServicesRequest(req.getIdsList(), List.of(currentToDoList),
                                                        req.getIsTest()));

                        taskResp.setTaskList(req.getTasks());
                        taskResp.setCurrentResult(Result.OK);
                        taskResp.setMessage(
                                        "The task id : " + req.getTasks().get(0).getId()
                                                        + " from the todo list with the id : "
                                                        + req.getIdsList()[0] + " is added");
                } catch (Exception e) {
                        String message = "error while adding the task from the todo list with the id : "
                                        + req.getIdsList()[0] + " - message : "
                                        + e.getMessage();
                        System.err.println(message);
                        taskResp.setMessage(message);
                        taskResp.setCurrentResult(Result.ERROR);

                        return taskResp;
                }

                return taskResp;
        }

        // Delete a task
        public TaskServicesResponse deleteTaskByIdFromJSON(int[] idsList, int[] idsTask,
                        boolean isTest) {
                TaskServicesResponse resp = new TaskServicesResponse();
                ToDoListDTO currentToDoList = new ToDoListDTO();
                TaskDTO taskToDelete = new TaskDTO();

                try {
                        // getting the todo list
                        currentToDoList = tdls
                                        .getToDoListByIdFromJSON(
                                                        idsList, isTest)
                                        .gettoDoLists().get(0);

                        // Getting the task to delete
                        taskToDelete = currentToDoList.getTasks().stream()
                                        .filter(task -> task.getId() == idsTask[0])
                                        .findFirst()
                                        .orElse(null);

                        if (taskToDelete == null) {
                                String message = "TASK DELETE : Task with ID " + idsTask[0]
                                                + " not found or not exist from Todo List ID "
                                                + idsList[0];
                                System.err.println(message);
                                resp.setMessage(message);
                                resp.setCurrentResult(Result.NOT_EXISTING);
                                return resp;
                        }

                        // Deleting the task
                        currentToDoList.getTasks().remove(taskToDelete);

                        // Rearanging tasks ids and sorting the tasks list ascending
                        currentToDoList.setTasks(rearangeTasksIds(currentToDoList.getTasks()));

                        // update the todo list
                        tdls.updateToDoListByIdFromJSON(
                                        new ToDoListServicesRequest(idsList, List.of(currentToDoList),
                                                        isTest));

                        resp.setTaskList(List.of(taskToDelete));
                        resp.setCurrentResult(Result.OK);
                        resp.setMessage("The task id : " + idsTask[0] + " from the todo list with the id : "
                                        + idsList[0] + " is deleted");
                } catch (Exception e) {
                        String message = "error while deleting the task id " + idsTask[0]
                                        + " from the todo list with the id : "
                                        + idsList[0] + " - message : "
                                        + e.getMessage();
                        System.err.println(message);
                        resp.setMessage(message);
                        resp.setCurrentResult(Result.ERROR);
                        resp.setTaskList(List.of(taskToDelete));

                        return resp;
                }

                return resp;
        }

        // Switch a task with another one
        public TaskServicesResponse switchTaskPositionFromJSON(TaskServicesRequest req) {
                TaskServicesResponse resp = new TaskServicesResponse();
                ToDoListDTO currentToDoList = new ToDoListDTO();

                try {
                        // getting the todo list
                        currentToDoList = tdls
                                        .getToDoListByIdFromJSON(
                                                        req.getIdsList(), req.getIsTest())
                                        .gettoDoLists().get(0);

                        // Getting the first task to switch
                        TaskDTO taskToSwitch1 = currentToDoList.getTasks().stream()
                                        .filter(task -> task.getId() == req.getIdsTask()[0])
                                        .findFirst()
                                        .orElse(null);

                        if (taskToSwitch1 == null) {
                                String message = "TASK SWITCH : Task with ID " + req.getIdsTask()[0]
                                                + " not found or not exist from Todo List ID "
                                                + req.getIdsList()[0];
                                System.err.println(message);
                                resp.setMessage(message);
                                resp.setCurrentResult(Result.NOT_EXISTING);
                                return resp;
                        }

                        // Getting the second task to switch
                        TaskDTO taskToSwitch2 = currentToDoList.getTasks().stream()
                                        .filter(task -> task.getId() == req.getIdsTask()[1])
                                        .findFirst()
                                        .orElse(null);

                        if (taskToSwitch2 == null) {
                                String message = "TASK SWITCH : Task with ID " + req.getIdsTask()[1]
                                                + " not found or not exist from Todo List ID "
                                                + req.getIdsList()[0];
                                System.err.println(message);
                                resp.setMessage(message);
                                resp.setCurrentResult(Result.NOT_EXISTING);
                                return resp;
                        }

                        int tempId = taskToSwitch1.getId();
                        // Switching the tasks ids
                        taskToSwitch1.setId(taskToSwitch2.getId());
                        taskToSwitch2.setId(tempId);

                        // Immediately update the tasks list in currentToDoList
                        currentToDoList.setTasks(currentToDoList.getTasks().stream()
                                        .sorted(Comparator.comparingInt(TaskDTO::getId))
                                        .toList());

                        // update the todo list
                        tdls.updateToDoListByIdFromJSON(
                                        new ToDoListServicesRequest(req.getIdsList(), List.of(currentToDoList),
                                                        req.getIsTest()));

                        resp.setTaskList(List.of(taskToSwitch2, taskToSwitch1));
                        resp.setCurrentResult(Result.OK);
                        resp.setMessage("The task 1 and 2 had their ids switched from the todo list with the id : "
                                        + req.getIdsList()[0]);
                } catch (Exception e) {
                        String message = "error while switching the task 1 id : " + req.getIdsTask()[0]
                                        + " and the task 2 id : " + req.getIdsTask()[1]
                                        + " from the todo list with the id : " + req.getIdsList()[0] + " - message : "
                                        + e.getMessage();
                        System.err.println(message);
                        resp.setMessage(message);
                        resp.setCurrentResult(Result.ERROR);
                        resp.setTaskList(List.of(req.getTasks().get(0)));

                        return resp;
                }

                return resp;
        }

        // Adding ids to 1 to N and sorting the list by id
        private List<TaskDTO> rearangeTasksIds(List<TaskDTO> tasks) {
                if (!tasks.isEmpty()) {
                        for (int i = 0; i <= tasks.size() - 1; i++) {
                                tasks.get(i).setId(i + 1);
                        }

                        Collections.sort(tasks, Comparator.comparingInt(TaskDTO::getId));
                }

                return tasks;
        }
}
