import { ElementType } from '@/types/elementType'
import type { SubTask } from '../subTask/SubTask'
import { Icon } from '../classes/Icon'

export class Task {
  id: number
  type: ElementType = ElementType.TASK
  taskContent: string = ''
  description: string = ''
  completionPercentage: number = 0
  icon: Icon = new Icon()
  isCompleted: boolean = false
  subTasks: Array<SubTask> = []

  constructor(params?: {
    id?: number
    type?: ElementType
    taskContent?: string
    description?: string
    icon?: Icon
  }) {
    this.id = params?.id ?? 0
    this.type = params?.type ?? ElementType.TASK
    this.taskContent = params?.taskContent ?? ''
    this.description = params?.description ?? ''
    this.icon = params?.icon ?? new Icon()
  }
}
