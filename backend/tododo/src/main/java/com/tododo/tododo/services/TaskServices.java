package com.tododo.tododo.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.Task;
import com.tododo.tododo.models.ToDoList;
import com.tododo.tododo.models.servicesResponse.TaskServicesResponse;
import com.tododo.tododo.models.servivesRequest.TaskServicesRequest;
import com.tododo.tododo.models.servivesRequest.ToDoListRequest;

public class TaskServices {
    File jsonFile = new File("src\\main\\resources\\data.json");
    ToDoListServices tdls = new ToDoListServices();

    // Get all tasks by id todo lists from data.json
    public TaskServicesResponse getAllTasksFromJSON(TaskServicesRequest req) {
        TaskServicesResponse resp = new TaskServicesResponse();
        List<ToDoList> allToDoLists = new ArrayList<ToDoList>();

        try {
            // gettin all list the mapper json to List<ToDoList>
            allToDoLists = tdls.getAllToDoListsFromJSON().get_toDoListList();
            resp.set_taskList(
                    allToDoLists.stream().filter(x -> x.id == req.get_idList()).toList().get(0).get_tasks());

            boolean isListEmpty = resp.get_taskList().size() == 0;
            resp.set_currentResult(isListEmpty ? Result.NOT_EXISTING : Result.OK);
            resp.set_message(isListEmpty ? "No task found for the todo list with the id " + req.get_idList() : "");

        } catch (Exception e) {
            String message = "error while parsing all task from the todo list with the id : " + req.get_idList()
                    + " - message : "
                    + e.getMessage();
            System.err.println(message);
            resp.set_message(message);
            resp.set_currentResult(Result.ERROR);

            return resp;
        }

        return resp;
    }

    // Get all tasks by id todo lists from data.json
    public TaskServicesResponse getTaskByIdFromJSON(TaskServicesRequest req) {
        TaskServicesResponse taskResp = new TaskServicesResponse();
        ToDoList currentToDoList = new ToDoList();

        try {
            // Getting the todo list asked
            currentToDoList = tdls.getListByIdFromJSON(new ToDoListRequest(req.get_idList())).get_toDoListList().get(0);
            // Getting the task asked
            taskResp.set_taskList(currentToDoList.get_tasks().stream().filter(x -> x.id == req.get_idTask()).toList());

            boolean isListEmpty = taskResp.get_taskList().size() == 0;
            taskResp.set_currentResult(isListEmpty ? Result.NOT_EXISTING : Result.OK);
            taskResp.set_message(
                    isListEmpty
                            ? "No task with the id : " + req.get_idTask() + " found for the todo list with the id "
                                    + req.get_idList()
                            : "");
        } catch (Exception e) {
            String message = "error while getting task id : " + req.get_idTask() + " from the todo list with the id : "
                    + req.get_idList() + " - message : "
                    + e.getMessage();
            System.err.println(message);
            taskResp.set_message(message);
            taskResp.set_currentResult(Result.ERROR);

            return taskResp;
        }

        return taskResp;
    }

    // Update a task
    public TaskServicesResponse updateTaskFromJSON(TaskServicesRequest req) {
        TaskServicesResponse resp = new TaskServicesResponse();
        ToDoList currentToDoList = new ToDoList();

        try {
            // getting the todo list to update
            currentToDoList = tdls.getListByIdFromJSON(new ToDoListRequest(req.get_idList())).get_toDoListList().get(0);

            // Getting the task to update
            Task taskToUpdate = currentToDoList.get_tasks().stream()
                    .filter(task -> task.getId() == req.get_idTask())
                    .findFirst()
                    .orElse(null);

            if (taskToUpdate == null) {
                String message = "Task with ID " + req.get_idTask() + " not found or not exist from Todo List ID "
                        + req.get_idList();
                System.err.println(message);
                resp.set_message(message);
                resp.set_currentResult(Result.ERROR);
                return resp;
            }

            // Updating task informations
            Task updatedTask = req.get_tasks().get(0);
            taskToUpdate.setTaskContent(updatedTask.getTaskContent());
            taskToUpdate.setIsCompleted(updatedTask.getIsCompleted());
            taskToUpdate.set_subTasks(updatedTask.get_subTasks());

            // Update the todo list
            List<ToDoList> toDoListToUpdate = new ArrayList<>();
            toDoListToUpdate.add(currentToDoList);
            tdls.updateToDoListFromJSON(new ToDoListRequest(req.get_idList(), toDoListToUpdate));

            resp.set_taskList(new ArrayList<Task>(List.of(taskToUpdate)));
            resp.set_currentResult(Result.OK);
            resp.set_message("The task id : " + req.get_idTask() + " from the todo list with the id : "
                    + req.get_idList() + " is updated");
        } catch (Exception e) {
            String message = "error while updating the task id : " + req.get_idTask()
                    + " from the todo list with the id : " + req.get_idList() + " - message : "
                    + e.getMessage();
            System.err.println(message);
            resp.set_message(message);
            resp.set_currentResult(Result.ERROR);
            resp.set_taskList(new ArrayList<Task>(List.of(req.get_tasks().get(0))));

            return resp;
        }

        return resp;
    }

    // Add a task
    public TaskServicesResponse addTaskFromJSON(TaskServicesRequest req) {
        TaskServicesResponse taskResp = new TaskServicesResponse();
        ToDoList currentToDoList = new ToDoList();
        // 1 if the added task id the first one of the todo list
        int lastId = 1;

        try {
            // getting the todo list to update
            currentToDoList = tdls.getListByIdFromJSON(new ToDoListRequest(req.get_idList())).get_toDoListList().get(0);

            boolean isEmptyTasks = currentToDoList.get_tasks().isEmpty();

            // Get the greatest id if the todo list has task(s)
            if (!isEmptyTasks) {
                // Sort the tasks in descending order based on their id
                Collections.sort(currentToDoList.get_tasks(), Comparator.comparingInt(Task::getId).reversed());
                lastId = currentToDoList.get_tasks().get(0).getId();
            }

            // Setting the id to the last id + 1 or if it's the first tasks to 1
            req.get_tasks().get(0).setId(isEmptyTasks ? lastId : lastId + 1);
            // The task is not yet completed
            req.get_tasks().get(0).setIsCompleted(false);
            // Adding the task to the current todo list
            currentToDoList.get_tasks().add(req.get_tasks().get(0));
            // Sorting the tasks in ascending order
            Collections.sort(currentToDoList.get_tasks(), Comparator.comparingInt(Task::getId));
            // update the todo list
            tdls.updateToDoListFromJSON(new ToDoListRequest(new ArrayList<ToDoList>(List.of(currentToDoList))));

            taskResp.set_taskList(req.get_tasks());
            taskResp.set_currentResult(Result.OK);
            taskResp.set_message(
                    "The task id : " + req.get_tasks().get(0).getId() + " from the todo list with the id : "
                            + req.get_idList() + " is added");
        } catch (Exception e) {
            String message = "error while adding the task from the todo list with the id : "
                    + req.get_idList() + " - message : "
                    + e.getMessage();
            System.err.println(message);
            taskResp.set_message(message);
            taskResp.set_currentResult(Result.ERROR);

            return taskResp;
        }

        return taskResp;
    }

    // Delete a task
    public TaskServicesResponse deleteTaskFromJSON(TaskServicesRequest req) {
        TaskServicesResponse resp = new TaskServicesResponse();
        ToDoList currentToDoList = new ToDoList();

        try {
            // getting the todo list to update
            currentToDoList = tdls.getListByIdFromJSON(new ToDoListRequest(req.get_idList())).get_toDoListList().get(0);

            // Getting the task to delete
            Task taskToDelete = currentToDoList.get_tasks().stream()
                    .filter(task -> task.getId() == req.get_idTask())
                    .findFirst()
                    .orElse(null);

            if (taskToDelete == null) {
                String message = "Task with ID " + req.get_idTask() + " not found or not exist from Todo List ID "
                        + req.get_idList();
                System.err.println(message);
                resp.set_message(message);
                resp.set_currentResult(Result.ERROR);
                return resp;
            }

            // Deleting the task
            currentToDoList.get_tasks().remove(taskToDelete);

            // Rearanging tasks ids and sorting the tasks list ascending
            currentToDoList.set_tasks(rearangeTasksIds(currentToDoList.get_tasks()));

            // update the todo list
            tdls.updateToDoListFromJSON(
                    new ToDoListRequest(req.get_idList(), new ArrayList<ToDoList>(List.of(currentToDoList))));

            resp.set_taskList(new ArrayList<Task>(List.of(taskToDelete)));
            resp.set_currentResult(Result.OK);
            resp.set_message("The task id : " + req.get_idTask() + " from the todo list with the id : "
                    + req.get_idList() + " is deleted");
        } catch (Exception e) {
            String message = "error while deleting the task id " + req.get_idTask()
                    + " from the todo list with the id : "
                    + req.get_idList() + " - message : "
                    + e.getMessage();
            System.err.println(message);
            resp.set_message(message);
            resp.set_currentResult(Result.ERROR);
            resp.set_taskList(new ArrayList<Task>(List.of(req.get_tasks().get(0))));

            return resp;
        }

        return resp;
    }

    // Adding ids to 1 to N and sorting the list by id
    private List<Task> rearangeTasksIds(List<Task> taskList) {
        if (!taskList.isEmpty()) {
            for (int i = 0; i <= taskList.size() - 1; i++) {
                taskList.get(i).setId(i + 1);
            }

            Collections.sort(taskList, Comparator.comparingInt(Task::getId));
        }

        return taskList;
    }
}
