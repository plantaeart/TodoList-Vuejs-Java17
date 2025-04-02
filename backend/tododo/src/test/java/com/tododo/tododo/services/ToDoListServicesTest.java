package com.tododo.tododo.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import java.util.List;
import org.junit.jupiter.api.Test;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.dto.ToDoListDTO;
import com.tododo.tododo.models.servicesResponse.ToDoListServicesResponse;
import com.tododo.tododo.models.servivesRequest.ToDoListServicesRequest;

public class ToDoListServicesTest {
    ToDoListServices service = new ToDoListServices();

    // Adding a ToDoList and testing if it was added successfully
    void testAddToDoListFromJSON(String toDoListName) {
        System.err.println("==> TODOLIST STARTING ADD TEST");

        ToDoListDTO data = new ToDoListDTO(toDoListName);
        ToDoListServicesRequest reqAdd = new ToDoListServicesRequest(List.of(data), true);

        ToDoListServicesResponse respAdd = service.addToDoListFromJSON(reqAdd);
        assert respAdd.getCurrentResult().equals(Result.OK);

        System.err.println("==> TODOLIST FINISHING ADD TEST");
    }

    // Adding a second ToDoList, getting the second one by id and testing if well
    // got and
    // if the id is correct
    void testGetListByIdFromJSON(int idList) {
        System.err.println("==> TODOLIST STARTING GET BY ID TEST");

        ToDoListServicesResponse respGetById = service.getToDoListByIdFromJSON(new int[] { idList }, true);
        assertAll(() -> {
            assert respGetById.getCurrentResult().equals(Result.OK);
            assert respGetById.gettoDoLists().get(0).getId() == idList;
        });

        System.err.println("==> TODOLIST FINISHING GET BY ID TEST");
    }

    // Get all the toDoLists and test if the result is OK and the size correct
    void testGetAllToDoListsFromJSON(int sizeToTest) {
        System.err.println("==> TODOLIST STARTING GET ALL TEST");

        ToDoListServicesResponse respGetAll = service.getAllToDoListsFromJSON(true);
        assertAll(() -> {
            assert respGetAll.getCurrentResult().equals(Result.OK);
            assert respGetAll.gettoDoLists().size() == sizeToTest;
        });

        System.err.println("==> TODOLIST FINISHING GET ALL TEST");
    }

    void testUpdateToDoListFromJSON(int idList, ToDoListDTO updatedData) {
        System.err.println("==> TODOLIST STARTING UPDATE TEST");

        ToDoListServicesRequest reqUpdate = new ToDoListServicesRequest(new int[] { idList }, List.of(updatedData),
                true);

        ToDoListServicesResponse respUpdate = service.updateToDoListFromJSON(reqUpdate);
        assertAll(() -> {
            assert respUpdate.getCurrentResult().equals(Result.OK);
            assert respUpdate.gettoDoLists().get(0).getName() == updatedData.getName();
        });

        System.err.println("==> TODOLIST FINISHING UPDATE TEST");
    }

    // Deleting both ToDoLists and testing if they were deleted successfully
    void testDeleteToDoListByIdFromJSON(int idList) {
        System.err.println("==> TODOLIST STARTING DELETE TEST");

        ToDoListServicesResponse resp = service.deleteToDoListByIdFromJSON(new int[] { idList }, true);
        assert resp.getCurrentResult().equals(Result.OK);

        System.err.println("==> TODOLIST FINISHING DELETE TEST");
    }

    @Test
    void workflowTestToDoListAll() {
        System.err.println("==> STARTING TODOLIST TESTS <==");

        testAddToDoListFromJSON("TestToDoList1");
        testAddToDoListFromJSON("TestToDoList2");
        testGetListByIdFromJSON(2);
        testGetAllToDoListsFromJSON(2);
        testUpdateToDoListFromJSON(2, new ToDoListDTO("TestToDoList2Updated"));
        testDeleteToDoListByIdFromJSON(2);
        testDeleteToDoListByIdFromJSON(1);

        System.err.println("==> FINISHING TODOLIST TESTS <==");
    }
}
