import { ElementType } from '@/types/elementType'
import type { Task } from '../task/Task'
import { Icon } from '../classes/Icon'
import { Color } from '../classes/Color'

export class ToDoList {
  id?: number = 0
  type?: ElementType = ElementType.TODOLIST
  name: string = ''
  description?: string = ''
  completionPercentage?: number = 0
  color?: Color = new Color()
  icon?: Icon = new Icon()
  isCompleted?: boolean = false
  tasks?: Array<Task> = []
  updateDate?: string

  constructor(params?: {
    id?: number
    type?: ElementType
    name?: string
    description?: string
    completionPercentage?: number
    color?: Color
    icon?: Icon
    isCompleted?: boolean
    tasks?: Array<Task>
    updateDate?: string
  }) {
    this.id = params?.id ?? 0
    this.type = params?.type ?? ElementType.TODOLIST
    this.name = params?.name ?? ''
    this.description = params?.description ?? ''
    this.completionPercentage = params?.completionPercentage ?? 0
    this.color = params?.color ?? new Color()
    this.icon = params?.icon ?? new Icon()
    this.isCompleted = params?.isCompleted ?? false
    this.tasks = params?.tasks ?? []
    this.updateDate = params?.updateDate ?? ''
  }
}
