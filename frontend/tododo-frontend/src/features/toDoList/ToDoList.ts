import { ElementType } from '@/types/elementType'
import type { Task } from '../task/Task'
import { Icon } from '../classes/Icon'

export class ToDoList {
  id?: number = 0
  type?: ElementType = ElementType.TODOLIST
  name: string = ''
  description?: string = ''
  completionPercentage?: number = 0
  color?: string = '#FFFFFF'
  icon?: Icon = new Icon()
  isCompleted?: boolean = false
  tasks?: Array<Task> = []
  updateDate?: string

  constructor(
    id: number = 0,
    type: ElementType = ElementType.TODOLIST,
    name: string = '',
    description: string = '',
    completionPercentage: number = 0,
    color: string = '#FFFFFF',
    icon: Icon = new Icon(),
    isCompleted: boolean = false,
    tasks: Array<Task> = [],
    updateDate: string = new Date().toISOString(),
  ) {
    this.id = id
    this.type = type
    this.name = name
    this.description = description
    this.completionPercentage = completionPercentage
    this.color = color
    this.icon = icon
    this.isCompleted = isCompleted
    this.tasks = tasks
    this.updateDate = updateDate
  }
}
