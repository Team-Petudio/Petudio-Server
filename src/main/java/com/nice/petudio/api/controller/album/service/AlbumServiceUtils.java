package com.nice.petudio.api.controller.album.service;

import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.NotFoundException;
import com.nice.petudio.domain.album.Album;
import com.nice.petudio.domain.album.repository.AlbumRepository;
import java.util.List;

public class AlbumServiceUtils {

    public static List<Album> findAlbumsByMemberId(AlbumRepository albumRepository, final Long memberId) {
        return albumRepository.findByMemberId(memberId);
    }

    public static Album findAlbumByAlbumId(AlbumRepository albumRepository, final Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ALBUM_EXCEPTION,
                        String.format("존재하지 않는 앨범ID (%d) 입니다.", albumId)));
    }
}
