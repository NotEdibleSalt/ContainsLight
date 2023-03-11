package com.ss.contains.light.dos;

import com.ss.contains.light.base.DOBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 菜单DO
 *
 * @author NotEdibleSalt
 */

@Data
@Table("menu")
@NoArgsConstructor
@AllArgsConstructor
public class MenuDO extends DOBase implements Serializable {

    /**
     * 父菜单id
     */
    private String parentId;

    /**
     * 父菜单名称
     */
    @Transient
    private String parentName;

    /**
     * 菜单类型
     */
    private String menuType;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序值
     */
    private Integer sortNumber;

    /**
     * 路由标题
     */
    private String routeName;

    /**
     * 路由路径
     */
    private String routePath;

    /**
     * 状态 ENABLE： 启用  DISABLE：禁用
     */
    private String status;

}
