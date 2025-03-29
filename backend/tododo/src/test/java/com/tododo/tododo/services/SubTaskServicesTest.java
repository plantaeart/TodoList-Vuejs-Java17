package com.tododo.tododo.services;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.SubTask;
import com.tododo.tododo.models.servicesResponse.SubTaskServicesResponse;
import com.tododo.tododo.models.servivesRequest.SubTaskServicesRequest;

public class SubTaskServicesTest {

    SubTaskServices service = new SubTaskServices();
    ToDoListServicesTest toDoListServiceTest = new ToDoListServicesTest();
    TaskServicesTest taskServiceTest = new TaskServicesTest();

    void testAddSubTaskFromJSON(int idList, int idTask, String taskContent) {
        System.err.println("==> SUBTASK STARTING ADD TEST");
        ;
        // Create a request object to add the SubTask
        SubTaskServicesRequest reqAdd = new SubTaskServicesRequest(idList, idTask, List.of(new SubTask(taskContent)),
                true);
        // Call the service method to add the SubTask and get the response
        SubTaskServicesResponse respAdd = service.addSubTaskFromJSON(reqAdd);
        // Assert that the result is OK
        assert respAdd.get_currentResult().equals(Result.OK);

        System.err.println("==> SUBTASK FINISHING ADD TEST");
    }

    void testDeleteSubTaskFromJSON(int idList, int idTask, int idSubTask) {
        System.err.println("==> SUBTASK STARTING DELETE TEST");

        // Create a request object to delete the SubTask
        SubTaskServicesRequest reqDelete = new SubTaskServicesRequest(idList, idTask, idSubTask, true);
        // Call the service method to delete the SubTask and get the response
        SubTaskServicesResponse respDelete = service.deleteSubTaskFromJSON(reqDelete);
        // Assert that the result is OK
        assert respDelete.get_currentResult().equals(Result.OK);

        System.err.println("==> SUBTASK FINISHING DELETE TEST");
    }

    void testGetSubTaskByIdFromJSON(int idList, int idTask, int idSubTask) {
        System.err.println("==> SUBTASK STARTING GET TEST");

        // Create a request object to get the SubTask by ID
        SubTaskServicesRequest reqGet = new SubTaskServicesRequest(idList, idTask, idSubTask, true);
        // Call the service method to get the SubTask and get the response
        SubTaskServicesResponse respGet = service.getSubTaskByIdFromJSON(reqGet);
        // Assert that the result is OK
        assert respGet.get_currentResult().equals(Result.OK);

        System.err.println("==> SUBTASK FINISHING GET TEST");
    }

    void testUpdateSubTaskFromJSON(int idList, int idTask, int idSubTask, SubTask updatedData) {
        System.err.println("==> SUBTASK STARTING UPDATE TEST");

        // Create a request object to update the SubTask
        SubTaskServicesRequest reqUpdate = new SubTaskServicesRequest(idList, idTask, idSubTask, List.of(updatedData),
                true);
        // Call the service method to update the SubTask and get the response
        SubTaskServicesResponse respUpdate = service.updateSubTaskFromJSON(reqUpdate);
        // Assert that the result is OK
        assertAll(() -> {
            assert respUpdate.get_currentResult().equals(Result.OK);
            assert respUpdate.get_subTaskList().get(0).getTaskContent() == updatedData.getTaskContent();
        });

        System.err.println("==> SUBTASK FINISHING UPDATE TEST");
    }

    @Test
    void workflowTestSubTasksAll() {
        toDoListServiceTest.testAddToDoListFromJSON("TestList1");
        taskServiceTest.testAddTaskFromJSON(1, "TestTask1");
        testAddSubTaskFromJSON(1, 1, "TestSubTask1");
        testAddSubTaskFromJSON(1, 1, "TestSubTask2");
        testUpdateSubTaskFromJSON(1, 1, 2, new SubTask("TestSubTask2Updated"));
        testGetSubTaskByIdFromJSON(1, 1, 2);
        testDeleteSubTaskFromJSON(1, 1, 2);
        testDeleteSubTaskFromJSON(1, 1, 1);
        taskServiceTest.testDeleteTaskFromJSON(1, 1);
        toDoListServiceTest.testDeleteToDoListByIdFromJSON(1);
    }
}
