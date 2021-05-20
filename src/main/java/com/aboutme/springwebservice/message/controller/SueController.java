package com.aboutme.springwebservice.message.controller;

import com.aboutme.springwebservice.board.model.BoardVO;
import com.aboutme.springwebservice.board.service.BoardInteractionService;
import com.aboutme.springwebservice.entity.BasicResponse;
import com.aboutme.springwebservice.message.model.SueVO;
import com.aboutme.springwebservice.message.service.SueService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class SueController {
    private final SueService sueService;

//    @GetMapping("/Message/sueList")
//    public List<SueVO> getSueList(){   }

    @PostMapping("/Message/sue")  //댓글 or 글 구별하기
    public ResponseEntity<? extends BasicResponse> createSue(@RequestBody SueVO sueVO) {
        return sueService.sue(sueVO);
    }
}
