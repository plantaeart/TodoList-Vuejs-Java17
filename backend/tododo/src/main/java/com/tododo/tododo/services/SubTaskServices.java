package com.tododo.tododo.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.dto.SubTaskDTO;
import com.tododo.tododo.models.dto.TaskDTO;
import com.tododo.tododo.models.servicesResponse.SubTaskServicesResponse;
import com.tododo.tododo.models.servivesRequest.SubTaskServicesRequest;
import com.tododo.tododo.models.servivesRequest.TaskServicesRequest;

public class SubTaskServices {

        TaskServices ts = new TaskServices();

        // Get a SubTask
        public SubTaskServicesResponse getSubTaskByIdFromJSON(int[] idsList, int[] idsTask, int[] idsSubTask,
                        boolean isTest) {
                SubTaskServicesResponse resp = new SubTaskServicesResponse();

                try {
                        // Getting the current task
                        TaskDTO currentTask = ts
                                        .getTaskByIdFromJSON(idsList, idsTask,
                                                        isTest)
                                        .getTasks().get(0);

                        // Find the subtask by its ID
                        SubTaskDTO subTask = currentTask.getSubTasks().stream()
                                        .filter(st -> st.getId() == idsSubTask[0])
                                        .findFirst()
                                        .orElse(null);

                        if (subTask == null) {
                                String message = "SubTask with ID " + idsSubTask[0]
                                                + " not found in Task ID "
                                                + idsTask[0] + " from Todo List ID " + idsList[0];
                                System.err.println(message);
                                resp.setMessage(message);
                                resp.setCurrentResult(Result.ERROR);
                                return resp;
                        }

                        // Set the retrieved subtask in the response
                        List<SubTaskDTO> subTasks = new ArrayList<>();
                        subTasks.add(subTask);
                        resp.setSubTaskList(subTasks);
                        resp.setCurrentResult(Result.OK);
                        resp.setMessage("SubTask with ID " + subTask.getId() + " retrieved successfully from Task ID "
                                        + currentTask.getId() + " in Todo List ID " + idsList[0]);
                } catch (Exception e) {
                        String message = "Error while retrieving SubTask with ID " + idsSubTask[0]
                                        + " from Task ID " + idsTask[0] + " in Todo List ID "
                                        + idsList[0]
                                        + " - message: " + e.getMessage();
                        System.err.println(message);
                        resp.setMessage(message);
                        resp.setCurrentResult(Result.ERROR);
                        return resp;
                }

                return resp;
        }

        // Add a subTask
        public SubTaskServicesResponse addSubTaskFromJSON(SubTaskServicesRequest req) {
                SubTaskServicesResponse resp = new SubTaskServicesResponse();
                TaskDTO currentTask = new TaskDTO();
                // 1 if the added task id the first one of the todo list
                int lastId = 1;

                try {
                        // Getting the current task
                        currentTask = ts
                                        .getTaskByIdFromJSON(req.getIdsList(), req.getIdsTask(),
                                                        req.getIsTest())
                                        .getTasks().get(0);
                        boolean isEmptySubTasks = currentTask.getSubTasks().isEmpty();
                        // Get the greatest id if the todo list has task(s)
                        if (!isEmptySubTasks) {
                                // Sort the subTasks in descending order based on their id
                                Collections.sort(currentTask.getSubTasks(),
                                                Comparator.comparingInt(SubTaskDTO::getId).reversed());
                                lastId = currentTask.getSubTasks().get(0).getId();
                        }

                        // Setting the id to the last id + 1 or if it's the first tasks to 1
                        req.getSubTasks().get(0).setId(isEmptySubTasks ? lastId : lastId + 1);
                        // The task is not yet completed
                        req.getSubTasks().get(0).setIsCompleted(false);
                        // Adding the subTask to the current task
                        currentTask.getSubTasks().add(req.getSubTasks().get(0));
                        // Sorting the tasks in ascending order
                        Collections.sort(currentTask.getSubTasks(), Comparator.comparingInt(SubTaskDTO::getId));
                        // Creating a List<Task> for request creation
                        List<TaskDTO> taskToUpdate = new ArrayList<TaskDTO>();
                        taskToUpdate.add(currentTask);
                        resp.setSubTaskList(req.getSubTasks());
                        // update the todo list
                        ts.updateTaskByIdFromJSON(
                                        new TaskServicesRequest(req.getIdsList(), req.getIdsTask(), taskToUpdate,
                                                        req.getIsTest()));
                } catch (Exception e) {
                        String message = "error while adding the subTask from task id : " + req.getIdsTask()[0]
                                        + " and from the todo list with the id : "
                                        + req.getIdsList()[0] + " - message : "
                                        + e.getMessage();
                        System.err.println(message);
                        resp.setMessage(message);
                        resp.setCurrentResult(Result.ERROR);

                        return resp;
                }

                resp.setCurrentResult(Result.OK);
                resp.setMessage("The subTask id : " + req.getSubTasks().get(0).getId() + " from the task id : "
                                + currentTask.getId() + " from the todo list with the id : "
                                + req.getIdsList() + " is added");
                return resp;
        }

        // Delete a SubTask
        public SubTaskServicesResponse deleteSubTaskByIdFromJSON(int[] idsList, int[] idsTask, int[] idsSubTask,
                        boolean isTest) {
                SubTaskServicesResponse resp = new SubTaskServicesResponse();
                SubTaskDTO subTaskToDelete = new SubTaskDTO();

                try {
                        // Getting the current task
                        TaskDTO currentTask = ts
                                        .getTaskByIdFromJSON(idsList, idsTask,
                                                        isTest)
                                        .getTasks().get(0);

                        // Getting the subTask to delete by its ID
                        subTaskToDelete = currentTask.getSubTasks().stream()
                                        .filter(subTask -> subTask.getId() == idsSubTask[0])
                                        .findFirst()
                                        .orElse(null);

                        if (subTaskToDelete == null) {
                                String message = "SUBTASK DELETE : SubTask with ID " + idsSubTask[0]
                                                + " not found or not exist in Task ID "
                                                + idsTask[0] + " from Todo List ID " + idsList[0];
                                System.err.println(message);
                                resp.setMessage(message);
                                resp.setCurrentResult(Result.NOT_EXISTING);
                                return resp;
                        }

                        // Supprimer la sous-tâche
                        currentTask.getSubTasks().remove(subTaskToDelete);

                        // Rearanger les IDs des sous-tâches et trier la liste par ID
                        currentTask.setSubTasks(rearangeSubTasksIds(currentTask.getSubTasks()));

                        // Mettre à jour la liste des tâches
                        List<TaskDTO> taskToUpdate = new ArrayList<>();
                        taskToUpdate.add(currentTask);
                        ts.updateTaskByIdFromJSON(
                                        new TaskServicesRequest(idsList, idsTask, taskToUpdate,
                                                        isTest));

                        resp.setSubTaskList(List.of(subTaskToDelete));
                        resp.setCurrentResult(Result.OK);
                        resp.setMessage("SubTask with ID " + subTaskToDelete.getId() + " from Task ID "
                                        + currentTask.getId()
                                        + " in Todo List ID " + idsList[0] + " has been deleted.");
                } catch (Exception e) {
                        String message = "Error while deleting SubTask with ID " + idsSubTask[0]
                                        + " from Task ID " + idsTask[0] + " in Todo List ID "
                                        + idsList[0]
                                        + " - message: " + e.getMessage();
                        System.err.println(message);
                        resp.setMessage(message);
                        resp.setCurrentResult(Result.ERROR);
                        resp.setSubTaskList(List.of(subTaskToDelete));

                        return resp;
                }

                return resp;
        }

        // Update a SubTask
        public SubTaskServicesResponse updateSubTaskByIdFromJSON(SubTaskServicesRequest req) {
                SubTaskServicesResponse resp = new SubTaskServicesResponse();

                try {
                        // Getting the current task
                        TaskDTO currentTask = ts
                                        .getTaskByIdFromJSON(req.getIdsList(), req.getIdsTask(),
                                                        req.getIsTest())
                                        .getTasks().get(0);

                        // Getting the subTask to update by its ID
                        SubTaskDTO subTaskToUpdate = currentTask.getSubTasks().stream()
                                        .filter(subTask -> subTask.getId() == req.getIdsSubTask()[0])
                                        .findFirst()
                                        .orElse(null);

                        if (subTaskToUpdate == null) {
                                String message = "SUBTASK UPDATE : SubTask with ID " + req.getIdsSubTask()[0]
                                                + " not found or not exist in Task ID "
                                                + req.getIdsTask()[0] + " from Todo List ID " + req.getIdsList()[0];
                                System.err.println(message);
                                resp.setMessage(message);
                                resp.setCurrentResult(Result.NOT_EXISTING);
                                return resp;
                        }

                        // Update the subTask informations
                        SubTaskDTO updatedSubTask = req.getSubTasks().get(0);
                        subTaskToUpdate.setSubTaskDTO(updatedSubTask);

                        // Mettre à jour la liste des tâches
                        List<TaskDTO> taskToUpdate = new ArrayList<>();
                        taskToUpdate.add(currentTask);
                        ts.updateTaskByIdFromJSON(
                                        new TaskServicesRequest(req.getIdsList(), req.getIdsTask(), taskToUpdate,
                                                        req.getIsTest()));

                        resp.setSubTaskList(List.of(subTaskToUpdate));
                        resp.setCurrentResult(Result.OK);
                        resp.setMessage("SubTask with ID " + subTaskToUpdate.getId() + " from Task ID "
                                        + currentTask.getId()
                                        + " in Todo List ID " + req.getIdsList()[0] + " has been updated.");
                } catch (Exception e) {
                        String message = "Error while updating SubTask with ID " + req.getSubTasks().get(0).getId()
                                        + " from Task ID " + req.getIdsTask()[0] + " in Todo List ID "
                                        + req.getIdsList()[0]
                                        + " - message: " + e.getMessage();
                        System.err.println(message);
                        resp.setMessage(message);
                        resp.setCurrentResult(Result.ERROR);
                        resp.setSubTaskList(List.of(req.getSubTasks().get(0)));

                        return resp;
                }

                return resp;
        }

        // Update a SubTask
        public SubTaskServicesResponse switchSubTaskPositionFromJSON(SubTaskServicesRequest req) {
                SubTaskServicesResponse resp = new SubTaskServicesResponse();

                try {
                        // Getting the current task
                        TaskDTO currentTask = ts
                                        .getTaskByIdFromJSON(req.getIdsList(), req.getIdsTask(),
                                                        req.getIsTest())
                                        .getTasks().get(0);

                        // Getting the subTask to switch 1
                        SubTaskDTO subTaskToSwitch1 = currentTask.getSubTasks().stream()
                                        .filter(subTask -> subTask.getId() == req.getIdsSubTask()[0])
                                        .findFirst()
                                        .orElse(null);

                        if (subTaskToSwitch1 == null) {
                                String message = "SUBTASK UPDATE : SubTask with ID " + req.getIdsSubTask()[0]
                                                + " not found or not exist in Task ID "
                                                + req.getIdsTask()[0] + " from Todo List ID " + req.getIdsList()[0];
                                System.err.println(message);
                                resp.setMessage(message);
                                resp.setCurrentResult(Result.NOT_EXISTING);
                                return resp;
                        }

                        // Getting the subTask to switch 1
                        SubTaskDTO subTaskToSwitch2 = currentTask.getSubTasks().stream()
                                        .filter(subTask -> subTask.getId() == req.getIdsSubTask()[1])
                                        .findFirst()
                                        .orElse(null);

                        if (subTaskToSwitch2 == null) {
                                String message = "SUBTASK UPDATE : SubTask with ID " + req.getIdsSubTask()[1]
                                                + " not found or not exist in Task ID "
                                                + req.getIdsTask()[0] + " from Todo List ID " + req.getIdsList()[0];
                                System.err.println(message);
                                resp.setMessage(message);
                                resp.setCurrentResult(Result.NOT_EXISTING);
                                return resp;
                        }

                        int tempId = subTaskToSwitch1.getId();
                        subTaskToSwitch1.setId(subTaskToSwitch2.getId());
                        subTaskToSwitch2.setId(tempId);

                        // Immediately update the subTasks list in currentToDoList
                        currentTask.setSubTasks(currentTask.getSubTasks().stream()
                                        .sorted(Comparator.comparingInt(SubTaskDTO::getId))
                                        .toList());

                        // Update the current tasks
                        ts.updateTaskByIdFromJSON(
                                        new TaskServicesRequest(req.getIdsList(), req.getIdsTask(),
                                                        List.of(currentTask),
                                                        req.getIsTest()));

                        resp.setSubTaskList(List.of(subTaskToSwitch2, subTaskToSwitch1));
                        resp.setCurrentResult(Result.OK);
                        resp.setMessage("SubTask1 and 2 had their ids switched from Task ID "
                                        + currentTask.getId()
                                        + " in Todo List ID " + req.getIdsList()[0]);
                } catch (Exception e) {
                        String message = "Error while switching SubTask 1 with ID " + req.getIdsSubTask()[0]
                                        + " and SubTask 2 with ID " + req.getIdsSubTask()[1]
                                        + " from Task ID " + req.getIdsTask()[0] + " in Todo List ID "
                                        + req.getIdsList()[0]
                                        + " - message: " + e.getMessage();
                        System.err.println(message);
                        resp.setMessage(message);
                        resp.setCurrentResult(Result.ERROR);
                        resp.setSubTaskList(List.of(req.getSubTasks().get(0)));

                        return resp;
                }

                return resp;
        }

        // Adding ids to 1 to N and sorting the list by id
        private List<SubTaskDTO> rearangeSubTasksIds(List<SubTaskDTO> subTasks) {
                if (!subTasks.isEmpty()) {
                        for (int i = 0; i <= subTasks.size() - 1; i++) {
                                subTasks.get(i).setId(i + 1);
                        }

                        Collections.sort(subTasks, Comparator.comparingInt(SubTaskDTO::getId));
                }

                return subTasks;
        }
}
