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

  public PostResponse findPostById(final Long id) {
    return postMapper.findById(id);
  }

  @Transactional
  public Long updatePost(final PostRequest params) {
    postMapper.update(params);
    return params.getId();
  }

    public Long deletePost(final Long id) {
      postMapper.deleteById(id);
      return id;
    }

    public List<PostResponse> findAllPost() {
      return postMapper.findAll();
    }

  }
