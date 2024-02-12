package com.nice.petudio.api.controller.album.service;

import com.nice.petudio.api.controller.album.dto.AlbumRetrieveResponse;
import com.nice.petudio.api.controller.album.dto.AlbumRetrieveResponses;
import com.nice.petudio.domain.album.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlbumQueryService {
    private final AlbumRepository albumRepository;

    public AlbumRetrieveResponses findMemberAlbumInfos(Long memberId) {

        List<AlbumRetrieveResponse> albumInfos = albumRepository.findAlbumInfosByMemberId(memberId);
        return new AlbumRetrieveResponses(albumInfos);
    }
}
