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
            // Rearanging tasks ids and sorting the tasks list ascending
            currentToDoList.set_tasks(rearangeTasksIds(currentToDoList.get_tasks()));
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

    // Add a task
    public TaskServicesResponse addTaskFromJSON(TaskServicesRequest req) {
        TaskServicesResponse taskResp = new TaskServicesResponse();
        ToDoList currentToDoList = new ToDoList();
        // 1 if the added task id the first one of the todo list
        int lastId = 1;

        try {
            // getting the todo list to update
            currentToDoList = tdls.getListByIdFromJSON(req.get_idList()).get_toDoListList().get(0);
            // Sort the tasks in descending order based on their id
            Collections.sort(currentToDoList.get_tasks(), Comparator.comparingInt(Task::getId).reversed());

            boolean isEmptyTasks = !currentToDoList.get_tasks().isEmpty();
            // Get the greatest id if the todo list has task(s)
            if (isEmptyTasks)
                lastId = currentToDoList.get_tasks().get(0).getId();

            // Setting the id to the last id + 1 or if it's the first tasks to 1
            req.get_tasks().get(0).setId(isEmptyTasks ? lastId + 1 : lastId);
            // The task is not yet completed
            req.get_tasks().get(0).setIsCompleted(false);
            // Adding the task to the current todo list
            currentToDoList.get_tasks().add(req.get_tasks().get(0));
            // Sorting the tasks in ascending order
            Collections.sort(currentToDoList.get_tasks(), Comparator.comparingInt(Task::getId));
            // update the todo list
            tdls.updateToDoListFromJSON(currentToDoList);
        } catch (Exception e) {
            String message = "error while adding the task from the todo list with the id : "
                    + req.get_idList() + " - message : "
                    + e.getMessage();
            System.err.println(message);
            taskResp.set_message(message);
            taskResp.set_currentResult(Result.ERROR);

            return taskResp;
        }

        taskResp.set_taskList(req.get_tasks());
        taskResp.set_currentResult(Result.OK);
        taskResp.set_message("The task id : " + req.get_tasks().get(0).getId() + " from the todo list with the id : "
                + req.get_idList() + " is added");
        return taskResp;
    }

    // Delete a task
    public TaskServicesResponse deleteTaskFromJSON(TaskServicesRequest req) {
        TaskServicesResponse taskResp = new TaskServicesResponse();
        ToDoList currentToDoList = new ToDoList();

        try {
            // getting the todo list to update
            currentToDoList = tdls.getListByIdFromJSON(req.get_idList()).get_toDoListList().get(0);
            // Remove the task
            currentToDoList.get_tasks().remove(req.get_idTask() - 1);
            // Rearanging tasks ids and sorting the tasks list ascending
            currentToDoList.set_tasks(rearangeTasksIds(currentToDoList.get_tasks()));
            // update the todo list
            tdls.updateToDoListFromJSON(currentToDoList);
        } catch (Exception e) {
            String message = "error while deleting the task id " + req.get_idTask()
                    + " from the todo list with the id : "
                    + req.get_idList() + " - message : "
                    + e.getMessage();
            System.err.println(message);
            taskResp.set_message(message);
            taskResp.set_currentResult(Result.ERROR);

            return taskResp;
        }

        taskResp.set_currentResult(Result.OK);
        taskResp.set_message("The task id : " + req.get_idTask() + " from the todo list with the id : "
                + req.get_idList() + " is deleted");
        return taskResp;
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
