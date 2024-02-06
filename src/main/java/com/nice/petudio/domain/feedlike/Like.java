package com.nice.petudio.domain.feedlike;

import com.nice.petudio.domain.base.BaseEntity;
import com.nice.petudio.domain.feed.Feed;
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
@Table(name = "likes")
@Builder(access = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @Column(name = "feed_id", nullable = false)
    private Long feedId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "is_valid", nullable = false)
    private Boolean isValid;

    public static Like newInstance(Long memberId, Long feedId) {
        return Like.builder()
                .feedId(feedId)
                .memberId(memberId)
                .isValid(true)
                .build();
    }
}
