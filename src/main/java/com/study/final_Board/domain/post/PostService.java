package com.study.final_Board.domain.post;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostMapper postMapper;

  @Transactional
  public Long savePost(final PostRequest params) {
    postMapper.save((params));
    return params.getId();
  }


  /**
   * 게시글 수정
   *
   * @param params - 게시글 정보
   * @return PK
   */

  public PostResponse findPostById(final Long id) {
    return postMapper.findById(id);
  }


  @Transactional
  public Long updatePost(final PostRequest params) {
    postMapper.update(params);
    return params.getId();
  }


  /**
   * 게시글 삭제
   *
   * @param id - PK
   * @return PK
   */
  public Long deletePost(final Long id) {
    postMapper.deleteByid(id);
    return id;
  }

  /**
   * 게시글 리스트 조회
   *
   * @return 게시글 리스트
   */
  public List<PostResponse> findAllPost() {
    return postMapper.findAll();

  }
}


