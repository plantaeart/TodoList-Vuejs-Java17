import { Result } from '@/types/result'
import type { SubTask } from '../subTask/SubTask'

export class SubTaskResponse {
  currentResult: Result
  message: string = ''
  subTasks: Array<SubTask> = []

  constructor(params?: { currentResult?: Result; message?: string; subTasks?: Array<SubTask> }) {
    this.currentResult = params?.currentResult ?? Result.OK
    this.message = params?.message ?? ''
    this.subTasks = params?.subTasks ?? []
  }
}
