package com.nice.petudio.domain.album.repository;

import com.nice.petudio.api.controller.album.dto.AlbumRetrieveResponse;
import com.nice.petudio.domain.album.Album;

import java.util.List;

public interface AlbumRepositoryCustom {

    List<Album> findByMemberId(Long memberId);
    List<AlbumRetrieveResponse> findAlbumInfosByMemberId(Long memberId);
}
