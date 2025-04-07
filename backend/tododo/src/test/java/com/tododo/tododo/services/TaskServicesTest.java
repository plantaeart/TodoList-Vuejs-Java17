package com.tododo.tododo.services;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.dto.TaskDTO;
import com.tododo.tododo.models.servicesResponse.TaskServicesResponse;
import com.tododo.tododo.models.servivesRequest.TaskServicesRequest;

public class TaskServicesTest {

    TaskServices service = new TaskServices();
    ToDoListServicesTest toDoListServiceTest = new ToDoListServicesTest();

    void testAddTaskFromJSON(int idList, String taskContent) {
        System.err.println("==> TASK STARTING ADD TEST");

        TaskDTO data = new TaskDTO(taskContent);

        TaskServicesRequest reqAdd = new TaskServicesRequest(new int[] { idList }, List.of(data), true);
        TaskServicesResponse respAdd = service.addTaskFromJSON(reqAdd);

        assert respAdd.getCurrentResult().equals(Result.OK);
        System.err.println("==> TASK FINISHING ADD TEST");
    }

    void testGetAllTasksFromJSON(int idList, int sizeToTest) {
        System.err.println("==> TASK STARTING GET ALL TEST");

        TaskServicesResponse respGetAll = service.getAllTasksFromJSON(new int[] { idList }, true);

        assertAll(() -> {
            assert respGetAll.getCurrentResult().equals(Result.OK);
            assert respGetAll.getTasks().size() == sizeToTest;
        });

        System.err.println("==> TASK FINISHING GET ALL TEST");
    }

    void testGetTaskByIdFromJSON(int idList, int idTask) {
        System.err.println("==> TASK STARTING GET BY ID TEST");

        TaskServicesResponse respGetById = service.getTaskByIdFromJSON(new int[] { idList }, new int[] { idTask },
                true);

        assertAll(() -> {
            assert respGetById.getCurrentResult().equals(Result.OK);
            assert respGetById.getTasks().get(0).getId() == idTask;
        });

        System.err.println("==> TASK FINISHING GET BY ID TEST");
    }

    void testupdateTaskByIdFromJSON(int idList, int idTask, TaskDTO updatedData) {
        System.err.println("==> TASK STARTING UPDATE TEST");

        TaskServicesRequest reqUpdate = new TaskServicesRequest(new int[] { idList }, new int[] { idTask },
                List.of(updatedData), true);
        TaskServicesResponse respUpdate = service.updateTaskByIdFromJSON(reqUpdate);

        assertAll(() -> {
            assert respUpdate.getCurrentResult().equals(Result.OK);
            assert respUpdate.getTasks().get(0).getTaskContent() == updatedData.getTaskContent();
        });

        System.err.println("==> TASK FINISHING UPDATE TEST");
    }

    void testDeleteTaskFromJSON(int idList, int idTask) {
        System.err.println("==> TASK STARTING DELETE TEST");

        TaskServicesResponse respDelete = service.deleteTaskByIdFromJSON(new int[] { idList }, new int[] { idTask },
                true);

        assert respDelete.getCurrentResult().equals(Result.OK);

        System.err.println("==> TASK FINISHING DELETE TEST");
    }

    void testSwitchTaskPositionFromJSON(int idList, int idTask1, int idTask2, String taskContentTest) {
        System.err.println("==> TASK STARTING SWITCH TASK POSITION TESTS");

        TaskServicesRequest reqSwitch = new TaskServicesRequest(new int[] { idList }, new int[] { idTask1, idTask2 },
                true);
        TaskServicesResponse respSwitch = service.switchTaskPositionFromJSON(reqSwitch);

        assertAll(() -> {
            assert respSwitch.getCurrentResult().equals(Result.OK);
            assert respSwitch.getTasks().get(0).getTaskContent().equals(taskContentTest);
        });

        System.err.println("==> TASK FINISHING SWITCH TASK POSITION TESTS");
    }

    @Test
    void workflowTestTaskAll() {
        System.err.println("==> STARTING TASKS TESTS <==");

        toDoListServiceTest.testAddToDoListFromJSON("TestToDoList1");
        testAddTaskFromJSON(1, "TestTask1");
        testAddTaskFromJSON(1, "TestTask2");
        testGetAllTasksFromJSON(1, 2);
        testGetTaskByIdFromJSON(1, 2);
        testupdateTaskByIdFromJSON(1, 2, new TaskDTO("TestTask2Updated"));
        testSwitchTaskPositionFromJSON(1, 1, 2, "TestTask2Updated");
        testDeleteTaskFromJSON(1, 2);
        testDeleteTaskFromJSON(1, 1);
        toDoListServiceTest.testDeleteToDoListByIdFromJSON(1);
        toDoListServiceTest.testToDoListEmpty();

        System.err.println("==> FINISHING TASKS TESTS <==");
    }
}
