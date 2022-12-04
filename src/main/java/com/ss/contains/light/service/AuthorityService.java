package com.ss.contains.light.service;

import com.ss.contains.light.common.R;
import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.controller.dto.command.SaveAuthorityDTO;
import com.ss.contains.light.controller.dto.query.AuthoritysPagingQuery;
import com.ss.contains.light.controller.ro.TreeDataRO;
import com.ss.contains.light.dos.AuthorityDO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author NotEdibleSalt
 */
public interface AuthorityService {

    /**
     * 分页查询权限
     *
     * @param authoritysPagingQuery 分页查询权限参数
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.paging.PagingResult < com.ss.contains.light.dos.AuthorityDO>>
     */
    Mono<PagingResult<AuthorityDO>> authorityPaging(AuthoritysPagingQuery authoritysPagingQuery);

    /**
     * 根据id查询权限
     *
     * @param id 权限id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.AuthorityDO>
     */
    Mono<AuthorityDO> authorityById(String id);

    /**
     * 新增权限
     *
     * @param saveAuthorityDTO 保存权限DTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.AuthorityDO>
     */
    Mono<AuthorityDO> addAuthority(@Valid SaveAuthorityDTO saveAuthorityDTO);

    /**
     * 更新权限
     *
     * @param id               权限id
     * @param saveAuthorityDTO 保存权限DTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.AuthorityDO>
     */
    Mono<AuthorityDO> updateAuthority(String id, @Valid SaveAuthorityDTO saveAuthorityDTO);

    /**
     * 删除权限
     *
     * @param id 权限id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.AuthorityDO>h
     */
    Mono<AuthorityDO> delAuthority(String id);

    /**
     * 查询权限树
     *
     * @param
     * @return reactor.core.publisher.Mono<java.util.List<com.ss.contains.light.controller.ro.TreeDataRO>>
     */
    Mono<List<TreeDataRO>> getAuthorityTree();

    /**
     * 查询权限中所有的模块名
     *
     * @return reactor.core.publisher.Flux<java.lang.String>
     */
    Flux<String> getAuthorityAllModule();

    /**
     * 获取管理员权限
     *
     * @param adminId 管理员id
     * @return reactor.core.publisher.Flux<java.lang.String>
     */
    Flux<String> getAdminAuthority(String adminId);
}
