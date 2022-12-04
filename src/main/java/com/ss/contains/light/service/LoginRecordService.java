package com.ss.contains.light.service;

import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.config.security.AdminUserDetails;
import com.ss.contains.light.controller.dto.query.LoginRecordPagingQuery;
import com.ss.contains.light.dos.LoginRecordDO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

/**
 * @author NotEdibleSalt
 */
@Validated
public interface LoginRecordService {

    /**
     * 分页查询登录记录
     *
     * @param loginRecordPagingQuery 分页查询登录记录参数
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.paging.PagingResult<com.ss.contains.light.dos.LoginRecordDO>>
     */
    Mono<PagingResult<LoginRecordDO>> loginRecordPaging(LoginRecordPagingQuery loginRecordPagingQuery);

    /**
     * 保存登录记录
     *
     * @param token            登录token
     * @return reactor.core.publisher.Mono<java.lang.Void>
     */
    Mono<LoginRecordDO> saveLoginRecord(String token);
}
