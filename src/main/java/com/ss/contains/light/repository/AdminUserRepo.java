package com.ss.contains.light.repository;

import com.ss.contains.light.base.RepoBase;
import com.ss.contains.light.dos.AdminUserDO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.util.annotation.Nullable;

/**
 * 后台管理用户Repo
 *
 * @author NotEdibleSalt
 */
public interface AdminUserRepo extends RepoBase<AdminUserDO> {

    Flux<AdminUserDO> findByNameLikeAndPhoneLike(Pageable pageable,
                                                 @Nullable @Param("name") String name,
                                                 @Nullable @Param("phone") String phone);
}


