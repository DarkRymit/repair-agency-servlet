package com.epam.finalproject.dto;

import java.util.Objects;

public class ReceiptStatusDTO {

    private Long id;

    private String name;

    public ReceiptStatusDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ReceiptStatusDTO() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
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
        ReceiptStatusDTO that = (ReceiptStatusDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "ReceiptStatusDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
