package com.nice.petudio.domain.album.repository;

import com.nice.petudio.api.controller.album.dto.AlbumRetrieveResponse;
import com.nice.petudio.api.controller.album.dto.AlbumRetrieveResponses;
import com.nice.petudio.api.controller.album.dto.ProfileImageUrisResponse;
import com.nice.petudio.common.util.JsonUtils;
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
        List<Tuple> result = queryFactory
                .select(album, pet.name, concept.info)
                .from(album)
                .join(pet).on(pet.id.eq(album.petId))
                .join(concept).on(concept.id.eq(album.conceptId))
                .where(album.memberId.eq(memberId))
                .fetch();

        List<AlbumRetrieveResponse> albumRetrieveResponses = new ArrayList<>();
        for (Tuple albumInfo : result) {
            Album resultAlbum = albumInfo.get(album);
            String petName = albumInfo.get(pet.name);
            String conceptName = albumInfo.get(concept.info).getConceptName();
            ProfileImageUrisResponse imageUris = JsonUtils.deserialize(resultAlbum.getProfileImageUris(), ProfileImageUrisResponse.class);
            albumRetrieveResponses.add(AlbumRetrieveResponse.of(resultAlbum, imageUris, petName, conceptName));
        }

        return albumRetrieveResponses;
    }
}
