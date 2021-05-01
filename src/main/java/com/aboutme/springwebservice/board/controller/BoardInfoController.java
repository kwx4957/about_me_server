package com.aboutme.springwebservice.board.controller;

import com.aboutme.springwebservice.board.model.*;
import com.aboutme.springwebservice.board.model.response.ResponseDailyLists;
import com.aboutme.springwebservice.board.repository.QnACategory;
import com.aboutme.springwebservice.board.repository.QnACategoryLevel;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryRepository;
import com.aboutme.springwebservice.board.service.BoardDailyService;
import com.aboutme.springwebservice.mypage.service.UserLevelService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.aboutme.springwebservice.board.model.BoardMetaInfoVO;
import com.aboutme.springwebservice.board.model.BoardVO;
import com.aboutme.springwebservice.board.model.ReplayVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class BoardInfoController {
    //TODO : list에서 담고 있는게 이 함수가 필요할까 확인 필요.

    @Autowired
    public QnACategoryLevelRepository answerRepository;
    @Autowired
    public QnACategoryRepository questionRepository;

    private final BoardDailyService boardDailyService;
    private final UserLevelService levelService;

    @GetMapping("/Board/info")
    public BoardVO getBoardInfo()
    {
        return null;
    }

    @GetMapping("/Board/info/replayList")
    public List<ReplayVO> getBoardReplayListInfo(@RequestParam BoardMetaInfoVO boardMetaInfoVO)
    {
        return null;
    }

    @PostMapping(value = "/Board/dailyColors" , produces = "application/json;charset=UTF-8")
    public ResponseDailyLists saveDailyColors(@RequestBody BoardVO vo){
        DailyQuestDTO questDTO =  new DailyQuestDTO();
        DailyAnswerDTO answerDTO = new DailyAnswerDTO();

        ResponseDailyLists r = new ResponseDailyLists();

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
            default:
                r.setError("색상입력이 잘못되었습니다.");
                return r;
        }
        questDTO.setUser(vo.getUser());
        questDTO.setTitle(vo.getTitle());

        int cardSeq = boardDailyService.setDailyStep1(questDTO);

        if(cardSeq ==0){
            r.setError("dl입력이 잘못되었습니다.");
        }
        answerDTO.setCategory_seq(cardSeq);
        answerDTO.setLevel(vo.getLevel());
        answerDTO.setAnswer(vo.getAnswer());
        if(!vo.getShare_yn().equals("N")){
            answerDTO.setShare('Y');
        }
        else
            answerDTO.setShare('N');

        System.out.println(cardSeq);
        System.out.println( answerDTO.getLevel());
        System.out.println( answerDTO.getAnswer());

        levelService.updateUserLevelExperience(vo.getUser(),questDTO.getColor(),false);

        return boardDailyService.setDailyStep2(answerDTO);
    }
    @PutMapping("/Board/dailyColors")
    public ResponseDailyLists updateDailyColors(@RequestBody DailyAnswerDTO ans){
        return boardDailyService.setDailyStep2(ans);
    }
    @DeleteMapping("/Board/dailyColors/{cardSeq}")
    public String deleteDailyColors(@PathVariable(name="cardSeq") int categorySeq){
        Optional<QnACategory> quest = questionRepository.findById((long)categorySeq);
        answerRepository.delCardAnswer(categorySeq);
        questionRepository.delCardQuestion(categorySeq);
        levelService.updateUserLevelExperience( quest.get().getAuthor_id(),quest.get().getColor(),true);

        return "{result:삭제완료}";
    }
    @GetMapping(path = "/Board/dailyColors/{user}")
    public ResponseDailyLists getDailyColors(@PathVariable(name = "user") int userId){
        return boardDailyService.getDailyColors(userId);
    }
}
