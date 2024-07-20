package com.bid90.psm.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageList<T>  extends Response{

    int size;
    List<T> content;
    int number;
    int totalPages;
    long totalElements;
    boolean first;
    boolean last;

    public PageList(Page<T> page) {
        this.size = page.getSize();
        this.content = page.getContent();
        this.number = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.first = page.isFirst();
        this.last = page.isLast();
    }
}
