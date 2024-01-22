package com.nice.petudio.common.auth.auth;

import com.nice.petudio.domain.member.MemberRole;
import com.nice.petudio.common.auth.handler.AuthCheckHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthCheckHandler authCheckHandler;
    public static final String MEMBER_ID = "memberId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Optional<Auth> auth = Optional.ofNullable(
                    handlerMethod.getMethodAnnotation(Auth.class));

            if(auth.isEmpty()) {
                return true;
            }
            Long memberId = authCheckHandler.validateAuthority(request, List.of(MemberRole.values()));
            request.setAttribute(MEMBER_ID, memberId);
            return true;
        }
        return true;
    }
}
