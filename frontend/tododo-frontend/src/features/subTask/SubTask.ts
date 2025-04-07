import { ElementType } from '@/types/elementType'
import { Icon } from '../classes/Icon'

export class SubTask {
  id: number
  type: ElementType = ElementType.SUBTASK
  taskContent: string = ''
  description: string = ''
  icon: Icon = new Icon()

  constructor(
    id?: number,
    type?: ElementType,
    taskContent?: string,
    description?: string,
    icon?: Icon,
  ) {
    this.id = id ?? 0
    this.type = type ?? this.type
    this.taskContent = taskContent ?? this.taskContent
    this.description = description ?? this.description
    this.icon = icon ?? this.icon
  }
}
