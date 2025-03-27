package com.tododo.tododo.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.SubTask;
import com.tododo.tododo.models.Task;
import com.tododo.tododo.models.servicesResponse.SubTaskServicesResponse;
import com.tododo.tododo.models.servivesRequest.SubTaskServicesRequest;
import com.tododo.tododo.models.servivesRequest.TaskServicesRequest;

public class SubTaskServices {

    File jsonFile = new File("src\\main\\resources\\data.json");
    TaskServices ts = new TaskServices();

    // Add a task
    public SubTaskServicesResponse addTaskFromJSON(SubTaskServicesRequest req) {
        SubTaskServicesResponse resp = new SubTaskServicesResponse();
        Task currentTask = new Task();
        // 1 if the added task id the first one of the todo list
        int lastId = 1;

        try {
            // getting the task where we want to add a subTask
            currentTask = ts.getTaskByIdFromJSON(new TaskServicesRequest(req.get_idList(), req.get_idTask(), null))
                    .get_taskList().get(0);
            boolean isEmptySubTasks = currentTask.get_subTasks().isEmpty();
            System.err.println(isEmptySubTasks);
            // Get the greatest id if the todo list has task(s)
            if (!isEmptySubTasks) {
                // Sort the subTasks in descending order based on their id
                Collections.sort(currentTask.get_subTasks(), Comparator.comparingInt(SubTask::getId).reversed());
                lastId = currentTask.get_subTasks().get(0).getId();
            }

            // Setting the id to the last id + 1 or if it's the first tasks to 1
            req.get_subTasks().get(0).setId(isEmptySubTasks ? lastId : lastId + 1);
            // The task is not yet completed
            req.get_subTasks().get(0).setIsCompleted(false);
            // Adding the subTask to the current task
            currentTask.get_subTasks().add(req.get_subTasks().get(0));
            // Sorting the tasks in ascending order
            Collections.sort(currentTask.get_subTasks(), Comparator.comparingInt(SubTask::getId));
            // Creating a List<Task> for request creation
            List<Task> taskToUpdate = new ArrayList<Task>();
            taskToUpdate.add(currentTask);
            resp.set_subTaskList(req.get_subTasks());
            // update the todo list
            ts.updateTaskFromJSON(new TaskServicesRequest(req.get_idList(), req.get_idTask(), taskToUpdate));
        } catch (Exception e) {
            String message = "error while adding the subTask from task id : " + req.get_idTask()
                    + " and from the todo list with the id : "
                    + req.get_idList() + " - message : "
                    + e.getMessage();
            System.err.println(message);
            resp.set_message(message);
            resp.set_currentResult(Result.ERROR);

            return resp;
        }

        resp.set_currentResult(Result.OK);
        resp.set_message("The subTask id : " + req.get_subTasks().get(0).getId() + " from the task id : "
                + currentTask.getId() + " from the todo list with the id : "
                + req.get_idList() + " is added");
        return resp;
    }
}
