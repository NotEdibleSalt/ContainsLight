package com.ss.contains.light.dos;

import com.ss.contains.light.base.DOBase;
import com.ss.contains.light.common.enums.AvailableStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 字典表DO
 *
 * @author NotEdibleSalt
 * @date 2022-5-2
 */

@Data
@Table("dict")
@NoArgsConstructor
@AllArgsConstructor
public class DictDO extends DOBase implements Serializable {


    /**
     * 名称
     */
    private String name;


    /**
     * 类型
     */
    private String type;

    /**
     * 备注
     */
    private String remark;


    /**
     * 是否系统内置
     */
    private Boolean system;


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
