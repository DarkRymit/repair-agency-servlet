package com.epam.finalproject.dto;

import java.util.Objects;

public class RepairWorkStatusDTO {

    private String name;

    public RepairWorkStatusDTO(String name) {
        this.name = name;
    }

    public RepairWorkStatusDTO() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepairWorkStatusDTO that = (RepairWorkStatusDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "RepairWorkStatusDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
