package com.tododo.tododo.models.dto;

import com.tododo.tododo.models.Color;

public class ColorDTO {
    private String name = "";
    private String color = "";

    public ColorDTO() {
    }

    public ColorDTO(String name, String color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return "ColorDTO [name=" + name + ", color=" + color + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ColorDTO other = (ColorDTO) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void toColorDTO(Color icon) {
        this.name = icon.getName();
        this.color = icon.getColor();
    }
}
