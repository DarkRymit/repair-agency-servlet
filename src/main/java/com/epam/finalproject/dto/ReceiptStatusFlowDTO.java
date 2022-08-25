package com.epam.finalproject.dto;

import java.util.Objects;

public class ReceiptStatusFlowDTO {

    private Long id;

    ReceiptStatusDTO fromStatus;

    ReceiptStatusDTO toStatus;

    RoleDTO role;

    public ReceiptStatusFlowDTO(Long id, ReceiptStatusDTO fromStatus, ReceiptStatusDTO toStatus, RoleDTO role) {
        this.id = id;
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
        this.role = role;
    }

    public ReceiptStatusFlowDTO() {
    }

    public Long getId() {
        return this.id;
    }

    public ReceiptStatusDTO getFromStatus() {
        return this.fromStatus;
    }

    public ReceiptStatusDTO getToStatus() {
        return this.toStatus;
    }

    public RoleDTO getRole() {
        return this.role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFromStatus(ReceiptStatusDTO fromStatus) {
        this.fromStatus = fromStatus;
    }

    public void setToStatus(ReceiptStatusDTO toStatus) {
        this.toStatus = toStatus;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptStatusFlowDTO flowDTO = (ReceiptStatusFlowDTO) o;
        return Objects.equals(id, flowDTO.id) && Objects.equals(fromStatus,
                flowDTO.fromStatus) && Objects.equals(toStatus, flowDTO.toStatus) && Objects.equals(
                role, flowDTO.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromStatus, toStatus, role);
    }

    @Override
    public String toString() {
        return "ReceiptStatusFlowDTO{" +
                "id=" + id +
                ", fromStatus=" + fromStatus +
                ", toStatus=" + toStatus +
                ", role=" + role +
                '}';
    }
}
