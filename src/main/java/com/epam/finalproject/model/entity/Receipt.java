package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlReferenceId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;


@SqlTable("receipts")
public class Receipt {

    @SqlId
    @SqlColumn
    private Long id;

    @SqlReferenceId
    @SqlColumn("user_id")
    private User user;

    @SqlReferenceId
    @SqlColumn("receipt_status_id")
    private ReceiptStatus status;

    @SqlReferenceId
    @SqlColumn("master_id")
    private User master;

    private Set<ReceiptItem> items;

    private ReceiptDelivery delivery;

    @SqlReferenceId
    @SqlColumn("category_id")
    private RepairCategory category;

    @SqlColumn("total_price")
    private BigDecimal totalPrice;

    @SqlReferenceId
    @SqlColumn("currency_id")
    private AppCurrency priceCurrency;

    @SqlColumn("note")
    private String note;

    @SqlColumn("creation_date")
    private Instant creationDate;

    @SqlColumn("created_by")
    private String createdBy;

    @SqlColumn("last_modified_by")
    private String lastModifiedBy;

    @SqlColumn("last_modified_date")
    private Instant lastModifiedDate;

    public Receipt(Long id, User user, ReceiptStatus status, User master, Set<ReceiptItem> items,
            ReceiptDelivery delivery, RepairCategory category, BigDecimal totalPrice, AppCurrency priceCurrency,
            String note, Instant creationDate, String createdBy, String lastModifiedBy, Instant lastModifiedDate) {
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

    public Receipt() {
    }

    public static ReceiptBuilder builder() {
        return new ReceiptBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public ReceiptStatus getStatus() {
        return this.status;
    }

    public User getMaster() {
        return this.master;
    }

    public Set<ReceiptItem> getItems() {
        return this.items;
    }

    public ReceiptDelivery getDelivery() {
        return this.delivery;
    }

    public RepairCategory getCategory() {
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

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(ReceiptStatus status) {
        this.status = status;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public void setItems(Set<ReceiptItem> items) {
        this.items = items;
    }

    public void setDelivery(ReceiptDelivery delivery) {
        this.delivery = delivery;
    }

    public void setCategory(RepairCategory category) {
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

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
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
        Receipt receipt = (Receipt) o;
        return Objects.equals(id, receipt.id) && Objects.equals(user,
                receipt.user) && Objects.equals(status, receipt.status) && Objects.equals(master,
                receipt.master) && Objects.equals(items, receipt.items) && Objects.equals(delivery,
                receipt.delivery) && Objects.equals(category, receipt.category) && Objects.equals(
                totalPrice, receipt.totalPrice) && Objects.equals(priceCurrency,
                receipt.priceCurrency) && Objects.equals(note, receipt.note) && Objects.equals(
                creationDate, receipt.creationDate) && Objects.equals(createdBy,
                receipt.createdBy) && Objects.equals(lastModifiedBy,
                receipt.lastModifiedBy) && Objects.equals(lastModifiedDate, receipt.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, status, master, items, delivery, category, totalPrice, priceCurrency, note,
                creationDate, createdBy, lastModifiedBy, lastModifiedDate);
    }

    @Override
    public String toString() {
        return "Receipt{" +
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

    public static class ReceiptBuilder {
        private Long id;
        private User user;
        private ReceiptStatus status;
        private User master;
        private Set<ReceiptItem> items;
        private ReceiptDelivery delivery;
        private RepairCategory category;
        private BigDecimal totalPrice;
        private AppCurrency priceCurrency;
        private String note;
        private Instant creationDate;
        private String createdBy;
        private String lastModifiedBy;
        private Instant lastModifiedDate;

        ReceiptBuilder() {
        }

        public ReceiptBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReceiptBuilder user(User user) {
            this.user = user;
            return this;
        }

        public ReceiptBuilder status(ReceiptStatus status) {
            this.status = status;
            return this;
        }

        public ReceiptBuilder master(User master) {
            this.master = master;
            return this;
        }

        public ReceiptBuilder items(Set<ReceiptItem> items) {
            this.items = items;
            return this;
        }

        public ReceiptBuilder delivery(ReceiptDelivery delivery) {
            this.delivery = delivery;
            return this;
        }

        public ReceiptBuilder category(RepairCategory category) {
            this.category = category;
            return this;
        }

        public ReceiptBuilder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public ReceiptBuilder priceCurrency(AppCurrency priceCurrency) {
            this.priceCurrency = priceCurrency;
            return this;
        }

        public ReceiptBuilder note(String note) {
            this.note = note;
            return this;
        }

        public ReceiptBuilder creationDate(Instant creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public ReceiptBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public ReceiptBuilder lastModifiedBy(String lastModifiedBy) {
            this.lastModifiedBy = lastModifiedBy;
            return this;
        }

        public ReceiptBuilder lastModifiedDate(Instant lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
            return this;
        }

        public Receipt build() {
            return new Receipt(id, user, status, master, items, delivery, category, totalPrice, priceCurrency, note,
                    creationDate, createdBy, lastModifiedBy, lastModifiedDate);
        }

        public String toString() {
            return "Receipt.ReceiptBuilder(id=" + this.id + ", user=" + this.user + ", status=" + this.status + ", master=" + this.master + ", items=" + this.items + ", delivery=" + this.delivery + ", category=" + this.category + ", totalPrice=" + this.totalPrice + ", priceCurrency=" + this.priceCurrency + ", note=" + this.note + ", creationDate=" + this.creationDate + ", createdBy=" + this.createdBy + ", lastModifiedBy=" + this.lastModifiedBy + ", lastModifiedDate=" + this.lastModifiedDate + ")";
        }
    }
}
