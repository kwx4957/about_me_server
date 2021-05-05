package com.aboutme.springwebservice.board.controller;

import com.aboutme.springwebservice.board.model.*;
import com.aboutme.springwebservice.board.model.response.ResponseDailyLists;
import com.aboutme.springwebservice.board.repository.QnACategory;
import com.aboutme.springwebservice.board.repository.QnACategoryLevelRepository;
import com.aboutme.springwebservice.board.repository.QnACategoryRepository;
import com.aboutme.springwebservice.board.service.BoardDailyService;
import com.aboutme.springwebservice.domain.repository.UserInfoRepository;
import com.aboutme.springwebservice.mypage.service.UserLevelService;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.aboutme.springwebservice.board.model.BoardMetaInfoVO;
import com.aboutme.springwebservice.board.model.BoardVO;
import com.aboutme.springwebservice.board.model.ReplayVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class BoardInfoController {
    //TODO : list에서 담고 있는게 이 함수가 필요할까 확인 필요.

    public QnACategoryLevelRepository answerRepository;
    public QnACategoryRepository questionRepository;
    public UserInfoRepository infoRepository;

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

        if(!infoRepository.existsById((long)vo.getUser())){
            r.setCode(500);
            r.setMessage("해당 유저가 존재하지 않습니다.");
            return r;
        }
        else{
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
                    r.setCode(500);
                    r.setMessage("색상입력이 잘못되었습니다. 다시 시도해주세요");
                    return r;
            }
            questDTO.setUser(vo.getUser());
            questDTO.setTitle(vo.getTitle());

            int cardSeq = boardDailyService.setDailyStep1(questDTO);

            if(cardSeq == 0){
                r.setCode(500);
                r.setMessage("데이터 내역이 존재하지 않습니다. 다시 시도해주세요");
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

            levelService.updateUserLevelExperience(vo.getUser(),questDTO.getColor(),false);//진행도 증가

            return boardDailyService.setDailyStep2(answerDTO);
        }

    }
    @PutMapping("/Board/dailyColors")
    public ResponseDailyLists updateDailyColors(@RequestBody DailyAnswerDTO ans){
            ResponseDailyLists r = new ResponseDailyLists();
        if(!questionRepository.existsById((long)ans.getCategory_seq())){
            r.setCode(500);
            r.setMessage("해당 질답에 대한 정보가 존재하지 않습니다.");
            return r;
        }
        else return boardDailyService.setDailyStep2(ans);
    }
    @DeleteMapping("/Board/dailyColors/{cardSeq}")
    public String deleteDailyColors(@PathVariable(name="cardSeq") int categorySeq){
        JsonObject o = new JsonObject();
        Optional<QnACategory> quest = questionRepository.findById((long)categorySeq);
        if(quest.isPresent()){
            answerRepository.delCardAnswer(categorySeq);
            questionRepository.delCardQuestion(categorySeq);
            levelService.updateUserLevelExperience( quest.get().getAuthor_id(),quest.get().getColor(),true); //진행도 감소
            o.addProperty("code",200);
            o.addProperty("message","삭제완료");
        }
        else {
            o.addProperty("code",500);
            o.addProperty("message","해당 질답에 대한 정보가 존재하지 않습니다.");
        }
        return o.toString();
    }
    @GetMapping(path = "/Board/dailyColors/{user}")
    public ResponseDailyLists getDailyColors(HttpServletResponse response,@PathVariable(name = "user") int userId){
        if(!infoRepository.existsById((long)userId)){
            ResponseDailyLists r = new ResponseDailyLists();
            r.setCode(500);
            r.setMessage("해당 유저가 존재하지 않습니다.");
            return r;
        }
        else return boardDailyService.getDailyColors(userId);
    }
}
