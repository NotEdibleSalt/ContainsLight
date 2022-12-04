package com.ss.contains.light.controller.dto.command;

import com.ss.contains.light.dos.DictDO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 新增字典DTO
 *
 * @version 1.0
 * @date 2022-5-2
 */
@Data
public class SaveDictDTO {


    /**
     * 名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String name;


    /**
     * 类型
     */
    @NotBlank(message = "字典类型不能为空")
    private String type;

    /**
     * 备注
     */
    private String remark;


    /**
     * 是否系统内置
     */
    private Boolean system;

    public DictDO to() {

        DictDO dictDO = new DictDO();
        dictDO.setName(this.getName());
        dictDO.setType(this.getType());
        dictDO.setRemark(this.getRemark());
        dictDO.setSystem(this.getSystem());
        dictDO.enabled();
        return dictDO;
    }

}
