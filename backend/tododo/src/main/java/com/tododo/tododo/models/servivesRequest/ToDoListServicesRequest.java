package com.tododo.tododo.models.servivesRequest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tododo.tododo.models.ToDoList;

public class ToDoListServicesRequest {
    @JsonProperty(value = "idList", required = false)
    protected int _idList;
    @JsonProperty(value = "toDoLists", required = false)
    private List<ToDoList> _toDoLists;
    @JsonProperty(value = "toDoLists", required = false)
    protected boolean isTest = false;

    public ToDoListServicesRequest() {
    }

    public ToDoListServicesRequest(int _idList, List<ToDoList> _toDoLists, boolean isTest) {
        this._idList = _idList;
        this._toDoLists = _toDoLists;
        this.isTest = isTest;
    }

    public ToDoListServicesRequest(int _idList, List<ToDoList> _toDoLists) {
        this._idList = _idList;
        this._toDoLists = _toDoLists;
    }

    public ToDoListServicesRequest(int _idList, boolean isTest) {
        this._idList = _idList;
        this.isTest = isTest;
    }

    public ToDoListServicesRequest(List<ToDoList> _toDoLists, boolean isTest) {
        this._toDoLists = _toDoLists;
        this.isTest = isTest;
    }

    public ToDoListServicesRequest(List<ToDoList> _toDoLists) {
        this._toDoLists = _toDoLists;
    }

    public ToDoListServicesRequest(int _idList) {
        this._idList = _idList;
    }

    public int get_idList() {
        return _idList;
    }

    public void set_idList(int _idList) {
        this._idList = _idList;
    }

    public ToDoListServicesRequest(boolean isTest) {
        this.isTest = isTest;
    }

    public List<ToDoList> get_toDoLists() {
        return _toDoLists;
    }

    public void set_toDoLists(List<ToDoList> _toDoLists) {
        this._toDoLists = _toDoLists;
    }

    public boolean getIsTest() {
        return isTest;
    }

    public void setTest(boolean isTest) {
        this.isTest = isTest;
    }

}
