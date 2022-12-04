package com.ss.contains.light.config.security.properties;

import com.ss.contains.light.common.enums.MethodEnum;
import lombok.Data;

/**
 * @author NotEdibleSalt
 */
@Data
public class RequestVO {

    /**
     * 请求方法
     */
    private MethodEnum method;

    /**
     * 路径
     */
    private String path;
}
