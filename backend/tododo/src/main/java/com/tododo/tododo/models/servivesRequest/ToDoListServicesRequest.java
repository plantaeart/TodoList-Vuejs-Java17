package com.tododo.tododo.models.servivesRequest;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tododo.tododo.models.dto.ToDoListDTO;

public class ToDoListServicesRequest {
    @JsonProperty(value = "idsList", required = false)
    protected int[] idsList;
    @JsonProperty(value = "toDoLists", required = false)
    private List<ToDoListDTO> toDoLists;
    @JsonProperty(value = "isTest", required = false)
    protected boolean isTest = false;

    public ToDoListServicesRequest() {
    }

    public ToDoListServicesRequest(int[] idsList, List<ToDoListDTO> _toDoLists, boolean isTest) {
        this.idsList = idsList;
        this.toDoLists = _toDoLists;
        this.isTest = isTest;
    }

    public ToDoListServicesRequest(int[] idsList, List<ToDoListDTO> _toDoLists) {
        this.idsList = idsList;
        this.toDoLists = _toDoLists;
    }

    public ToDoListServicesRequest(int[] idsList, boolean isTest) {
        this.idsList = idsList;
        this.isTest = isTest;
    }

    public ToDoListServicesRequest(List<ToDoListDTO> toDoLists, boolean isTest) {
        this.toDoLists = toDoLists;
        this.isTest = isTest;
    }

    public ToDoListServicesRequest(List<ToDoListDTO> toDoLists) {
        this.toDoLists = toDoLists;
    }

    public ToDoListServicesRequest(int[] idsList) {
        this.idsList = idsList;
    }

    public ToDoListServicesRequest(boolean isTest) {
        this.isTest = isTest;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(idsList);
        result = prime * result + ((toDoLists == null) ? 0 : toDoLists.hashCode());
        result = prime * result + (isTest ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ToDoListServicesRequest other = (ToDoListServicesRequest) obj;
        if (!Arrays.equals(idsList, other.idsList))
            return false;
        if (toDoLists == null) {
            if (other.toDoLists != null)
                return false;
        } else if (!toDoLists.equals(other.toDoLists))
            return false;
        if (isTest != other.isTest)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ToDoListServicesRequest [idsList=" + Arrays.toString(idsList) + ", toDoLists=" + toDoLists + ", isTest="
                + isTest + "]";
    }

    public int[] getIdsList() {
        return idsList;
    }

    public void setIdsList(int[] idsList) {
        this.idsList = idsList;
    }

    public List<ToDoListDTO> getToDoLists() {
        return toDoLists;
    }

    public void setToDoLists(List<ToDoListDTO> toDoLists) {
        this.toDoLists = toDoLists;
    }

    public boolean getIsTest() {
        return isTest;
    }

    public void setIsTest(boolean isTest) {
        this.isTest = isTest;
    }

}
