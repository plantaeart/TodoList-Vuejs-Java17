import { Result } from '@/types/result'
import type { ToDoList } from './ToDoList'

export class ToDoListResponse {
  currentResult: Result
  message: String = ''
  toDoLists: Array<ToDoList> = []

  constructor(currentResult?: Result, message?: String, toDoLists?: Array<ToDoList>) {
    this.currentResult = currentResult ?? Result.ERROR
    this.message = message ?? ''
    this.toDoLists = toDoLists ?? []
  }
}
