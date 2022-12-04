package com.ss.contains.light.controller;

import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.controller.dto.query.DictsPagingQuery;
import com.ss.contains.light.controller.dto.query.LoginRecordPagingQuery;
import com.ss.contains.light.dos.DictDO;
import com.ss.contains.light.dos.LoginRecordDO;
import com.ss.contains.light.service.LoginRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 登录记录相关接口
 *
 * @author NotEdibleSalt
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("loginRecords")
public class LoginRecordController {

    private final LoginRecordService loginRecordService;

    /**
     * 分页查询登录记录
     *
     * @param loginRecordPagingQuery 分页查询登录记录参数
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.paging.PagingResult<com.ss.contains.light.dos.LoginRecordDO>>
     */
    @GetMapping()
    public Mono<PagingResult<LoginRecordDO>> loginRecordPaging(LoginRecordPagingQuery loginRecordPagingQuery) {

        return loginRecordService.loginRecordPaging(loginRecordPagingQuery);
    }



}
