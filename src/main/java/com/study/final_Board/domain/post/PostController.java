package com.study.final_Board.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @GetMapping("/post/write.do")
  public String openPostWrite(Model model) {
    return "post/write";
  }
}
