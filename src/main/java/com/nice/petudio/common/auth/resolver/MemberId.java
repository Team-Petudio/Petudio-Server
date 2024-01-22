package com.nice.petudio.common.auth.resolver;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JWT를 파싱하여 MemberId를 흭득하도록 유도하는 애노테이션
 * Auth 애노테이션과 함께 사용한다.
 *
 * ex)
 * void methodName(@MemberId final Long memberId) {}
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MemberId {
}
