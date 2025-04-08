export class Color {
  name: string = ''
  color: string = ''

  constructor(name?: string, color?: string) {
    this.name = name ?? this.name
    this.color = color ?? this.color
  }
}
