package com.nice.petudio.global.auth.resolver;

import static com.nice.petudio.global.auth.auth.AuthInterceptor.MEMBER_ID;

import com.nice.petudio.global.auth.auth.Auth;
import com.nice.petudio.global.exception.InternalServerException;
import com.nice.petudio.global.exception.error.ErrorCode;
import java.util.Optional;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MemberIdResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberId.class) && Long.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Optional<Auth> auth = Optional.ofNullable(parameter.getMethodAnnotation(Auth.class));
        if (auth.isEmpty()) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_EXCEPTION,
                    "@MemberId 애노테이션을 적용한 메서드에 Auth 관련 애노테이션이 존재하지 않습니다.");
        }

        Optional<Object> object = Optional.ofNullable(webRequest.getAttribute(MEMBER_ID, 0));
        return object.orElseThrow(() ->
                new InternalServerException(ErrorCode.INTERNAL_SERVER_EXCEPTION,
                        "memberId를 확인할 수 없습니다. Auth 관련 로직을 확인해주세요."));

    }
}
