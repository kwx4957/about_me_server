package com.aboutme.springwebservice.board.controller;

import com.aboutme.springwebservice.board.service.BoardInteractionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@AllArgsConstructor
@RestController
public class BoardInteractionController {

    private final BoardInteractionService boardInteractionService;

    @PostMapping("/Board/{seq}/{userId}/likes/")
    public ResponseEntity<String> createLike(@PathVariable long seq, @PathVariable long userId)  //userid->  @AuthenticationPrincipal MemberAdapter memberAdapter
    {
        boolean result=true; //추후 수정
        boardInteractionService.addLike(userId,seq);
        return result?new ResponseEntity<>(HttpStatus.OK):new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/Board/{seq}/{userId}/scrap/")
    public ResponseEntity<String> createScrap(@PathVariable long seq, @PathVariable long userId)
    {
        boolean result=true; //추후 수정
        boardInteractionService.addScrap(userId,seq);
        return result?new ResponseEntity<>(HttpStatus.OK):new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
