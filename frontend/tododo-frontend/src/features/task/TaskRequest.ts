import appEnv from 'app-env'
import type { Task } from './Task'

export class TaskRequest {
  idsList: number[] = []
  idsTask: number[] = []
  tasks?: Array<Task> = []
  isTest: boolean = appEnv.VITE_IS_TEST

  constructor(params?: {
    idsList?: number[]
    idsTask?: number[]
    tasks?: Array<Task>
    isTest?: boolean
  }) {
    this.idsList = params?.idsList ?? []
    this.idsTask = params?.idsTask ?? []
    this.tasks = params?.tasks ?? []
    this.isTest = params?.isTest ?? false
  }
}
