package com.nice.petudio.api.controller.feed.service;

import com.nice.petudio.api.controller.album.service.AlbumServiceUtils;
import com.nice.petudio.api.controller.feed.dto.PostConceptPhotoRequest;
import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.ConflictException;
import com.nice.petudio.common.exception.model.NotFoundException;
import com.nice.petudio.common.exception.model.ValidationException;
import com.nice.petudio.domain.album.Album;
import com.nice.petudio.domain.album.repository.AlbumRepository;
import com.nice.petudio.domain.feed.Feed;
import com.nice.petudio.domain.feed.repository.FeedRepository;
import com.nice.petudio.domain.feedlike.Like;
import com.nice.petudio.domain.feedlike.repository.LikeRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedCommandService {
    private final FeedRepository feedRepository;
    private final LikeRepository likeRepository;
    private final AlbumRepository albumRepository;

    public void postConceptPhoto(final Long memberId, final PostConceptPhotoRequest request) {
        Album album = AlbumServiceUtils.findAlbumByAlbumId(albumRepository, request.albumId());
        validateAlbumOwner(memberId, request, album);
        validatePhotoDuplication(request, memberId);

        feedRepository.save(Feed.newInstance(memberId, request));
    }

    private void validatePhotoDuplication(PostConceptPhotoRequest request, Long memberId) {
        List<Feed> memberFeeds = feedRepository.findFeedByMemberAndPetAndConceptId(memberId,
                request.petId(), request.conceptId());
        String profileImageUri = request.profileImageUri();

        for (Feed feed : memberFeeds) {
            if (feed.getProfileImageUri().equals(profileImageUri)) {
                throw new ConflictException(ErrorCode.CONCEPT_PHOTO_CONFLICT_EXCEPTION,
                        String.format("요청하신 컨셉 포토 (URI: %s) 는 이미 Feed (ID: %d) 에 게시되어 있습니다.",
                                profileImageUri, feed.getId()));
            }
        }
    }

    private void validateAlbumOwner(Long memberId, PostConceptPhotoRequest request, Album album) {
        if (!album.getMemberId().equals(memberId)) {
            throw new ValidationException(ErrorCode.INVALID_ALBUM_OWNER_EXCEPTION,
                    String.format("피드 게시를 요청한 앨범ID (%d)의 사진은 맴버ID (%d)의 소유가 아닙니다.", request.albumId(), memberId));
        }
    }

    public void likePost(Long memberId, Long feedId) {
        Optional<Like> like = likeRepository.findByMemberAndFeedId(memberId, feedId);
        // 이미 feedId,memberId에 해당하는 Like 레코드가 존재하면 valid 상태만 변경
        if(like.isPresent()) {
            like.get().changeValidStatus();
            return;
        }
        // 존재하지 않으면 Like 레코드 생성
        likeRepository.save(Like.newInstance(memberId, feedId));
    }

    public void deletePost(Long memberId, Long feedId) {
        Feed feed = feedRepository.findFeedByMemberAndFeedId(memberId, feedId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_FEED_EXCEPTION,
                        String.format("회원ID (%d) 가 올린 피드ID (%d) 가 존재하지 않습니다.", memberId, feedId)));

        feedRepository.delete(feed);
    }
}
