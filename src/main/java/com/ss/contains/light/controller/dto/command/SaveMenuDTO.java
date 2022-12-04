package com.ss.contains.light.controller.dto.command;

import com.ss.contains.light.dos.MenuDO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 保存菜单DTO
 *
 * @author NotEdibleSalt
 */
@Data
public class SaveMenuDTO {

    /**
     * 父菜单id
     */
    private String parentId;

    /**
     * 菜单类型
     */
    @NotBlank(message = "菜单类型不能为空")
    private String type;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单路径
     */
    @NotBlank(message = "菜单路径不能为空")
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
     * 路由名称
     */
    private String routeName;

    /**
     * 路由路径
     */
    private String routePath;

    /**
     * 状态 ENABLE： 启用  DISABLE：禁用
     */
    @NotBlank(message = "状态不能为空")
    private String status;


    public MenuDO toMenuDO(){

        MenuDO menuDO = new MenuDO();
        menuDO.setParentId(this.getParentId());
        menuDO.setType(this.getType());
        menuDO.setName(this.getName());
        menuDO.setPath(this.getPath());
        menuDO.setDescription(this.getDescription());
        menuDO.setSortNumber(this.getSortNumber());
        menuDO.setRouteName(this.getRouteName());
        menuDO.setRoutePath(this.getRoutePath());
        menuDO.setStatus(this.getStatus());
        return menuDO;

    }

}
