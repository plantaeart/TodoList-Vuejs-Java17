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

        TaskServicesRequest reqGetAll = new TaskServicesRequest(new int[] { idList }, true);
        TaskServicesResponse respGetAll = service.getAllTasksFromJSON(reqGetAll);

        assertAll(() -> {
            assert respGetAll.getCurrentResult().equals(Result.OK);
            assert respGetAll.getTaskList().size() == sizeToTest;
        });

        System.err.println("==> TASK FINISHING GET ALL TEST");
    }

    void testGetTaskByIdFromJSON(int idList, int idTask) {
        System.err.println("==> TASK STARTING GET BY ID TEST");

        TaskServicesRequest reqGetById = new TaskServicesRequest(new int[] { idList }, new int[] { idTask }, true);
        TaskServicesResponse respGetById = service.getTaskByIdFromJSON(reqGetById);

        assertAll(() -> {
            assert respGetById.getCurrentResult().equals(Result.OK);
            assert respGetById.getTaskList().get(0).getId() == idTask;
        });

        System.err.println("==> TASK FINISHING GET BY ID TEST");
    }

    void testUpdateTaskFromJSON(int idList, int idTask, TaskDTO updatedData) {
        System.err.println("==> TASK STARTING UPDATE TEST");

        TaskServicesRequest reqUpdate = new TaskServicesRequest(new int[] { idList }, new int[] { idTask },
                List.of(updatedData), true);
        TaskServicesResponse respUpdate = service.updateTaskFromJSON(reqUpdate);

        assertAll(() -> {
            assert respUpdate.getCurrentResult().equals(Result.OK);
            assert respUpdate.getTaskList().get(0).getTaskContent() == updatedData.getTaskContent();
        });

        System.err.println("==> TASK FINISHING UPDATE TEST");
    }

    void testDeleteTaskFromJSON(int idList, int idTask) {
        System.err.println("==> TASK STARTING DELETE TEST");

        TaskServicesRequest reqDelete = new TaskServicesRequest(new int[] { idList }, new int[] { idTask }, true);
        TaskServicesResponse respDelete = service.deleteTaskFromJSON(reqDelete);

        assert respDelete.getCurrentResult().equals(Result.OK);

        System.err.println("==> TASK FINISHING DELETE TEST");
    }

    void testSwitchTaskPositionFromJSON(int idList, int idTask1, int idTask2, String taskContentTest) {
        System.err.println("==> TASK STARTING SWITCH TASK POSITION TESTS");

        TaskServicesRequest reqSwitch = new TaskServicesRequest(new int[] { idList }, new int[] { idTask1, idTask2 },
                true);
        TaskServicesResponse respSwitch = service.switchTaskPositionFromJSON(reqSwitch);

        respSwitch.getTaskList().forEach(x -> System.err.println(x.toString()));

        assertAll(() -> {
            assert respSwitch.getCurrentResult().equals(Result.OK);
            assert respSwitch.getTaskList().get(0).getTaskContent().equals(taskContentTest);
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
        testUpdateTaskFromJSON(1, 2, new TaskDTO("TestTask2Updated"));
        testSwitchTaskPositionFromJSON(1, 1, 2, "TestTask2Updated");
        testDeleteTaskFromJSON(1, 2);
        testDeleteTaskFromJSON(1, 1);
        toDoListServiceTest.testDeleteToDoListByIdFromJSON(1);

        System.err.println("==> FINISHING TASKS TESTS <==");
    }
}
