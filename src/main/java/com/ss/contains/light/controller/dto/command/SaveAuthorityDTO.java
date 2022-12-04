package com.ss.contains.light.controller.dto.command;

import com.ss.contains.light.common.enums.MethodEnum;
import com.ss.contains.light.dos.AuthorityDO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 保存权限参数
 *
 * @author NotEdibleSalt
 */
@Data
public class SaveAuthorityDTO {

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotBlank(message = "所属模块名称名称不能为空")
    private String module;

    @NotBlank(message = "接口路径不能为空")
    private String url;

    private MethodEnum method;

    private String description;

    public AuthorityDO to() {

        AuthorityDO authorityDO = new AuthorityDO();
        authorityDO.setName(this.name);
        authorityDO.setModule(this.module);
        authorityDO.setMethod(this.method);
        authorityDO.setUrl(this.url);
        authorityDO.setDescription(this.description);
        return authorityDO;
    }
}
