import { Result } from '@/types/result'
import type { ToDoList } from './ToDoList'

export class ToDoListResponse {
  currentResult: Result
  message: string = ''
  toDoLists: Array<ToDoList> = []

  constructor(currentResult?: Result, message?: string, toDoLists?: Array<ToDoList>) {
    this.currentResult = currentResult ?? Result.ERROR
    this.message = message ?? ''
    this.toDoLists = toDoLists ?? []
  }
}
