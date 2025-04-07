package com.tododo.tododo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tododo.tododo.models.dto.IconDTO;

public class Icon {
    @JsonProperty(value = "name", required = false)
    private String name = "";
    @JsonProperty(value = "icon", required = false)
    private String icon = "";

    public Icon() {
    }

    public Icon(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((icon == null) ? 0 : icon.hashCode());
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
        Icon other = (Icon) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (icon == null) {
            if (other.icon != null)
                return false;
        } else if (!icon.equals(other.icon))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Icon [name=" + name + ", icon=" + icon + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void fromIconDTO(IconDTO icon) {
        this.name = icon.getName();
        this.icon = icon.getIcon();
    }
}
