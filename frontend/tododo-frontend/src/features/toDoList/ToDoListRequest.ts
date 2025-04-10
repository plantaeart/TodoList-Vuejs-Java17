import appEnv from 'app-env'
import type { ToDoList } from './ToDoList'

export class ToDoListRequest {
  idsList: number[] = []
  toDoLists?: Array<ToDoList> = []
  isTest: boolean = appEnv.VITE_IS_TEST

  constructor(idsList?: number[], toDoLists?: Array<ToDoList>, isTest?: boolean) {
    this.idsList = idsList ?? this.idsList
    this.toDoLists = toDoLists ?? this.toDoLists
    this.isTest = isTest ?? this.isTest
  }
}
