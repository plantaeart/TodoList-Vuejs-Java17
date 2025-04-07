export class Icon {
  name: string = ''
  icon: string = ''

  constructor(name?: string, icon?: string) {
    this.name = name ?? this.name
    this.icon = icon ?? this.icon
  }
}
