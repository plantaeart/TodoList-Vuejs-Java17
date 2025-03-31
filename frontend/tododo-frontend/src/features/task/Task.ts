import { ElementType } from '@/types/elementType'
import type { SubTask } from '../subTask/SubTask'

export class Task {
  id: number
  type: ElementType = ElementType.TASK
  taskContent: String = ''
  description: String = ''
  completionPercentage: number = 0
  icon: String = ''
  isCompleted: Boolean = false
  subTasks: Array<SubTask> = []

  constructor(
    id?: number,
    type?: ElementType,
    taskContent?: String,
    description?: String,
    completionPercentage?: number,
    icon?: String,
    isCompleted?: Boolean,
    subTasks?: Array<SubTask>,
  ) {
    this.id = id ?? 0
    this.type = type ?? this.type
    this.taskContent = taskContent ?? this.taskContent
    this.description = description ?? this.description
    this.completionPercentage = completionPercentage ?? this.completionPercentage
    this.icon = icon ?? this.icon
    this.isCompleted = isCompleted ?? this.isCompleted
    this.subTasks = subTasks ?? this.subTasks
  }
}
