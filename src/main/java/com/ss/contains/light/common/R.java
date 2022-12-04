package com.ss.contains.light.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author NotEdibleSalt
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {

    private Integer code;

    private String message;

    private T res;

    public static <T> R<T> success() {

        R<T> r = new R<>();
        r.setCode(200);
        return r;
    }

    public static <T> R<T> success(T data) {

        R<T> r = new R<>();
        r.setCode(200);
        r.setRes(data);
        return r;
    }

    public static <T> R<T> message(String msg) {

        R<T> r = new R<>();
        r.setCode(200);
        r.setMessage(msg);
        return r;
    }
}
