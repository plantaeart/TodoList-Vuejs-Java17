package com.tododo.tododo.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import java.util.List;
import org.junit.jupiter.api.Test;

import com.tododo.tododo.enums.Result;
import com.tododo.tododo.models.ToDoList;
import com.tododo.tododo.models.servicesResponse.ToDoListServicesResponse;
import com.tododo.tododo.models.servivesRequest.ToDoListRequest;

public class ToDoListServicesTest {
    ToDoListServices service = new ToDoListServices();

    // Adding a ToDoList and testing if it was added successfully
    void testAddToDoListFromJSON(String toDoListName) {
        System.err.println("==> STARTING ADD TEST");
        ToDoList data = new ToDoList(toDoListName);
        ToDoListRequest reqAdd = new ToDoListRequest(List.of(data), true);

        ToDoListServicesResponse respAdd = service.addToDoListFromJSON(reqAdd);
        assert respAdd.get_currentResult().equals(Result.OK);
    }

    // Adding a second ToDoList, getting the second one by id and testing if well
    // got and
    // if the id is correct
    void testGetListByIdFromJSON(int idList) {
        System.err.println("==> STARTING GET BY ID TEST");
        ToDoListRequest reqGetById = new ToDoListRequest(2, true);

        ToDoListServicesResponse respGetById = service.getListByIdFromJSON(reqGetById);
        assertAll(() -> {
            assert respGetById.get_currentResult().equals(Result.OK);
            assert respGetById.get_toDoListList().get(0).getId() == 2;
        });

        System.err.println("==> FINISHING GET BY ID TEST");
    }

    // Get all the toDoLists and test if the result is OK and the size correct
    void testGetAllToDoListsFromJSON() {
        System.err.println("==> STARTING GET ALL TEST");
        ToDoListRequest reqGetAll = new ToDoListRequest(true);

        ToDoListServicesResponse respGetAll = service.getAllToDoListsFromJSON(reqGetAll);
        assertAll(() -> {
            assert respGetAll.get_currentResult().equals(Result.OK);
            assert respGetAll.get_toDoListList().size() == 2;
        });

        System.err.println("==> FINISHING GET ALL TEST");
    }

    void testUpdateToDoListFromJSON(int idList, ToDoList dataToUpdate) {
        System.err.println("==> STARTING UPDATE TEST");
        ToDoListRequest reqUpdate = new ToDoListRequest(idList, List.of(dataToUpdate), true);

        ToDoListServicesResponse respUpdate = service.updateToDoListFromJSON(reqUpdate);
        assertAll(() -> {
            assert respUpdate.get_currentResult().equals(Result.OK);
            assert respUpdate.get_toDoListList().get(0).get_name() == dataToUpdate.get_name();
        });

        System.err.println("==> FINISHING UPDATE TEST");
    }

    // Deleting both ToDoLists and testing if they were deleted successfully
    void testDeleteToDoListByIdFromJSON(int idList) {
        System.err.println("==> STARTING DELETE TEST");
        ToDoListRequest reqDel = new ToDoListRequest(idList, true);

        ToDoListServicesResponse resp = service.deleteToDoListByIdFromJSON(reqDel);
        assert resp.get_currentResult().equals(Result.OK);

        System.err.println("==> FINISHING DELETE TEST");
    }

    @Test
    void workflowTestToDoListAll() {
        testAddToDoListFromJSON("TestToDoList1");
        testAddToDoListFromJSON("TestToDoList2");
        testGetListByIdFromJSON(2);
        testGetAllToDoListsFromJSON();
        testUpdateToDoListFromJSON(2, new ToDoList("TestToDoList2Updated"));
        testDeleteToDoListByIdFromJSON(2);
        testDeleteToDoListByIdFromJSON(1);
    }
}
