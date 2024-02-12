package com.nice.petudio.domain.album.repository;

import com.nice.petudio.api.controller.album.dto.AlbumRetrieveResponse;
import com.nice.petudio.domain.album.Album;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.nice.petudio.domain.album.QAlbum.album;
import static com.nice.petudio.domain.concept.QConcept.concept;
import static com.nice.petudio.domain.pet.QPet.pet;

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

    @Override
    public List<AlbumRetrieveResponse> findAlbumInfosByMemberId(Long memberId) {
        List<Tuple> fetch = queryFactory
                .select(album, pet.name, concept.info)
                .from(album)
                .join(album).on(album.petId.eq(pet.id))
                .join(album).on(album.conceptId.eq(concept.id))
                .where(album.memberId.eq(memberId))
                .fetch();

        List<AlbumRetrieveResponse> results = new ArrayList<>();
        for (Tuple tuple : fetch) {
            Album resultAlbum = tuple.get(album);
            String petName = tuple.get(pet.name);
            String conceptName = tuple.get(concept.info).getConceptName();

            results.add(AlbumRetrieveResponse.of(resultAlbum, petName, conceptName));
        }

        return results;
    }
}
