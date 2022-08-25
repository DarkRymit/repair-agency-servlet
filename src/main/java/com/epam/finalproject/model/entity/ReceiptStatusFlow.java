package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlReferenceId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.util.Objects;


@SqlTable("receipt_status_flows")
public class ReceiptStatusFlow {

    @SqlId
    @SqlColumn
    private Long id;

    @SqlReferenceId
    @SqlColumn("from_status_id")
    ReceiptStatus fromStatus;

    @SqlReferenceId
    @SqlColumn("to_status_id")
    ReceiptStatus toStatus;

    @SqlReferenceId
    @SqlColumn("role_id")
    Role role;

    public ReceiptStatusFlow() {
    }

    public Long getId() {
        return this.id;
    }

    public ReceiptStatus getFromStatus() {
        return this.fromStatus;
    }

    public ReceiptStatus getToStatus() {
        return this.toStatus;
    }

    public Role getRole() {
        return this.role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFromStatus(ReceiptStatus fromStatus) {
        this.fromStatus = fromStatus;
    }

    public void setToStatus(ReceiptStatus toStatus) {
        this.toStatus = toStatus;
    }

    public void setRole(Role role) {
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
        ReceiptStatusFlow flow = (ReceiptStatusFlow) o;
        return Objects.equals(id, flow.id) && Objects.equals(fromStatus,
                flow.fromStatus) && Objects.equals(toStatus, flow.toStatus) && Objects.equals(role,
                flow.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromStatus, toStatus, role);
    }

    @Override
    public String toString() {
        return "ReceiptStatusFlow{" +
                "id=" + id +
                ", fromStatus=" + fromStatus +
                ", toStatus=" + toStatus +
                ", role=" + role +
                '}';
    }
}
