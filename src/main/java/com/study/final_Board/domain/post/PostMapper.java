package com.study.final_Board.domain.post;

public interface PostMapper {

  void save(PostRequest params); // 게시글 저장

  PostResponse findById(Long id); // 게시글 상세정보 조회

  void update(PostRequest params); // 게시글 수정
}
