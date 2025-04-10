import appEnv from 'app-env'
import type { SubTask } from './SubTask'

export class SubTaskRequest {
  idsList: number[] = []
  idsTask: number[] = []
  idsSubTask: number[] = []
  subTasks?: Array<SubTask> = []
  isTest: boolean = appEnv.VITE_IS_TEST

  constructor(params?: {
    idsList?: number[]
    idsTask?: number[]
    idsSubTask?: number[]
    subTasks?: Array<SubTask>
    isTest?: boolean
  }) {
    this.idsList = params?.idsList ?? []
    this.idsTask = params?.idsTask ?? []
    this.idsSubTask = params?.idsSubTask ?? []
    this.subTasks = params?.subTasks ?? []
    this.isTest = params?.isTest ?? false
  }
}
