package com.aboutme.springwebservice.board.controller;

import com.aboutme.springwebservice.board.model.*;
import com.aboutme.springwebservice.board.model.response.ResponseDailyLists;
import com.aboutme.springwebservice.board.service.BoardDailyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BoardInfoController {
    //TODO : list에서 담고 있는게 이 함수가 필요할까 확인 필요.

    private final BoardDailyService boardDailyService;

    @GetMapping("/Board/info")
    public BoardVO getBoardInfo(@RequestParam BoardMetaInfoVO boardMetaInfoVO)
    {
        return null;
    }

    @GetMapping("/Board/info/replayList")
    public List<ReplayVO> getBoardReplayListInfo(@RequestParam BoardMetaInfoVO boardMetaInfoVO)
    {
        return null;
    }

    @PostMapping("/Board/dailyColors")
    public String saveDailyColors(@RequestBody BoardVO vo){
        DailyQuestDTO questDTO =  new DailyQuestDTO();
        DailyAnswerDTO answerDTO = new DailyAnswerDTO();

        switch (vo.getColor()){
            case "red":
                questDTO.setColor(0);
                break;
            case "yellow":
                questDTO.setColor(1);
                break;
            case "green":
                questDTO.setColor(2);
                break;
            case "pink":
                questDTO.setColor(3);
                break;
            case "purple":
                questDTO.setColor(4);
                break;
        }
        questDTO.setUser(vo.getUser());
        questDTO.setTitle(vo.getTitle());

        answerDTO.setAnswer(vo.getAnswer());
        answerDTO.setLevel(vo.getLevel());
        answerDTO.setShare(vo.getShare_yn());

        return null;
    }
    @PutMapping("/Board/dailyColors")
    public String updateDailyColors(@RequestBody BoardVO vo){
        DailyQuestDTO questDTO =  new DailyQuestDTO();
        DailyAnswerDTO answerDTO = new DailyAnswerDTO();

        switch (vo.getColor()){
            case "red":
                questDTO.setColor(0);
                break;
            case "yellow":
                questDTO.setColor(1);
                break;
            case "green":
                questDTO.setColor(2);
                break;
            case "pink":
                questDTO.setColor(3);
                break;
            case "purple":
                questDTO.setColor(4);
                break;
        }
        questDTO.setUser(vo.getUser());
        questDTO.setTitle(vo.getTitle());

        answerDTO.setAnswer(vo.getAnswer());
        answerDTO.setLevel(vo.getLevel());
        answerDTO.setShare(vo.getShare_yn());
        return null;
    }
    @DeleteMapping("/Board/dailyColors/{category}")
    public String deleteDailyColors(@PathVariable(name="category") int categorySeq){
        return null;
    }
    @GetMapping(path = "/Board/dailyColors/{user}")
    public ResponseDailyLists getDailyColors(@PathVariable(name = "user") int userId){
        return boardDailyService.getDailyColors(userId);
    }
}
