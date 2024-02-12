package com.nice.petudio.api.controller.album;


import com.nice.petudio.api.controller.album.dto.AlbumRetrieveResponses;
import com.nice.petudio.api.controller.album.service.AlbumQueryService;
import com.nice.petudio.api.dto.ApiResponse;
import com.nice.petudio.common.auth.auth.Auth;
import com.nice.petudio.common.auth.resolver.MemberId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AlbumController {
    private final AlbumQueryService albumQueryService;

    @Operation(summary = "[인증] 앨범 목록 조회")
    @Auth
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/albums")
    public ApiResponse<AlbumRetrieveResponses> getMemberAlbumInfos(@MemberId final Long memberId) {
        return ApiResponse.success(albumQueryService.findMemberAlbumInfos(memberId));
    }
}
