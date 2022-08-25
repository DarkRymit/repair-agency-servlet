package com.epam.finalproject.dto;

import com.epam.finalproject.model.entity.AppCurrency;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

public class ReceiptDTO {

    private Long id;

    private UserDTO user;

    private ReceiptStatusDTO status;

    private UserDTO master;

    private Set<ReceiptItemDTO> items;

    private ReceiptDeliveryDTO delivery;

    private RepairCategoryDTO category;

    private BigDecimal totalPrice;

    private AppCurrency priceCurrency;

    private String note;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="EEE MMM dd HH:mm:ss Z yyyy")
    private ZonedDateTime creationDate;

    private String createdBy;

    private String lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

    public ReceiptDTO(Long id, UserDTO user, ReceiptStatusDTO status, UserDTO master, Set<ReceiptItemDTO> items,
            ReceiptDeliveryDTO delivery, RepairCategoryDTO category, BigDecimal totalPrice, AppCurrency priceCurrency,
            String note, ZonedDateTime creationDate, String createdBy, String lastModifiedBy,
            ZonedDateTime lastModifiedDate) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.master = master;
        this.items = items;
        this.delivery = delivery;
        this.category = category;
        this.totalPrice = totalPrice;
        this.priceCurrency = priceCurrency;
        this.note = note;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    public ReceiptDTO() {
    }

    public Long getId() {
        return this.id;
    }

    public UserDTO getUser() {
        return this.user;
    }

    public ReceiptStatusDTO getStatus() {
        return this.status;
    }

    public UserDTO getMaster() {
        return this.master;
    }

    public Set<ReceiptItemDTO> getItems() {
        return this.items;
    }

    public ReceiptDeliveryDTO getDelivery() {
        return this.delivery;
    }

    public RepairCategoryDTO getCategory() {
        return this.category;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public AppCurrency getPriceCurrency() {
        return this.priceCurrency;
    }

    public String getNote() {
        return this.note;
    }

    public ZonedDateTime getCreationDate() {
        return this.creationDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void setStatus(ReceiptStatusDTO status) {
        this.status = status;
    }

    public void setMaster(UserDTO master) {
        this.master = master;
    }

    public void setItems(Set<ReceiptItemDTO> items) {
        this.items = items;
    }

    public void setDelivery(ReceiptDeliveryDTO delivery) {
        this.delivery = delivery;
    }

    public void setCategory(RepairCategoryDTO category) {
        this.category = category;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPriceCurrency(AppCurrency priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM dd HH:mm:ss Z yyyy")
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptDTO that = (ReceiptDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(user,
                that.user) && Objects.equals(status, that.status) && Objects.equals(master,
                that.master) && Objects.equals(items, that.items) && Objects.equals(delivery,
                that.delivery) && Objects.equals(category, that.category) && Objects.equals(totalPrice,
                that.totalPrice) && Objects.equals(priceCurrency, that.priceCurrency) && Objects.equals(
                note, that.note) && Objects.equals(creationDate, that.creationDate) && Objects.equals(
                createdBy, that.createdBy) && Objects.equals(lastModifiedBy,
                that.lastModifiedBy) && Objects.equals(lastModifiedDate, that.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, status, master, items, delivery, category, totalPrice, priceCurrency, note,
                creationDate, createdBy, lastModifiedBy, lastModifiedDate);
    }

    @Override
    public String toString() {
        return "ReceiptDTO{" +
                "id=" + id +
                ", user=" + user +
                ", status=" + status +
                ", master=" + master +
                ", items=" + items +
                ", delivery=" + delivery +
                ", category=" + category +
                ", totalPrice=" + totalPrice +
                ", priceCurrency=" + priceCurrency +
                ", note='" + note + '\'' +
                ", creationDate=" + creationDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
