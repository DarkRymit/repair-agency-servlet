package com.epam.finalproject.framework.data;

enum Unpaged implements Pageable {
    INSTANCE;

    private Unpaged() {
    }

    public boolean isPaged() {
        return false;
    }

    public Pageable previousOrFirst() {
        return this;
    }

    public Pageable next() {
        return this;
    }

    public boolean hasPrevious() {
        return false;
    }

    public Sort getSort() {
        return Sort.unsorted();
    }

    public int getPageSize() {
        throw new UnsupportedOperationException();
    }

    public int getPageNumber() {
        throw new UnsupportedOperationException();
    }

    public long getOffset() {
        throw new UnsupportedOperationException();
    }

    public Pageable first() {
        return this;
    }

    public Pageable withPage(int pageNumber) {
        if (pageNumber == 0) {
            return this;
        } else {
            throw new UnsupportedOperationException();
        }
    }
}