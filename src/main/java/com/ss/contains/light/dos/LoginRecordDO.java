package com.ss.contains.light.dos;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import java.io.Serializable;
import com.ss.contains.light.base.DOBase;

/**
 * 登录记录表DO
 *
 * @author NotEdibleSalt
 */

@Data
@Table("login_record")
@NoArgsConstructor
@AllArgsConstructor
public class LoginRecordDO extends DOBase implements Serializable {

    /**
    * 登录用户id
    */
    private String loginUserId;

    /**
    * 登录人
    */
    private String loginName;

    /**
    * 登录账号
    */
    private String loginAccount;

    /**
    * 登录时间
    */
    private LocalDateTime loginTime;

    /**
     * 到期时间
     */
    private LocalDateTime expireDate;

    /**
     * 登录状态
     */
    private LoginStatus loginStatus;

    /**
     * token
     */
    private String token;

    /**
     * 登录状态
     */
    @Getter
    @AllArgsConstructor
    public enum LoginStatus {

        ONLINE("在线"),
        OFFLINE("离线"),
        EXPIRE("离线");

        private final String description;
    }

}
