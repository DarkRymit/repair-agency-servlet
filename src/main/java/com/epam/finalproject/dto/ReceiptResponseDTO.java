package com.epam.finalproject.dto;

import java.util.Objects;

public class ReceiptResponseDTO {

    private Long id;

    private ReceiptDTO receipt;

    private String text;

    private Integer rating;

    public ReceiptResponseDTO(Long id, ReceiptDTO receipt, String text, Integer rating) {
        this.id = id;
        this.receipt = receipt;
        this.text = text;
        this.rating = rating;
    }

    public ReceiptResponseDTO() {
    }

    public Long getId() {
        return this.id;
    }

    public ReceiptDTO getReceipt() {
        return this.receipt;
    }

    public String getText() {
        return this.text;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReceipt(ReceiptDTO receipt) {
        this.receipt = receipt;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptResponseDTO that = (ReceiptResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(receipt,
                that.receipt) && Objects.equals(text, that.text) && Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, receipt, text, rating);
    }

    @Override
    public String toString() {
        return "ReceiptResponseDTO{" +
                "id=" + id +
                ", receipt=" + receipt +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                '}';
    }
}
