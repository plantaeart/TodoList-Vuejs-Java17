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

  constructor(
    id: number = 0,
    taskContent: string = '',
    type: ElementType = ElementType.TASK,
    description: string = '',
    completionPercentage: number = 0,
    icon: Icon = new Icon(),
    isCompleted: boolean = false,
    subTasks: Array<SubTask> = [],
  ) {
    this.id = id
    this.type = type
    this.taskContent = taskContent
    this.description = description
    this.completionPercentage = completionPercentage
    this.icon = icon
    this.isCompleted = isCompleted
    this.subTasks = subTasks
  }
}
