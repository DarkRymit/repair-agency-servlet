package com.epam.finalproject.dto;

import java.util.Objects;
import java.util.Set;

public class RepairCategoryDTO {

    private Long id;

    private String keyName;

    private String name;

    private Set<RepairWorkDTO> works;

    public RepairCategoryDTO(Long id, String keyName, String name, Set<RepairWorkDTO> works) {
        this.id = id;
        this.keyName = keyName;
        this.name = name;
        this.works = works;
    }

    public RepairCategoryDTO() {
    }

    public Long getId() {
        return this.id;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public String getName() {
        return this.name;
    }

    public Set<RepairWorkDTO> getWorks() {
        return this.works;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorks(Set<RepairWorkDTO> works) {
        this.works = works;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepairCategoryDTO that = (RepairCategoryDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(keyName,
                that.keyName) && Objects.equals(name, that.name) && Objects.equals(works, that.works);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyName, name, works);
    }

    @Override
    public String toString() {
        return "RepairCategoryDTO{" +
                "id=" + id +
                ", keyName='" + keyName + '\'' +
                ", name='" + name + '\'' +
                ", works=" + works +
                '}';
    }
}
