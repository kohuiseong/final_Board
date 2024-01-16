package com.study.final_Board.domain.post;

import java.util.List;

public interface PostMapper {

  void save(PostRequest params); // 게시글 저장

  PostResponse findById(Long id); // 게시글 상세정보 조회

  void update(PostRequest params); // 게시글 수정




  /**
   * 게시글 리스트 조회
   */
  List<PostResponse> findAll();

  /**
   * 게시글 수 카운팅
   */
  int count();


  /* 게시글 삭제 */
  void deleteByid(Long id);

  void deleteById(Long id);

}

