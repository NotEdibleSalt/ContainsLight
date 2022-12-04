package com.ss.contains.light.config.handler;

import com.ss.contains.light.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

/**
 * @version 1.0
 * @date 2022-5-2
 */
@Slf4j
public class CustomizeResponseBodyResultHandler extends ResponseBodyResultHandler {

    private static final R COMMON_RESULT_SUCCESS = R.success();
    private static final MethodParameter METHOD_PARAMETER_MONO_COMMON_RESULT;

    static {
        try {
            // 获得 METHOD_PARAMETER_MONO_COMMON_RESULT 。其中 -1 表示 `#methodForParams()` 方法的返回值
            METHOD_PARAMETER_MONO_COMMON_RESULT = new MethodParameter(CustomizeResponseBodyResultHandler.class.getDeclaredMethod("methodForParams"), -1);
        } catch (NoSuchMethodException e) {
            log.error("[static][获取 METHOD_PARAMETER_MONO_COMMON_RESULT 时，找不到方法");
            throw new RuntimeException(e);
        }
    }

    public CustomizeResponseBodyResultHandler(List<HttpMessageWriter<?>> writers, RequestedContentTypeResolver resolver) {
        super(writers, resolver);
    }

    public CustomizeResponseBodyResultHandler(List<HttpMessageWriter<?>> writers, RequestedContentTypeResolver resolver, ReactiveAdapterRegistry registry) {
        super(writers, resolver, registry);
    }

    private static Mono<R> methodForParams() {
        return null;
    }

    @Override
    public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {

        Object returnValue = result.getReturnValue();
        Object body;

        if (returnValue instanceof Mono) {
            body = ((Mono<R>) returnValue)
                    .map((Function<Object, Object>) CustomizeResponseBodyResultHandler::wrapCommonResult)
                    .defaultIfEmpty(COMMON_RESULT_SUCCESS);

        } else if (returnValue instanceof Flux) {
            body = ((Flux<R>) returnValue)
                    .collectList()
                    .map((Function<Object, Object>) CustomizeResponseBodyResultHandler::wrapCommonResult)
                    .defaultIfEmpty(COMMON_RESULT_SUCCESS);

        } else {
            body = Mono.just(wrapCommonResult(returnValue));
        }
        return writeBody(body, METHOD_PARAMETER_MONO_COMMON_RESULT, exchange);
    }

    private static R<?> wrapCommonResult(Object body) {

        if (body instanceof R) {
            return (R) body;
        }
        return R.success(body);
    }
}
