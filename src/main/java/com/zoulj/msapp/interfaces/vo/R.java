package com.zoulj.msapp.interfaces.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<E> {
    private Integer code;
    private String message;
    private E data;

    public R(Integer code, String message) {
        this(code, message, null);
    }

    public R(E data) {
        this(200, "ok", data);
    }
}
