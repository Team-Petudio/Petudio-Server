package com.nice.petudio.common.auth.admin;

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
public class AdminAuthInterceptor implements HandlerInterceptor {

    private final AuthCheckHandler authHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Optional<Admin> adminAuth = Optional.ofNullable(
                    handlerMethod.getMethodAnnotation(Admin.class));

            if (adminAuth.isEmpty()) {
                return true;
            }
            authHandler.validateAuthority(request, List.of(MemberRole.ADMIN));
            return true;
        }
        return true;
    }
}
