package com.aboutme.springwebservice.board.controller;

import com.aboutme.springwebservice.board.model.BoardMetaInfoVO;
import com.aboutme.springwebservice.board.model.BoardVO;
import com.aboutme.springwebservice.board.model.ReplayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardInfoController {
    @Autowired

    //TODO : list에서 담고 있는게 이 함수가 필요할까 확인 필요.
    @GetMapping("/Board/info")
    public BoardVO getBoardInfo(@RequestParam(value="questionId") int questionId, @RequestParam(value="authorId") int authorId)
    {
        return null;
    }

    @GetMapping("/Board/info/replayList")
    public List<ReplayVO> getBoardReplayListInfo(@RequestParam BoardMetaInfoVO boardMetaInfoVO)
    {
        return null;
    }


}
