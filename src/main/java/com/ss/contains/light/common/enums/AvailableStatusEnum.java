package com.ss.contains.light.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 可用状态枚举
 *
 * @author NotEdibleSalt
 */
@Getter
@AllArgsConstructor
public enum AvailableStatusEnum {

    ENABLE("可用"),
    DISABLE("禁用");

    private final String des;


}
