package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.*;

import java.util.Objects;


@SqlTable("receipt_deliveries")
public class ReceiptDelivery {

    @SqlId
    @SqlColumn("receipt_id")
    private Long id;

    @SqlMapsId
    @SqlReferenceId(isEntity = false)
    @SqlColumn("receipt_id")
    private Long receiptId;

    @SqlColumn("country")
    private String country;

    @SqlColumn("state")
    private String state;

    @SqlColumn("city")
    private String city;

    @SqlColumn("local_address")
    private String localAddress;

    @SqlColumn("postal_code")
    private String postalCode;

    public ReceiptDelivery(Long id, Long receiptId, String country, String state, String city, String localAddress,
            String postalCode) {
        this.id = id;
        this.receiptId = receiptId;
        this.country = country;
        this.state = state;
        this.city = city;
        this.localAddress = localAddress;
        this.postalCode = postalCode;
    }

    public ReceiptDelivery() {
    }

    public static ReceiptDeliveryBuilder builder() {
        return new ReceiptDeliveryBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Long getReceiptId() {
        return this.receiptId;
    }

    public String getCountry() {
        return this.country;
    }

    public String getState() {
        return this.state;
    }

    public String getCity() {
        return this.city;
    }

    public String getLocalAddress() {
        return this.localAddress;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptDelivery that = (ReceiptDelivery) o;
        return Objects.equals(id, that.id) && Objects.equals(receiptId,
                that.receiptId) && Objects.equals(country, that.country) && Objects.equals(state,
                that.state) && Objects.equals(city, that.city) && Objects.equals(localAddress,
                that.localAddress) && Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, receiptId, country, state, city, localAddress, postalCode);
    }

    @Override
    public String toString() {
        return "ReceiptDelivery{" +
                "id=" + id +
                ", receiptId=" + receiptId +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", localAddress='" + localAddress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

    public static class ReceiptDeliveryBuilder {
        private Long id;
        private Long receiptId;
        private String country;
        private String state;
        private String city;
        private String localAddress;
        private String postalCode;

        ReceiptDeliveryBuilder() {
        }

        public ReceiptDeliveryBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReceiptDeliveryBuilder receiptId(Long receiptId) {
            this.receiptId = receiptId;
            return this;
        }

        public ReceiptDeliveryBuilder country(String country) {
            this.country = country;
            return this;
        }

        public ReceiptDeliveryBuilder state(String state) {
            this.state = state;
            return this;
        }

        public ReceiptDeliveryBuilder city(String city) {
            this.city = city;
            return this;
        }

        public ReceiptDeliveryBuilder localAddress(String localAddress) {
            this.localAddress = localAddress;
            return this;
        }

        public ReceiptDeliveryBuilder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public ReceiptDelivery build() {
            return new ReceiptDelivery(id, receiptId, country, state, city, localAddress, postalCode);
        }

        public String toString() {
            return "ReceiptDelivery.ReceiptDeliveryBuilder(id=" + this.id + ", receiptId=" + this.receiptId + ", country=" + this.country + ", state=" + this.state + ", city=" + this.city + ", localAddress=" + this.localAddress + ", postalCode=" + this.postalCode + ")";
        }
    }
}
