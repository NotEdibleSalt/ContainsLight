package com.ss.contains.light.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 自定义异常
 *
 * @version 1.0
 * @date 2022-5-2
 */
@Data
@AllArgsConstructor
public class Ex extends RuntimeException {

    private String message;

}
