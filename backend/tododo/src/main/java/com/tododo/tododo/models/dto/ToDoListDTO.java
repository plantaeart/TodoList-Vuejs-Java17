package com.tododo.tododo.models.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.tododo.tododo.enums.ElementType;

public class ToDoListDTO {
    private int id;
    private ElementType type = ElementType.TODOLIST;
    private String name;
    private String description = "";
    private int completionPercentage = 0;
    private ColorDTO ColorDTO = new ColorDTO();
    private IconDTO icon = new IconDTO();
    private Boolean isCompleted = false;
    private List<TaskDTO> tasks = new ArrayList<TaskDTO>();
    private String updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

    public ToDoListDTO(ElementType type, String name, String description, int completionPercentage, ColorDTO ColorDTO,
            IconDTO icon, Boolean isCompleted, List<TaskDTO> tasks, String updateDate) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.completionPercentage = completionPercentage;
        this.ColorDTO = ColorDTO;
        this.icon = icon;
        this.isCompleted = isCompleted;
        this.tasks = tasks;
        this.updateDate = updateDate;
    }

    public ToDoListDTO(ElementType type, String name, String description, int completionPercentage, ColorDTO ColorDTO,
            IconDTO icon, Boolean isCompleted, List<TaskDTO> tasks) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.completionPercentage = completionPercentage;
        this.ColorDTO = ColorDTO;
        this.icon = icon;
        this.isCompleted = isCompleted;
        this.tasks = tasks;
    }

    public ToDoListDTO(String name) {
        this.name = name;
    }

    public ToDoListDTO() {
    }

    @Override
    public String toString() {
        return "ToDoListDTO [id=" + id + ", type=" + type + ", name=" + name + ", description=" + description
                + ", completionPercentage=" + completionPercentage + ", ColorDTO=" + ColorDTO + ", icon=" + icon
                + ", isCompleted=" + isCompleted + ", tasks=" + tasks + ", updateDate=" + updateDate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + completionPercentage;
        result = prime * result + ((ColorDTO == null) ? 0 : ColorDTO.hashCode());
        result = prime * result + ((icon == null) ? 0 : icon.hashCode());
        result = prime * result + ((isCompleted == null) ? 0 : isCompleted.hashCode());
        result = prime * result + ((tasks == null) ? 0 : tasks.hashCode());
        result = prime * result + ((updateDate == null) ? 0 : updateDate.hashCode());
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
        ToDoListDTO other = (ToDoListDTO) obj;
        if (id != other.id)
            return false;
        if (type != other.type)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (completionPercentage != other.completionPercentage)
            return false;
        if (ColorDTO == null) {
            if (other.ColorDTO != null)
                return false;
        } else if (!ColorDTO.equals(other.ColorDTO))
            return false;
        if (icon == null) {
            if (other.icon != null)
                return false;
        } else if (!icon.equals(other.icon))
            return false;
        if (isCompleted == null) {
            if (other.isCompleted != null)
                return false;
        } else if (!isCompleted.equals(other.isCompleted))
            return false;
        if (tasks == null) {
            if (other.tasks != null)
                return false;
        } else if (!tasks.equals(other.tasks))
            return false;
        if (updateDate == null) {
            if (other.updateDate != null)
                return false;
        } else if (!updateDate.equals(other.updateDate))
            return false;
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public ColorDTO getColor() {
        return ColorDTO;
    }

    public void setColor(ColorDTO ColorDTO) {
        this.ColorDTO = ColorDTO;
    }

    public IconDTO getIcon() {
        return icon;
    }

    public void setIcon(IconDTO icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    /************************* OTHERS *************************/

    public void setToDoListDTO(ToDoListDTO toDoList) {
        if (toDoList.getId() != 0) {
            this.id = toDoList.getId();
        }
        if (toDoList.getType() != null) {
            this.type = toDoList.getType();
        }
        if (toDoList.getName() != null) {
            this.name = toDoList.getName();
        }
        if (toDoList.getDescription() != null) {
            this.description = toDoList.getDescription();
        }
        if (toDoList.getCompletionPercentage() != 0) {
            this.completionPercentage = toDoList.getCompletionPercentage();
        }
        if (toDoList.getColor() != null) {
            this.ColorDTO = toDoList.getColor();
        }
        if (toDoList.getIcon() != null) {
            this.icon = toDoList.getIcon();
        }
        if (toDoList.getIsCompleted() != null) {
            this.isCompleted = toDoList.getIsCompleted();
        }
        if (toDoList.getTasks() != null && !toDoList.getTasks().isEmpty()) {
            this.tasks = toDoList.getTasks();
        } else {
            this.tasks = new ArrayList<TaskDTO>();
        }
        if (toDoList.getUpdateDate() != null) {
            this.updateDate = toDoList.getUpdateDate();
        }
    }
}