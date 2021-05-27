package com.aboutme.springwebservice.message.controller;

import com.aboutme.springwebservice.entity.BasicResponse;
import com.aboutme.springwebservice.message.model.SueJudgeVO;
import com.aboutme.springwebservice.message.model.SueVO;
import com.aboutme.springwebservice.message.service.SueService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
public class SueController {
    private final SueService sueService;

    @GetMapping("/Message/sueList") //신고리스트
    public ResponseEntity<? extends BasicResponse> getSueList(){

        //어드민 계정임을 확인하고 return
//        if(user!=admin) {
//           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("인가된 사용자가 아닙니다.", "401"));
//        }

      return  sueService.sueList();
    }

    @PostMapping("/Message/judgeSue") //신고 판별 거부 또는 승인
    public ResponseEntity<?extends BasicResponse> sueBoard(@RequestBody SueJudgeVO vo){

        //어드민 계정임을 확인하고 return
    //        if(user!=admin) {
    //            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("인가된 사용자가 아닙니다.", "401"));
    //        }
       return sueService.sueBoard(vo);
    }

    @PostMapping("/Message/sue") //신고기능
    public ResponseEntity<? extends BasicResponse> createSue(@RequestBody SueVO vo){
        return sueService.sue(vo);
    }
}
