package com.tododo.tododo.services;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.Task;
import com.tododo.tododo.models.servicesResponse.TaskServicesResponse;
import com.tododo.tododo.models.servivesRequest.TaskServicesRequest;

public class TaskServicesTest {

    TaskServices service = new TaskServices();
    ToDoListServicesTest toDoListServiceTest = new ToDoListServicesTest();

    void testAddTaskFromJSON(int idList, String taskContent) {
        System.err.println("==> TASK STARTING ADD TEST");

        Task data = new Task(taskContent);

        TaskServicesRequest reqAdd = new TaskServicesRequest(idList, List.of(data), true);
        TaskServicesResponse respAdd = service.addTaskFromJSON(reqAdd);

        assert respAdd.get_currentResult().equals(Result.OK);
        System.err.println("==> TASK FINISHING ADD TEST");
    }

    void testGetAllTasksFromJSON(int sizeToTest) {
        System.err.println("==> TASK STARTING GET ALL TEST");

        TaskServicesRequest reqGetAll = new TaskServicesRequest(1, true);
        TaskServicesResponse respGetAll = service.getAllTasksFromJSON(reqGetAll);

        assertAll(() -> {
            assert respGetAll.get_currentResult().equals(Result.OK);
            assert respGetAll.get_taskList().size() == sizeToTest;
        });

        System.err.println("==> TASK FINISHING GET ALL TEST");
    }

    void testGetTaskByIdFromJSON(int idList, int idTask) {
        System.err.println("==> TASK STARTING GET BY ID TEST");

        TaskServicesRequest reqGetById = new TaskServicesRequest(idList, idTask, true);
        TaskServicesResponse respGetById = service.getTaskByIdFromJSON(reqGetById);

        assertAll(() -> {
            assert respGetById.get_currentResult().equals(Result.OK);
            assert respGetById.get_taskList().get(0).getId() == idTask;
        });

        System.err.println("==> TASK FINISHING GET BY ID TEST");
    }

    void testUpdateTaskFromJSON(int idList, int idTask, Task updatedData) {
        System.err.println("==> TASK STARTING UPDATE TEST");

        TaskServicesRequest reqUpdate = new TaskServicesRequest(idList, idTask, List.of(updatedData), true);
        TaskServicesResponse respUpdate = service.updateTaskFromJSON(reqUpdate);

        assertAll(() -> {
            assert respUpdate.get_currentResult().equals(Result.OK);
            assert respUpdate.get_taskList().get(0).getTaskContent() == updatedData.getTaskContent();
        });

        System.err.println("==> TASK FINISHING UPDATE TEST");
    }

    void testDeleteTaskFromJSON(int idList, int idTask) {
        System.err.println("==> TASK STARTING DELETE TEST");

        TaskServicesRequest reqDelete = new TaskServicesRequest(idList, idTask, true);
        TaskServicesResponse respDelete = service.deleteTaskFromJSON(reqDelete);

        assert respDelete.get_currentResult().equals(Result.OK);

        System.err.println("==> TASK FINISHING DELETE TEST");
    }

    @Test
    void workflowTestTaskAll() {
        toDoListServiceTest.testAddToDoListFromJSON("TestToDoList1");
        testAddTaskFromJSON(1, "TestTask1");
        testAddTaskFromJSON(1, "TestTask2");
        testGetAllTasksFromJSON(2);
        testGetTaskByIdFromJSON(1, 2);
        testUpdateTaskFromJSON(1, 2, new Task("TestTask2Updated"));
        testDeleteTaskFromJSON(1, 2);
        testDeleteTaskFromJSON(1, 1);
        toDoListServiceTest.testDeleteToDoListByIdFromJSON(1);
    }
}
