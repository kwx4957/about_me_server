package com.aboutme.springwebservice.board.controller;

import com.aboutme.springwebservice.board.model.BoardInteractionVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardInteractionController {
    @PostMapping("/Board/like")
    public void createLike(@RequestBody BoardInteractionVO boardInteractionVO)
    {
    }

    @PostMapping("/Board/scrap")
    public void createScrap(@RequestBody BoardInteractionVO boardInteractionVO)
    {
    }
}
