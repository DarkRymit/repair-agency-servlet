package com.epam.finalproject.framework.data;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageImpl<T> implements Page<T>, Serializable {
    private static final long serialVersionUID = 867755909294344406L;
    private final long total;
    private final List<T> content = new ArrayList<>();
    private final Pageable pageable;

    public PageImpl(Collection<T> content, Pageable pageable, long total) {
        this.content.addAll(content);
        this.pageable = pageable;
        this.total = pageable.toOptional()
                .filter(it -> !content.isEmpty())
                .filter(it -> it.getOffset() + it.getPageSize() > total)
                .map(it -> it.getOffset() + content.size())
                .orElse(total);
    }

    public PageImpl(Collection<T> content) {
        this(content, Pageable.unpaged(), null == content ? 0L : (long) content.size());
    }

    public int getTotalPages() {
        return this.getSize() == 0 ? 1 : (int) Math.ceil((double) this.total / (double) this.getSize());
    }

    public long getTotalElements() {
        return this.total;
    }

    public boolean hasNext() {
        return this.getNumber() + 1 < this.getTotalPages();
    }

    public boolean isLast() {
        return !this.hasNext();
    }

    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new PageImpl<>(this.getConvertedContent(converter), this.getPageable(), this.total);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageImpl<?> page = (PageImpl<?>) o;

        if (total != page.total) return false;
        if (!content.equals(page.content)) return false;
        return Objects.equals(pageable, page.pageable);
    }

    @Override
    public int hashCode() {
        int result = (int) (total ^ (total >>> 32));
        result = 31 * result + content.hashCode();
        result = 31 * result + (pageable != null ? pageable.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PageImpl{" + "total=" + total + ", content=" + content + ", pageable=" + pageable + '}';
    }

    public int getNumber() {
        return this.pageable.isPaged() ? this.pageable.getPageNumber() : 0;
    }

    public int getSize() {
        return this.pageable.isPaged() ? this.pageable.getPageSize() : this.content.size();
    }

    public int getNumberOfElements() {
        return this.content.size();
    }

    public boolean hasPrevious() {
        return this.getNumber() > 0;
    }

    public boolean isFirst() {
        return !this.hasPrevious();
    }

    public Pageable nextPageable() {
        return this.hasNext() ? this.pageable.next() : Pageable.unpaged();
    }

    public Pageable previousPageable() {
        return this.hasPrevious() ? this.pageable.previousOrFirst() : Pageable.unpaged();
    }

    public boolean hasContent() {
        return !this.content.isEmpty();
    }

    public List<T> getContent() {
        return Collections.unmodifiableList(this.content);
    }

    @Override
    public Pageable getPageable() {
        return this.pageable;
    }

    public Sort getSort() {
        return this.pageable.getSort();
    }

    public Iterator<T> iterator() {
        return this.content.iterator();
    }

    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
        return this.stream().map(converter).collect(Collectors.toList());
    }
}
