package com.nice.petudio.common.auth.resolver;

import static com.nice.petudio.common.auth.auth.AuthInterceptor.MEMBER_ID;

import com.nice.petudio.common.auth.admin.Admin;
import com.nice.petudio.common.auth.auth.Auth;
import com.nice.petudio.common.exception.model.InternalServerException;
import com.nice.petudio.common.exception.error.ErrorCode;
import java.util.Optional;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
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
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {
        Optional<Auth> auth = Optional.ofNullable(parameter.getMethodAnnotation(Auth.class));
        Optional<Admin> admin = Optional.ofNullable(parameter.getMethodAnnotation(Admin.class));
        if (auth.isEmpty() && admin.isEmpty()) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_EXCEPTION,
                    "@MemberId 애노테이션을 적용한 메서드에 Auth 관련 애노테이션이 존재하지 않습니다.");
        }

        Optional<Object> object = Optional.ofNullable(webRequest.getAttribute(MEMBER_ID, 0));
        return object.orElseThrow(() ->
                new InternalServerException(ErrorCode.INTERNAL_SERVER_EXCEPTION,
                        "memberId를 확인할 수 없습니다. Auth 관련 로직을 확인해주세요."));

    }
}
