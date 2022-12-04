package com.ss.contains.light.controller.dto.command;

import com.ss.contains.light.dos.RoleAuthorityDO;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 保存角色关联的权限参数
 *
 * @author NotEdibleSalt
 */
@Data
public class RoleAssociatedAuthoitysSave {

    private Set<String> authoityIdSet;


    public Set<RoleAuthorityDO> toRoleAuthorityDOSet(String roleId) {

        return authoityIdSet.stream()
                .filter(authorityId -> !authorityId.contains(":"))
                .map(authorityId -> {
                    RoleAuthorityDO roleAuthorityDO = new RoleAuthorityDO();
                    roleAuthorityDO.setRoleId(roleId);
                    roleAuthorityDO.setAuthorityId(authorityId);
                    return roleAuthorityDO;
                })
                .collect(Collectors.toSet());

    }

}
