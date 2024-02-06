package com.nice.petudio.domain.gift;

import com.nice.petudio.domain.base.BaseEntity;
import com.nice.petudio.domain.concept.ConceptInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    public static Gift newInstance(Long memberId, String code) {
        return Gift.builder()
                .code(code)
                .buyerId(memberId)
                .isUsed(false)
                .build();
    }

    public void use(final Long userId) {
        this.userId = userId;
        isUsed = true;
    }

    public boolean isUsed() {
        return isUsed;
    }
}
