package com.tododo.tododo.models.servivesRequest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tododo.tododo.models.ToDoList;

public class ToDoListRequest {
    @JsonProperty(value = "idList", required = false)
    protected int _idList;
    @JsonProperty(value = "toDoLists", required = false)
    private List<ToDoList> _toDoLists;

    public ToDoListRequest() {
    }

    public ToDoListRequest(int _idList, List<ToDoList> _toDoLists) {
        this._idList = _idList;
        this._toDoLists = _toDoLists;
    }

    public ToDoListRequest(List<ToDoList> _toDoLists) {
        this._toDoLists = _toDoLists;
    }

    public ToDoListRequest(int _idList) {
        this._idList = _idList;
    }

    public int get_idList() {
        return _idList;
    }

    public List<ToDoList> get_toDoLists() {
        return _toDoLists;
    }

    public void set_idList(int _idList) {
        this._idList = _idList;
    }

    public void set_toDoLists(List<ToDoList> _toDoLists) {
        this._toDoLists = _toDoLists;
    }

}
