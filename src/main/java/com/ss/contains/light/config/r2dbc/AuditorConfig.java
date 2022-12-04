package com.ss.contains.light.config.r2dbc;

import cn.hutool.core.util.StrUtil;
import com.ss.contains.light.base.DOBase;
import com.ss.contains.light.util.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author NotEdibleSalt
 * @version 1.0
 */
@Slf4j
@Configuration
@EnableR2dbcAuditing
class R2dbcAuditingConfig {

    @Bean
    public ReactiveAuditorAware<String> myAuditorProvider() {
        return new SpringSecurityAuditorAware();
    }

    @Bean
    public BeforeConvertCallback<? extends DOBase> idGeneratingCallback() {

        return (doBase, sqlIdentifier) -> {

            if (StrUtil.isBlank(doBase.getId())) {
                doBase.setId(SnowFlakeUtils.getId());
            }
            return Mono.just(doBase);
        };
    }

    /**
     * 查询数据时过滤已被删除的数据
     *
     * @return org.springframework.data.r2dbc.mapping.event.AfterConvertCallback<? extends com.ss.contains.light.base.DOBase>
     */
    @Bean
    public AfterConvertCallback<? extends DOBase> filter() {

        return (entry, table) -> {
            if (!Objects.isNull(entry) && entry.isDel()) {
                return Flux.empty();
            }
            return Flux.just(entry);
        };
    }

}
