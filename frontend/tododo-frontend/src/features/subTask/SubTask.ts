import { ElementType } from '@/types/elementType'

export class SubTask {
  id: number
  type: ElementType = ElementType.SUBTASK
  taskContent: String = ''
  description: String = ''
  icon: String = ''

  constructor(
    id?: number,
    type?: ElementType,
    taskContent?: String,
    description?: String,
    icon?: String,
  ) {
    this.id = id ?? 0
    this.type = type ?? this.type
    this.taskContent = taskContent ?? this.taskContent
    this.description = description ?? this.description
    this.icon = icon ?? this.icon
  }
}
