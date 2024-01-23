package com.nice.petudio.domain.album.repository;

import static com.nice.petudio.domain.album.QAlbum.album;

import com.nice.petudio.domain.album.Album;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlbumRepositoryImpl implements AlbumRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Album> findByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(album)
                .where(album.memberId.eq(memberId))
                .fetch();
    }
}
