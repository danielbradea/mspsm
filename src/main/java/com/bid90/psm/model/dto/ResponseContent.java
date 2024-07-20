package com.bid90.psm.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseContent<T> extends Response {
    T content;
    public ResponseContent(T content) {
        this.content = content;
    }
}
