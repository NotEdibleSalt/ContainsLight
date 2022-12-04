package com.ss.contains.light.dos;

import com.ss.contains.light.base.DOBase;
import com.ss.contains.light.common.enums.AvailableStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 角色表DO
 *
 * @author NotEdibleSalt
 */

@Data
@Table("role")
@NoArgsConstructor
@AllArgsConstructor
public class RoleDO extends DOBase implements Serializable {


    /**
     * 角色名称
     */
    private String name;


    /**
     * 角描述
     */
    private String description;


    /**
     * 状态
     */
    private AvailableStatusEnum status;

    /**
     * 禁用账号
     *
     * @date 2022-5-2
     */
    public void disabled() {
        this.status = AvailableStatusEnum.DISABLE;
    }

    /**
     * 启用账号
     *
     * @date 2022-5-2
     */
    public void enabled() {
        this.status = AvailableStatusEnum.ENABLE;
    }

}
