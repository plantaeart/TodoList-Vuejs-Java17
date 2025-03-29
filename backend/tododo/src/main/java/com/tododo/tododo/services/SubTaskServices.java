package com.tododo.tododo.services;

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

    TaskServices ts = new TaskServices();

    // Get a SubTask
    public SubTaskServicesResponse getSubTaskByIdFromJSON(SubTaskServicesRequest req) {
        SubTaskServicesResponse resp = new SubTaskServicesResponse();

        try {
            // Retrieve the task containing the subtask
            Task currentTask = ts
                    .getTaskByIdFromJSON(new TaskServicesRequest(req.get_idList(), req.get_idTask(), req.getIsTest()))
                    .get_taskList().get(0);

            // Find the subtask by its ID
            SubTask subTask = currentTask.get_subTasks().stream()
                    .filter(st -> st.getId() == req.get_idSubTask())
                    .findFirst()
                    .orElse(null);

            if (subTask == null) {
                String message = "SubTask with ID " + req.get_subTasks().get(0).getId() + " not found in Task ID "
                        + req.get_idTask() + " from Todo List ID " + req.get_idList();
                System.err.println(message);
                resp.set_message(message);
                resp.set_currentResult(Result.ERROR);
                return resp;
            }

            // Set the retrieved subtask in the response
            List<SubTask> subTaskList = new ArrayList<>();
            subTaskList.add(subTask);
            resp.set_subTaskList(subTaskList);
            resp.set_currentResult(Result.OK);
            resp.set_message("SubTask with ID " + subTask.getId() + " retrieved successfully from Task ID "
                    + currentTask.getId() + " in Todo List ID " + req.get_idList());
        } catch (Exception e) {
            String message = "Error while retrieving SubTask with ID " + req.get_subTasks().get(0).getId()
                    + " from Task ID " + req.get_idTask() + " in Todo List ID " + req.get_idList()
                    + " - message: " + e.getMessage();
            System.err.println(message);
            resp.set_message(message);
            resp.set_currentResult(Result.ERROR);
            return resp;
        }

        return resp;
    }

    // Add a subTask
    public SubTaskServicesResponse addSubTaskFromJSON(SubTaskServicesRequest req) {
        SubTaskServicesResponse resp = new SubTaskServicesResponse();
        Task currentTask = new Task();
        // 1 if the added task id the first one of the todo list
        int lastId = 1;

        try {
            // getting the task where we want to add a subTask
            currentTask = ts
                    .getTaskByIdFromJSON(new TaskServicesRequest(req.get_idList(), req.get_idTask(), req.getIsTest()))
                    .get_taskList().get(0);
            boolean isEmptySubTasks = currentTask.get_subTasks().isEmpty();
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
            ts.updateTaskFromJSON(
                    new TaskServicesRequest(req.get_idList(), req.get_idTask(), taskToUpdate, req.getIsTest()));
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

    // Delete a SubTask
    public SubTaskServicesResponse deleteSubTaskFromJSON(SubTaskServicesRequest req) {
        SubTaskServicesResponse resp = new SubTaskServicesResponse();

        try {
            // Récupérer la tâche contenant la sous-tâche à supprimer
            Task currentTask = ts
                    .getTaskByIdFromJSON(new TaskServicesRequest(req.get_idList(), req.get_idTask(), req.getIsTest()))
                    .get_taskList().get(0);

            // Trouver la sous-tâche à supprimer par son ID
            SubTask subTaskToDelete = currentTask.get_subTasks().stream()
                    .filter(subTask -> subTask.getId() == req.get_idSubTask())
                    .findFirst()
                    .orElse(null);

            if (subTaskToDelete == null) {
                String message = "SUBTASK DELETE : SubTask with ID " + req.get_idSubTask()
                        + " not found or not exist in Task ID "
                        + req.get_idTask() + " from Todo List ID " + req.get_idList();
                System.err.println(message);
                resp.set_message(message);
                resp.set_currentResult(Result.NOT_EXISTING);
                return resp;
            }

            // Supprimer la sous-tâche
            currentTask.get_subTasks().remove(subTaskToDelete);

            // Rearanger les IDs des sous-tâches et trier la liste par ID
            currentTask.set_subTasks(rearangeSubTasksIds(currentTask.get_subTasks()));

            // Mettre à jour la liste des tâches
            List<Task> taskToUpdate = new ArrayList<>();
            taskToUpdate.add(currentTask);
            ts.updateTaskFromJSON(
                    new TaskServicesRequest(req.get_idList(), req.get_idTask(), taskToUpdate, req.getIsTest()));

            resp.set_subTaskList(new ArrayList<SubTask>(List.of(subTaskToDelete)));
            resp.set_currentResult(Result.OK);
            resp.set_message("SubTask with ID " + subTaskToDelete.getId() + " from Task ID " + currentTask.getId()
                    + " in Todo List ID " + req.get_idList() + " has been deleted.");
        } catch (Exception e) {
            String message = "Error while deleting SubTask with ID " + req.get_subTasks().get(0).getId()
                    + " from Task ID " + req.get_idTask() + " in Todo List ID " + req.get_idList()
                    + " - message: " + e.getMessage();
            System.err.println(message);
            resp.set_message(message);
            resp.set_currentResult(Result.ERROR);
            resp.set_subTaskList(new ArrayList<SubTask>(List.of(req.get_subTasks().get(0))));

            return resp;
        }

        return resp;
    }

    // Update a SubTask
    public SubTaskServicesResponse updateSubTaskFromJSON(SubTaskServicesRequest req) {
        SubTaskServicesResponse resp = new SubTaskServicesResponse();

        try {
            // Récupérer la tâche contenant la sous-tâche à mettre à jour
            Task currentTask = ts
                    .getTaskByIdFromJSON(new TaskServicesRequest(req.get_idList(), req.get_idTask(), req.getIsTest()))
                    .get_taskList().get(0);

            // Getting the subTask to update by its ID
            SubTask subTaskToUpdate = currentTask.get_subTasks().stream()
                    .filter(subTask -> subTask.getId() == req.get_idSubTask())
                    .findFirst()
                    .orElse(null);

            if (subTaskToUpdate == null) {
                String message = "SUBTASK UPDATE : SubTask with ID " + req.get_idSubTask()
                        + " not found or not exist in Task ID "
                        + req.get_idTask() + " from Todo List ID " + req.get_idList();
                System.err.println(message);
                resp.set_message(message);
                resp.set_currentResult(Result.NOT_EXISTING);
                return resp;
            }

            // Update the subTask informations
            SubTask updatedSubTask = req.get_subTasks().get(0);
            subTaskToUpdate.setTaskContent(updatedSubTask.getTaskContent());
            subTaskToUpdate.setIsCompleted(updatedSubTask.getIsCompleted());

            // Mettre à jour la liste des tâches
            List<Task> taskToUpdate = new ArrayList<>();
            taskToUpdate.add(currentTask);
            ts.updateTaskFromJSON(
                    new TaskServicesRequest(req.get_idList(), req.get_idTask(), taskToUpdate, req.getIsTest()));

            resp.set_subTaskList(new ArrayList<SubTask>(List.of(subTaskToUpdate)));
            resp.set_currentResult(Result.OK);
            resp.set_message("SubTask with ID " + subTaskToUpdate.getId() + " from Task ID " + currentTask.getId()
                    + " in Todo List ID " + req.get_idList() + " has been updated.");
        } catch (Exception e) {
            String message = "Error while updating SubTask with ID " + req.get_subTasks().get(0).getId()
                    + " from Task ID " + req.get_idTask() + " in Todo List ID " + req.get_idList()
                    + " - message: " + e.getMessage();
            System.err.println(message);
            resp.set_message(message);
            resp.set_currentResult(Result.ERROR);
            resp.set_subTaskList(new ArrayList<SubTask>(List.of(req.get_subTasks().get(0))));

            return resp;
        }

        return resp;
    }

    // Adding ids to 1 to N and sorting the list by id
    private List<SubTask> rearangeSubTasksIds(List<SubTask> subTaskList) {
        if (!subTaskList.isEmpty()) {
            for (int i = 0; i <= subTaskList.size() - 1; i++) {
                subTaskList.get(i).setId(i + 1);
            }

            Collections.sort(subTaskList, Comparator.comparingInt(SubTask::getId));
        }

        return subTaskList;
    }
}
