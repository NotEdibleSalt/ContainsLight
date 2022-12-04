package com.ss.contains.light.controller;

import com.ss.contains.light.common.MessageCommon;
import com.ss.contains.light.common.R;
import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.controller.dto.command.SaveAuthorityDTO;
import com.ss.contains.light.controller.dto.query.AuthoritysPagingQuery;
import com.ss.contains.light.controller.ro.TreeDataRO;
import com.ss.contains.light.dos.AuthorityDO;
import com.ss.contains.light.service.AuthorityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * 权限管理Controller
 *
 * @author NotEdibleSalt
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("authoritys")
public class AuthorityController {

    private final AuthorityService authorityService;

    /**
     * 分页查询权限
     *
     * @param authoritysPagingQuery 分页查询权限参数
     * @return com.ss.contains.light.common.paging.PagingResult
     **/
    @GetMapping()
    public Mono<PagingResult<AuthorityDO>> authorityPaging(AuthoritysPagingQuery authoritysPagingQuery) {

        return authorityService.authorityPaging(authoritysPagingQuery);
    }

    /**
     * 根据id查询权限
     *
     * @param id 权限id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.AuthorityDO>
     */
    @GetMapping("/{id}")
    public Mono<AuthorityDO> authorityById(@PathVariable("id") String id) {

        return authorityService.authorityById(id);
    }

    /**
     * 新增权限
     *
     * @param saveAuthorityDTO 保存权限DTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.AuthorityDO>
     */
    @PostMapping("")
    public Mono<AuthorityDO> addAuthority(@RequestBody SaveAuthorityDTO saveAuthorityDTO) {

        return authorityService.addAuthority(saveAuthorityDTO);
    }

    /**
     * 更新权限
     *
     * @param id               权限id
     * @param saveAuthorityDTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.authorityDO>
     **/
    @PutMapping("/{id}")
    public Mono<AuthorityDO> updateAuthority(@PathVariable("id") String id, @RequestBody SaveAuthorityDTO saveAuthorityDTO) {

        return authorityService.updateAuthority(id, saveAuthorityDTO);
    }

    /**
     * 删除权限
     *
     * @param id 权限id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.R < java.lang.Object>>
     */
    @DeleteMapping("{id}")
    public Mono<R<Object>> delAuthority(@PathVariable("id") String id) {

        return authorityService.delAuthority(id).thenReturn(R.message(MessageCommon.DEL_SUCCESS));
    }

    /**
     * 查询权限树
     *
     * @return reactor.core.publisher.Mono<java.util.List<com.ss.contains.light.controller.ro.TreeDataRO>>
     */
    @GetMapping("tree")
    public Mono<List<TreeDataRO>> getAuthorityTree(){

        return authorityService.getAuthorityTree();
    }

    /**
     * 查询权限中所有的模块名
     *
     * @return reactor.core.publisher.Flux<java.lang.String>
     */
    @GetMapping("modules")
    public Flux<String> getAuthorityAllModule(){

        return authorityService.getAuthorityAllModule();
    }
}
