import { ElementType } from '@/types/elementType'
import type { Task } from '../task/Task'
import { formatDate } from '@/utils/dateUtils'

export class ToDoList {
  id: number = 0
  type: ElementType = ElementType.TODOLIST
  name: String = ''
  description: String = ''
  completionPercentage: number = 0
  color: String = '#FFFFFF'
  icon: String = ''
  isCompleted: Boolean = false
  tasks: Array<Task> = []
  updateDate: String = formatDate(new Date())

  constructor(
    id?: number,
    type?: ElementType,
    name?: String,
    description?: String,
    completionPercentage?: number,
    color?: String,
    icon?: String,
    isCompleted?: Boolean,
    tasks?: Array<Task>,
    updateDate?: String,
  ) {
    this.id = id ?? this.id
    this.type = type ?? this.type
    this.name = name ?? this.name
    this.description = description ?? this.description
    this.completionPercentage = completionPercentage ?? this.completionPercentage
    this.color = color ?? this.color
    this.icon = icon ?? this.icon
    this.isCompleted = isCompleted ?? this.isCompleted
    this.tasks = tasks ?? this.tasks
    this.updateDate = updateDate ?? this.updateDate
  }
}
