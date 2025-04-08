package com.tododo.tododo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tododo.tododo.models.dto.ColorDTO;

public class Color {
    @JsonProperty(value = "name", required = false)
    private String name = "";
    @JsonProperty(value = "color", required = false)
    private String color = "";

    public Color() {
    }

    public Color(String name, String color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Color [name=" + name + ", color=" + color + "]";
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
        Color other = (Color) obj;
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

    public void fromColorDTO(ColorDTO color) {
        this.name = color.getName();
        this.color = color.getColor();
    }
}
