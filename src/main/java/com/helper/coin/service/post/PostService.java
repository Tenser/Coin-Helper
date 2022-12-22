package com.helper.coin.service.post;

import com.helper.coin.controller.dto.UserIsOkResponseDto;
import com.helper.coin.controller.dto.post.PostResponseDto;
import com.helper.coin.controller.dto.post.PostSaveRequestDto;
import com.helper.coin.domain.post.Post;
import com.helper.coin.domain.post.PostRepository;
import com.helper.coin.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public UserIsOkResponseDto save(PostSaveRequestDto requestDto){
        postRepository.save(requestDto.toEntity());
        return new UserIsOkResponseDto("OK");
    }

    @Transactional
    public List<PostResponseDto> findByCoinName(String coinName){
        List<Post> Posts = postRepository.findByCoinName(coinName);
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        String userName;
        for(Post post: Posts){
            userName = userRepository.findByUserId(post.getUserId()).getName();
            postResponseDtos.add(new PostResponseDto(post, userName));
        }
        return postResponseDtos;
    }

    @Transactional
    public UserIsOkResponseDto delete(Long id){
        if(postRepository.findById(id) != null){
            postRepository.deleteById(id);
            return new UserIsOkResponseDto("OK");
        }
        return new UserIsOkResponseDto("NO");
    }
}
