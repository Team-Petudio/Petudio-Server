package com.nice.petudio.domain.gift;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.ValidationException;
import com.nice.petudio.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gifts")
@Builder(access = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Gift extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_id")
    private Long id;

    @Column(name = "buyer_id")
    private Long buyerId; // 기프트 구매자 아이디

    @Column(name = "user_id")
    private Long userId; // 기프트 사용자 아이디

    @Column(name = "gift_code", length = 100, nullable = false)
    private String code;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "expired_at", updatable = false)
    private LocalDateTime expiredAt;

    public static Gift newInstance(Long memberId, String code) {
        return Gift.builder()
                .code(code)
                .buyerId(memberId)
                .isUsed(false)
                .expiredAt(LocalDateTime.now().plusYears(1L))
                .build();
    }

    public void use(final Long userId, LocalDateTime now) {
        validateNotUsed();
        validateNotExpired(now);

        this.userId = userId;
        isUsed = true;
    }

    private void validateNotUsed() {
        if(this.isUsed) {
            throw new ValidationException(ErrorCode.ALREADY_USED_GIFT_EXCEPTION,
                    String.format("이미 사용된 기프트 (GIFT_ID: %d) 입니다.", this.id));
        }
    }

    public void validateNotExpired(LocalDateTime now) {
        if(isExpired(now)) {
            throw new ValidationException(ErrorCode.EXPIRED_GIFT_EXCEPTION,
                    String.format("사용 기한이 만료된 기프트 (GIFT_ID: %d) 입니다.", this.id));
        }
    }

    public boolean isExpired(LocalDateTime now) {
        return expiredAt.isBefore(now);
    }
}
