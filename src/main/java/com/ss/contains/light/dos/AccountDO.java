package com.ss.contains.light.dos;

import com.ss.contains.light.base.DOBase;
import com.ss.contains.light.common.enums.AvailableStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 账户DO
 *
 * @author NotEdibleSalt
 */

@Data
@Table("account")
@NoArgsConstructor
@AllArgsConstructor
public class AccountDO extends DOBase implements Serializable {


    /**
     * 账号id
     */

    /**
     * 用户名
     */
    private String username;


    /**
     * 密码
     */
    private String password;

    /**
     * 随机数
     */
    private String random;


    /**
     * 状态 1:enable, 2:disable
     */
    private AvailableStatusEnum status;

    /**
     * 禁用账号
     *
     * @return void
     * @date 2022-5-2
     */
    public void disabled() {
        this.status = AvailableStatusEnum.DISABLE;
    }

    /**
     * 启用账号
     *
     * @return void
     * @date 2022-5-2
     */
    public void enabled() {
        this.status = AvailableStatusEnum.ENABLE;
    }

}
