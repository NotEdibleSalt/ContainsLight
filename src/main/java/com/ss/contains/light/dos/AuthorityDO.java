package com.ss.contains.light.dos;

import com.ss.contains.light.base.DOBase;
import com.ss.contains.light.common.enums.MethodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 系统权限DO
 *
 * @author NotEdibleSalt
 * @date 2022-05-05
 */
@Data
@Table("authority")
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDO extends DOBase implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 请求方式
     */
    private MethodEnum method;

    /**
     * 接口uri
     */
    private String url;

    /**
     * 描述
     */
    private String description;

}
