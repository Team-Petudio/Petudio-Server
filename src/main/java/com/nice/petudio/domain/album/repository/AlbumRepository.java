package com.nice.petudio.domain.album.repository;

import com.nice.petudio.domain.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends AlbumRepositoryCustom, JpaRepository<Album, Long> {
}
