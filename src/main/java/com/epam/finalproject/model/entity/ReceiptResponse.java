package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.util.Objects;


@SqlTable("master_responses")
public class ReceiptResponse {

    @SqlId
    @SqlColumn("receipt_id")
    private Long id;

    private Receipt receipt;

    @SqlColumn("text")
    private String text;

    @SqlColumn("rating")
    private Integer rating;

    public ReceiptResponse(Long id, Receipt receipt, String text, Integer rating) {
        this.id = id;
        this.receipt = receipt;
        this.text = text;
        this.rating = rating;
    }

    public ReceiptResponse() {
    }

    public static ReceiptResponseBuilder builder() {
        return new ReceiptResponseBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Receipt getReceipt() {
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

    public void setReceipt(Receipt receipt) {
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
        ReceiptResponse response = (ReceiptResponse) o;
        return Objects.equals(id, response.id)  && Objects.equals(text, response.text) && Objects.equals(rating,
                response.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, rating);
    }

    @Override
    public String toString() {
        return "ReceiptResponse{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                '}';
    }

    public static class ReceiptResponseBuilder {
        private Long id;
        private Receipt receipt;
        private String text;
        private Integer rating;

        ReceiptResponseBuilder() {
        }

        public ReceiptResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReceiptResponseBuilder receipt(Receipt receipt) {
            this.receipt = receipt;
            return this;
        }

        public ReceiptResponseBuilder text(String text) {
            this.text = text;
            return this;
        }

        public ReceiptResponseBuilder rating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public ReceiptResponse build() {
            return new ReceiptResponse(id, receipt, text, rating);
        }

        public String toString() {
            return "ReceiptResponse.ReceiptResponseBuilder(id=" + this.id + ", receipt=" + this.receipt + ", text=" + this.text + ", rating=" + this.rating + ")";
        }
    }
}
