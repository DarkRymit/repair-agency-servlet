package com.epam.finalproject.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ReceiptResponseCreateRequest {

    private Long receiptId;

    @NotNull
    @Size(max = 255)
    private String text;

    @NotNull
    @Max(5)
    @Min(1)
    private Integer rating;

    public ReceiptResponseCreateRequest(Long receiptId, String text, Integer rating) {
        this.receiptId = receiptId;
        this.text = text;
        this.rating = rating;
    }

    public ReceiptResponseCreateRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiptResponseCreateRequest that = (ReceiptResponseCreateRequest) o;
        return Objects.equals(receiptId, that.receiptId) && Objects.equals(text,
                that.text) && Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiptId, text, rating);
    }

    @Override
    public String toString() {
        return "ReceiptResponseCreateRequest{" +
                "receiptId=" + receiptId +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                '}';
    }

    public Long getReceiptId() {
        return this.receiptId;
    }

    public String getText() {
        return this.text;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
