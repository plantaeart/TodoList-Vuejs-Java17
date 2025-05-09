package com.tododo.tododo.services;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.dto.SubTaskDTO;
import com.tododo.tododo.models.servicesResponse.SubTaskServicesResponse;
import com.tododo.tododo.models.servivesRequest.SubTaskServicesRequest;

public class SubTaskServicesTest {

    SubTaskServices service = new SubTaskServices();
    ToDoListServicesTest toDoListServiceTest = new ToDoListServicesTest();
    TaskServicesTest taskServiceTest = new TaskServicesTest();

    void testAddSubTaskFromJSON(int idList, int idTask, String taskContent) {
        System.err.println("==> SUBTASK STARTING ADD TEST");

        // Create a request object to add the SubTask
        SubTaskServicesRequest reqAdd = new SubTaskServicesRequest(new int[] { idList }, new int[] { idTask },
                List.of(new SubTaskDTO(taskContent)),
                true);
        // Call the service method to add the SubTask and get the response
        SubTaskServicesResponse respAdd = service.addSubTaskFromJSON(reqAdd);
        // Assert that the result is OK
        assert respAdd.getCurrentResult().equals(Result.OK);

        System.err.println("==> SUBTASK FINISHING ADD TEST");
    }

    void testdeleteSubTaskByIdFromJSON(int idList, int idTask, int idSubTask) {
        System.err.println("==> SUBTASK STARTING DELETE TEST");

        // Call the service method to delete the SubTask and get the response
        SubTaskServicesResponse respDelete = service.deleteSubTaskByIdFromJSON(new int[] { idList },
                new int[] { idTask },
                new int[] { idSubTask }, true);
        // Assert that the result is OK
        assert respDelete.getCurrentResult().equals(Result.OK);

        System.err.println("==> SUBTASK FINISHING DELETE TEST");
    }

    void testGetSubTaskByIdFromJSON(int idList, int idTask, int idSubTask) {
        System.err.println("==> SUBTASK STARTING GET TEST");

        // Call the service method to get the SubTask and get the response
        SubTaskServicesResponse respGet = service.getSubTaskByIdFromJSON(new int[] { idList }, new int[] { idTask },
                new int[] { idSubTask }, true);
        // Assert that the result is OK
        assert respGet.getCurrentResult().equals(Result.OK);

        System.err.println("==> SUBTASK FINISHING GET TEST");
    }

    void testupdateSubTaskByIdFromJSON(int idList, int idTask, int idSubTask, SubTaskDTO updatedData) {
        System.err.println("==> SUBTASK STARTING UPDATE TEST");

        // Create a request object to update the SubTask
        SubTaskServicesRequest reqUpdate = new SubTaskServicesRequest(new int[] { idList }, new int[] { idTask },
                new int[] { idSubTask }, List.of(updatedData),
                true);
        // Call the service method to update the SubTask and get the response
        SubTaskServicesResponse respUpdate = service.updateSubTaskByIdFromJSON(reqUpdate);
        // Assert that the result is OK
        assertAll(() -> {
            assert respUpdate.getCurrentResult().equals(Result.OK);
            assert respUpdate.getSubTasks().get(0).getTaskContent() == updatedData.getTaskContent();
        });

        System.err.println("==> SUBTASK FINISHING UPDATE TEST");
    }

    void testSwitchTaskPositionFromJSON(int idList, int idTask, int idSubTask1, int idSubTask2,
            String subTaskContentTest) {
        System.err.println("==> SUBTASK STARTING SWITCH POSITION TEST");

        // Create a request object to switch the position of the SubTasks
        SubTaskServicesRequest reqSwitch = new SubTaskServicesRequest(new int[] { idList }, new int[] { idTask },
                new int[] { idSubTask1, idSubTask2 }, true);
        // Call the service method to switch the position of the SubTasks and get the
        // response
        SubTaskServicesResponse respSwitch = service.switchSubTaskPositionFromJSON(reqSwitch);
        // Assert that the result is OK
        assertAll(() -> {
            assert respSwitch.getCurrentResult().equals(Result.OK);
            assert respSwitch.getSubTasks().get(0).getTaskContent().equals(subTaskContentTest);
        });

        System.err.println("==> SUBTASK FINISHING SWITCH POSITION TEST");
    }

    void testSubTaskEmpty(int idList, int idTask) {
        System.err.println("==> SUBTASK STARTING EMPTY TEST");

        // Call the service method to check if the SubTask is empty and get the response
        SubTaskServicesResponse respEmpty = service.getAllSubTasksFromJSON(new int[] { idList }, new int[] { idTask },
                true);
        // Assert that the result is OK
        assertAll(() -> {
            assert respEmpty.getCurrentResult().equals(Result.NOT_EXISTING);
            assert respEmpty.getSubTasks().isEmpty();
        });

        System.err.println("==> SUBTASK FINISHING EMPTY TEST");
    }

    @Test
    void workflowTestSubTasksAll() {
        System.err.println("==> STARTING SUBTASKS TESTS <==");

        toDoListServiceTest.testAddToDoListFromJSON("TestList1");
        taskServiceTest.testAddTaskFromJSON(1, "TestTask1");
        testAddSubTaskFromJSON(1, 1, "TestSubTask1");
        testAddSubTaskFromJSON(1, 1, "TestSubTask2");
        testupdateSubTaskByIdFromJSON(1, 1, 2, new SubTaskDTO("TestSubTask2Updated"));
        testGetSubTaskByIdFromJSON(1, 1, 2);
        testSwitchTaskPositionFromJSON(1, 1, 1, 2, "TestSubTask2Updated");
        testdeleteSubTaskByIdFromJSON(1, 1, 2);
        testdeleteSubTaskByIdFromJSON(1, 1, 1);
        testSubTaskEmpty(1, 1);
        taskServiceTest.testDeleteTaskFromJSON(1, 1);
        toDoListServiceTest.testDeleteToDoListByIdFromJSON(1);
        toDoListServiceTest.testToDoListEmpty();

        System.err.println("==> FINISHING SUBTASKS TESTS <==");
    }
}
