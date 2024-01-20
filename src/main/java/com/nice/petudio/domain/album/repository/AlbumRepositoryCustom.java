package com.nice.petudio.domain.album.repository;

import com.nice.petudio.domain.album.Album;
import java.util.List;

public interface AlbumRepositoryCustom {

    List<Album> findByMemberId(Long memberId);
}
