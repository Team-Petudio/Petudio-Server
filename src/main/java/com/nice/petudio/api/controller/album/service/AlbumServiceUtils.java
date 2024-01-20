package com.nice.petudio.api.controller.album.service;

import com.nice.petudio.domain.album.Album;
import com.nice.petudio.domain.album.repository.AlbumRepository;
import java.util.List;

public class AlbumServiceUtils {

    public static List<Album> findAlbumsByMemberId(AlbumRepository albumRepository, final Long memberId) {
        return albumRepository.findByMemberId(memberId);
    }
}
