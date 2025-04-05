import type { Task } from './Task'

export class TaskRequest {
  idsList: number[] = []
  idsTask: number[] = []
  tasks?: Array<Task> = []
  isTest: boolean = false

  constructor(idsList?: number[], idsTask?: number[], tasks?: Array<Task>, isTest?: boolean) {
    this.idsList = idsList ?? this.idsList
    this.idsTask = idsTask ?? this.idsTask
    this.tasks = tasks ?? this.tasks
    this.isTest = isTest ?? this.isTest
  }
}
