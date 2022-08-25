package com.epam.finalproject.request.receipt.create;

import java.util.Objects;

public class ReceiptDeliveryCreateRequest {

    private String country;

    private String state;

    private String city;

    private String localAddress;

    private String postalCode;

    public ReceiptDeliveryCreateRequest(String country, String state, String city, String localAddress,
            String postalCode) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.localAddress = localAddress;
        this.postalCode = postalCode;
    }

    public ReceiptDeliveryCreateRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptDeliveryCreateRequest that = (ReceiptDeliveryCreateRequest) o;
        return Objects.equals(country, that.country) && Objects.equals(state,
                that.state) && Objects.equals(city, that.city) && Objects.equals(localAddress,
                that.localAddress) && Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, state, city, localAddress, postalCode);
    }

    @Override
    public String toString() {
        return "ReceiptDeliveryCreateRequest{" +
                "country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", localAddress='" + localAddress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
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
}
