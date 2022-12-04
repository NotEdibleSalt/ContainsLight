package com.ss.contains.light.config.security.properties;

import com.ss.contains.light.common.enums.MethodEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author NotEdibleSalt
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix = "secure.ignore")
public class IgnoreRequestsProperties {

    private Set<RequestVO> requestVOs;

    /**
     * 根据请求方法分组
     *
     * @return java.util.Map<com.ss.contains.light.common.enums.MethodEnum, java.util.List < com.ss.contains.light.config.security.properties.Request>>
     */
    public Map<MethodEnum, List<RequestVO>> groupHttpMethod() {

        return requestVOs.stream()
                .collect(Collectors.groupingBy(RequestVO::getMethod));

    }
}
