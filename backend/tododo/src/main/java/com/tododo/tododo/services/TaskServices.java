package com.tododo.tododo.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.Task;
import com.tododo.tododo.models.ToDoList;
import com.tododo.tododo.models.servicesResponse.TaskServicesResponse;
import com.tododo.tododo.models.servivesRequest.TaskServicesRequest;

public class TaskServices {
    File jsonFile = new File("src\\main\\resources\\data.json");
    ToDoListServices tdls = new ToDoListServices();

    // Get all tasks by id todo lists from data.json
    public TaskServicesResponse getAllTasksFromJSON(int idList) {
        TaskServicesResponse taskResp = new TaskServicesResponse();
        List<ToDoList> allToDoLists = new ArrayList<ToDoList>();

        try {
            // gettin all list the mapper json to List<ToDoList>
            allToDoLists = tdls.getAllToDoListsFromJSON().get_toDoListList();
            taskResp.set_taskList(
                    allToDoLists.stream().filter(x -> x.id == idList).toList().get(0).get_tasks());

        } catch (Exception e) {
            String message = "error while parsing all task from the todo list with the id : " + idList + " - message : "
                    + e.getMessage();
            System.err.println(message);
            taskResp.set_message(message);
            taskResp.set_currentResult(Result.ERROR);

            return taskResp;
        }

        boolean isListEmpty = taskResp.get_taskList().size() == 0;
        taskResp.set_currentResult(isListEmpty ? Result.NOT_EXISTING : Result.OK);
        taskResp.set_message(isListEmpty ? "No task found for the todo list with the id " + idList : "");
        return taskResp;
    }

    // Update a task
    public TaskServicesResponse updateTaskFromJSON(TaskServicesRequest req) {
        TaskServicesResponse taskResp = new TaskServicesResponse();
        ToDoList currentToDoList = new ToDoList();
        Task oldTask = new Task();
        int currentIndex = 0;

        try {
            // getting the todo list to update
            currentToDoList = tdls.getListByIdFromJSON(req.get_idList()).get_toDoListList().get(0);
            // getting the task to update
            oldTask = currentToDoList.get_tasks().stream()
                    .filter(x -> x.id == req.get_idTask()).toList().get(0);
            // getting it's index
            currentIndex = currentToDoList.get_tasks().indexOf(oldTask);
            // Removing the old task and adding the new one
            currentToDoList.get_tasks().remove(currentIndex);
            currentToDoList.get_tasks().add(currentIndex, req.get_tasks().get(0));
            // update the todo list
            tdls.updateToDoListFromJSON(currentToDoList);
        } catch (Exception e) {
            String message = "error while updating the task id : " + req.get_idTask()
                    + " from the todo list with the id : " + req.get_idList() + " - message : "
                    + e.getMessage();
            System.err.println(message);
            taskResp.set_message(message);
            taskResp.set_currentResult(Result.ERROR);

            return taskResp;
        }

        taskResp.set_taskList(req.get_tasks());
        taskResp.set_currentResult(Result.OK);
        taskResp.set_message("The task id : " + req.get_idTask() + " from the todo list with the id : "
                + req.get_idList() + " is updated");
        return taskResp;
    }

}
