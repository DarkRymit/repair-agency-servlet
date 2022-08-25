package com.epam.finalproject.request.receipt.create;

import java.util.Objects;

public class ReceiptItemCreateRequest {
    Long repairWorkID;

    public ReceiptItemCreateRequest(Long repairWorkID) {
        this.repairWorkID = repairWorkID;
    }

    public ReceiptItemCreateRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptItemCreateRequest that = (ReceiptItemCreateRequest) o;
        return Objects.equals(repairWorkID, that.repairWorkID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repairWorkID);
    }

    @Override
    public String toString() {
        return "ReceiptItemCreateRequest{" +
                "repairWorkID=" + repairWorkID +
                '}';
    }

    public Long getRepairWorkID() {
        return this.repairWorkID;
    }

    public void setRepairWorkID(Long repairWorkID) {
        this.repairWorkID = repairWorkID;
    }
}
