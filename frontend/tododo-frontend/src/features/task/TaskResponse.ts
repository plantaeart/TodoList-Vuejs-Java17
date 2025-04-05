import { Result } from '@/types/result'
import type { Task } from './Task'

export class TaskResponse {
  currentResult: Result
  message: string = ''
  tasks: Array<Task> = []

  constructor(currentResult?: Result, message?: string, tasks?: Array<Task>) {
    this.currentResult = currentResult ?? Result.ERROR
    this.message = message ?? ''
    this.tasks = tasks ?? []
  }
}
