package com.tododo.tododo.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import java.util.List;
import org.junit.jupiter.api.Test;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.ToDoList;
import com.tododo.tododo.models.servicesResponse.ToDoListServicesResponse;
import com.tododo.tododo.models.servivesRequest.ToDoListServicesRequest;

public class ToDoListServicesTest {
    ToDoListServices service = new ToDoListServices();

    // Adding a ToDoList and testing if it was added successfully
    void testAddToDoListFromJSON(String toDoListName) {
        System.err.println("==> TODOLIST STARTING ADD TEST");

        ToDoList data = new ToDoList(toDoListName);
        ToDoListServicesRequest reqAdd = new ToDoListServicesRequest(List.of(data), true);

        ToDoListServicesResponse respAdd = service.addToDoListFromJSON(reqAdd);
        assert respAdd.get_currentResult().equals(Result.OK);

        System.err.println("==> TODOLIST FINISHING ADD TEST");
    }

    // Adding a second ToDoList, getting the second one by id and testing if well
    // got and
    // if the id is correct
    void testGetListByIdFromJSON(int idList) {
        System.err.println("==> TODOLIST STARTING GET BY ID TEST");

        ToDoListServicesRequest reqGetById = new ToDoListServicesRequest(idList, true);

        ToDoListServicesResponse respGetById = service.getListByIdFromJSON(reqGetById);
        assertAll(() -> {
            assert respGetById.get_currentResult().equals(Result.OK);
            assert respGetById.get_toDoListList().get(0).getId() == idList;
        });

        System.err.println("==> TODOLIST FINISHING GET BY ID TEST");
    }

    // Get all the toDoLists and test if the result is OK and the size correct
    void testGetAllToDoListsFromJSON(int sizeToTest) {
        System.err.println("==> TODOLIST STARTING GET ALL TEST");

        ToDoListServicesRequest reqGetAll = new ToDoListServicesRequest(true);

        ToDoListServicesResponse respGetAll = service.getAllToDoListsFromJSON(reqGetAll);
        assertAll(() -> {
            assert respGetAll.get_currentResult().equals(Result.OK);
            assert respGetAll.get_toDoListList().size() == sizeToTest;
        });

        System.err.println("==> TODOLIST FINISHING GET ALL TEST");
    }

    void testUpdateToDoListFromJSON(int idList, ToDoList updatedData) {
        System.err.println("==> TODOLIST STARTING UPDATE TEST");

        ToDoListServicesRequest reqUpdate = new ToDoListServicesRequest(idList, List.of(updatedData), true);

        ToDoListServicesResponse respUpdate = service.updateToDoListFromJSON(reqUpdate);
        assertAll(() -> {
            assert respUpdate.get_currentResult().equals(Result.OK);
            assert respUpdate.get_toDoListList().get(0).get_name() == updatedData.get_name();
        });

        System.err.println("==> TODOLIST FINISHING UPDATE TEST");
    }

    // Deleting both ToDoLists and testing if they were deleted successfully
    void testDeleteToDoListByIdFromJSON(int idList) {
        System.err.println("==> TODOLIST STARTING DELETE TEST");

        ToDoListServicesRequest reqDel = new ToDoListServicesRequest(idList, true);

        ToDoListServicesResponse resp = service.deleteToDoListByIdFromJSON(reqDel);
        assert resp.get_currentResult().equals(Result.OK);

        System.err.println("==> TODOLIST FINISHING DELETE TEST");
    }

    @Test
    void workflowTestToDoListAll() {
        testAddToDoListFromJSON("TestToDoList1");
        testAddToDoListFromJSON("TestToDoList2");
        testGetListByIdFromJSON(2);
        testGetAllToDoListsFromJSON(2);
        testUpdateToDoListFromJSON(2, new ToDoList("TestToDoList2Updated"));
        testDeleteToDoListByIdFromJSON(2);
        testDeleteToDoListByIdFromJSON(1);
    }
}
