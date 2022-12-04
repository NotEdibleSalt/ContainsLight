package com.ss.contains.light.controller.dto.command;

import com.ss.contains.light.common.enums.AvailableStatusEnum;
import com.ss.contains.light.dos.RoleDO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 保存角色DTO
 */
@Data
public class SaveRoleDTO {

    /**
     * 角色名
     */
    @NotBlank(message = "角色名不能为空")
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 角色名
     */
    @NotNull(message = "状态不能为空")
    private AvailableStatusEnum status;

    public RoleDO to() {

        RoleDO roleDO = new RoleDO();
        roleDO.setName(this.getName());
        roleDO.setDescription(this.getDescription());
        roleDO.setStatus(this.status);
        return roleDO;
    }


}
