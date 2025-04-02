import { ElementType } from '@/types/elementType'
import type { Task } from '../task/Task'

export class ToDoList {
  id?: number = 0
  type?: ElementType = ElementType.TODOLIST
  name: string = ''
  description?: string = ''
  completionPercentage?: number = 0
  color?: string = '#FFFFFF'
  icon?: string = ''
  isCompleted?: boolean = false
  tasks?: Array<Task> = []
  updateDate?: string

  constructor(
    id?: number,
    type?: ElementType,
    name?: string,
    description?: string,
    completionPercentage?: number,
    color?: string,
    icon?: string,
    isCompleted?: boolean,
    tasks?: Array<Task>,
    updateDate?: string,
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
