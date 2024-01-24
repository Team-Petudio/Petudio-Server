package com.nice.petudio.domain.member;

import com.nice.petudio.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "fcm_token", length = 300)
    private String fcmToken;

    @Column(name = "email", length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", length = 30, nullable = false)
    private MemberRole role;

    @Embedded
    private SocialInfo socialInfo;

    public static Member newInstance(String socialId, SocialType socialType, String fcmToken, String email) {
        return Member.builder()
                .socialInfo(SocialInfo.of(socialId, socialType))
                .email(email)
                .fcmToken(fcmToken)
                .role(MemberRole.MEMBER)
                .build();
    }

    public void updateFcmToken(final String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
