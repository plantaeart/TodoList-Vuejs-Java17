package com.tododo.tododo.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.tododo.tododo.models.ToDoList;
import com.tododo.tododo.models.Task;
import com.tododo.tododo.models.SubTask;
import com.tododo.tododo.models.dto.ToDoListDTO;
import com.tododo.tododo.models.dto.TaskDTO;
import com.tododo.tododo.models.dto.SubTaskDTO;

public class ToDoListMapper {

    // Map ToDoList to ToDoListDTO
    public static ToDoListDTO toDTO(ToDoList toDoList) {
        ToDoListDTO dto = new ToDoListDTO();
        dto.setId(toDoList.getId());
        dto.setType(toDoList.getType());
        dto.setName(toDoList.getName());
        dto.setDescription(toDoList.getDescription());
        dto.setCompletionPercentage(toDoList.getCompletionPercentage());
        dto.setColor(toDoList.getColor());
        dto.getIcon().toIconDTO(toDoList.getIcon());
        dto.setIsCompleted(toDoList.getIsCompleted());
        dto.setUpdateDate(toDoList.getUpdateDate());

        // Map tasks and their subtasks
        List<TaskDTO> taskDTOs = toDoList.getTasks().stream()
                .map(task -> {
                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setId(task.getId());
                    taskDTO.setType(task.getType());
                    taskDTO.setTaskContent(task.getTaskContent());
                    taskDTO.setDescription(task.getDescription());
                    taskDTO.setCompletionPercentage(task.getCompletionPercentage());
                    taskDTO.getIcon().toIconDTO(task.getIcon());
                    taskDTO.setIsCompleted(task.getIsCompleted());

                    // Map subtasks
                    List<SubTaskDTO> subTaskDTOs = task.getSubTasks().stream()
                            .map(subTask -> {
                                SubTaskDTO subTaskDTO = new SubTaskDTO();
                                subTaskDTO.setId(subTask.getId());
                                subTaskDTO.setType(subTask.getType());
                                subTaskDTO.setTaskContent(subTask.getTaskContent());
                                subTaskDTO.setDescription(subTask.getDescription());
                                subTaskDTO.getIcon().toIconDTO(subTask.getIcon());
                                subTaskDTO.setIsCompleted(subTask.getIsCompleted());
                                return subTaskDTO;
                            })
                            .collect(Collectors.toList());

                    taskDTO.setSubTasks(subTaskDTOs);
                    return taskDTO;
                })
                .collect(Collectors.toList());

        dto.setTasks(taskDTOs);
        return dto;
    }

    // Map ToDoListDTO back to ToDoList
    public static ToDoList fromDTO(ToDoListDTO dto) {
        ToDoList toDoList = new ToDoList();
        toDoList.setId(dto.getId());
        toDoList.setType(dto.getType());
        toDoList.setName(dto.getName());
        toDoList.setDescription(dto.getDescription());
        toDoList.setCompletionPercentage(dto.getCompletionPercentage());
        toDoList.setColor(dto.getColor());
        toDoList.getIcon().fromIconDTO(dto.getIcon());
        toDoList.setIsCompleted(dto.getIsCompleted());
        toDoList.setUpdateDate(dto.getUpdateDate());

        // Map tasks and their subtasks
        List<Task> tasks = dto.getTasks().stream()
                .map(taskDTO -> {
                    Task task = new Task();
                    task.setId(taskDTO.getId());
                    task.setType(taskDTO.getType());
                    task.setTaskContent(taskDTO.getTaskContent());
                    task.setDescription(taskDTO.getDescription());
                    task.setCompletionPercentage(taskDTO.getCompletionPercentage());
                    task.getIcon().fromIconDTO(taskDTO.getIcon());
                    task.setDescription(taskDTO.getDescription());
                    task.setIsCompleted(taskDTO.getIsCompleted());

                    // Map subtasks
                    List<SubTask> subTasks = taskDTO.getSubTasks().stream()
                            .map(subTaskDTO -> {
                                SubTask subTask = new SubTask();
                                subTask.setId(subTaskDTO.getId());
                                subTask.setType(subTaskDTO.getType());
                                subTask.setTaskContent(subTaskDTO.getTaskContent());
                                subTask.setDescription(subTaskDTO.getDescription());
                                subTask.getIcon().fromIconDTO(subTaskDTO.getIcon());
                                subTask.setIsCompleted(subTaskDTO.getIsCompleted());
                                return subTask;
                            })
                            .collect(Collectors.toList());

                    task.setSubTasks(subTasks);
                    return task;
                })
                .collect(Collectors.toList());

        toDoList.setTasks(tasks);
        return toDoList;
    }

}