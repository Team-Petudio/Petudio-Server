package com.nice.petudio.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SocialInfo {

    @Column(name = "social_id", length = 300, nullable = false)
    private String socialId;

    @Column(name = "social_type", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    public static SocialInfo from(String id, SocialType type) {
        return new SocialInfo(id, type);
    }
}
