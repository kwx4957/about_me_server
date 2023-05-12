package com.aboutme.springwebservice.board.controller;

import com.aboutme.springwebservice.board.model.BoardInteractionVO;
import com.aboutme.springwebservice.board.service.BoardInteractionService;
import com.aboutme.springwebservice.entity.BasicResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
public class BoardInteractionController {

    private final BoardInteractionService boardInteractionService;

    @PostMapping("/Board/likes/")
    public ResponseEntity<? extends BasicResponse> createLike(@RequestBody BoardInteractionVO vo)
    {
        return boardInteractionService.addLike(vo);
    }

    @PostMapping("/Board/scrap/")
    public ResponseEntity<? extends BasicResponse> createScrap(@RequestBody BoardInteractionVO vo)
    {
        return boardInteractionService.addScrap(vo);
    }

    @PutMapping("/Board/shares/{cardSeq}/{level}")
    public ResponseEntity<? extends BasicResponse> editIsShare(
            @PathVariable("cardSeq") long cardSeq,
            @PathVariable("level") int level){

        return boardInteractionService.editIsShare(cardSeq,level);
    }
}
