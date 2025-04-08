import { ElementType } from '@/types/elementType'
import { Icon } from '../classes/Icon'

export class SubTask {
  id: number
  type: ElementType = ElementType.SUBTASK
  taskContent: string = ''
  description: string = ''
  icon: Icon = new Icon()
  isCompleted: boolean = false

  constructor(params?: {
    id?: number
    type?: ElementType
    taskContent?: string
    description?: string
    icon?: Icon
  }) {
    this.id = params?.id ?? 0
    this.type = params?.type ?? ElementType.SUBTASK
    this.taskContent = params?.taskContent ?? ''
    this.description = params?.description ?? ''
    this.icon = params?.icon ?? new Icon()
  }
}
