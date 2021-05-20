package com.aboutme.springwebservice.mypage.controller;

import com.aboutme.springwebservice.domain.repository.UserInfoRepository;
import com.aboutme.springwebservice.mypage.model.*;
import com.aboutme.springwebservice.mypage.model.response.ResponseSelfQnAList;
import com.aboutme.springwebservice.mypage.model.response.ResponseThemeList;
import com.aboutme.springwebservice.mypage.repository.SelfQuestRepository;
import com.aboutme.springwebservice.mypage.service.SelfQuestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class SelfQuestionAnswerController {

    public SelfQuestRepository selfQuestRepository;
    public UserInfoRepository infoRepository;

    private final SelfQuestService selfQuestService;

    //한 주제에 대한 단계별 글 내용 다 생성
    @PostMapping(value="/MyPage/10Q10A/answer")
    public String createSelfQnA(@RequestBody SelfRequestVO sq) throws JsonProcessingException {
        //input json {"user":1,"stage":1,"theme":"진로","answerLists":[{"level":1,"question":"질문이생겼다","answer":"몰라라라랄"},{...}]}
        QuestionAnswerDTO qaDto = new QuestionAnswerDTO();

        JsonObject js = new JsonObject();
        for(int i=0;i<sq.getAnswerLists().size();i++) {
            int j=i+1;
            qaDto.setUser(sq.getUser());
            qaDto.setTheme(sq.getTheme());
            qaDto.setTitle(sq.getAnswerLists().get(i).getQuestion());
            qaDto.setAnswer(sq.getAnswerLists().get(i).getAnswer());
            qaDto.setStage(sq.getStage());
            qaDto.setLevels(sq.getAnswerLists().get(i).getLevel());
            //dto  생성자 순대로 할라고 이렇게 했슴
            String result =selfQuestService.createSelfQuestionAnswer(qaDto);
            if(result.equals("저장 완료"))
                js.addProperty("code",200);
            else  js.addProperty("code",500);
            js.addProperty("message ", result);
        }

        return js.toString();
    }

    //한 주제에 대한 단계별 글 내용 하나 수정
    @PutMapping(value="/MyPage/10Q10A/updateAnswer")
    public String updateSelfQnA(@RequestBody SelfRequestPutVO sq) throws JsonProcessingException {
        //input json {"user":1,"stage":1,"theme":"진로","answerLists":[{"level":1,"question":"질문이생겼다","answer":"몰라라라랄"}]} 바꿀리스트만 인풋
        QuestionAnswerDTO qaDto = new QuestionAnswerDTO();

        String NEW = (sq.getTheme_new()=="" ||  sq.getTheme_new().equals(sq.getTheme())) ? sq.getTheme() : sq.getTheme_new() ;
        System.out.println(NEW);
        JsonObject js = new JsonObject();
        qaDto.setUser(sq.getUser());
        qaDto.setTheme(sq.getTheme());
        qaDto.setTheme_new(NEW);
        qaDto.setTitle(sq.getAnswerLists().get(0).getQuestion());
        qaDto.setAnswer(sq.getAnswerLists().get(0).getAnswer());
        qaDto.setStage(sq.getStage());
        qaDto.setLevels(sq.getAnswerLists().get(0).getLevel());
        String result = selfQuestService.updateSelfQuestionAnswer(qaDto);
        if(result.equals("수정 완료"))
            js.addProperty("code",200);
        else  js.addProperty("code",500);
        js.addProperty("message ", result);

        return js.toString();
    }

    //한 주제에 대한 단계별 글 내용 다 삭제
    @DeleteMapping(path = "/MyPage/10Q10A/{user}/{stage}/{theme}")
    public String deleteSelfQuestTheme( @PathVariable(name = "user") int userId,
                                        @PathVariable(name = "stage") int stage,
                                        @PathVariable(name = "theme") String theme) {

        JsonObject js = new JsonObject();
        if(!infoRepository.existsById((long)userId)){
            js.addProperty("code",400);
            js.addProperty("message","해당 유저가 존재하지 않습니다.");
        }
        else {
            ResponseSelfQnAList r  = selfQuestService.getSelfQuestList(userId, stage, theme);
            if (r.getAnswerLists().isEmpty()) {
                js.addProperty("code",500);
                js.addProperty("message","해당 " + theme + " Ver." + stage + " 에 대한 내용이 존재하지 않습니다.");
            }
            else{
                selfQuestRepository.deleteTheme(userId,stage,theme);
                js.addProperty("code",200);
                js.addProperty("message","삭제 완료");
            }
        }
        return js.toString();
    }

    // 생성주제 리스트 프로시져 결과 이미지 :https://prnt.sc/120scgd
    @GetMapping(path="/MyPage/10Q10A/listDetail/{user}/{stage}/{theme}")
    public ResponseSelfQnAList getSelfQnAList(
            @PathVariable(name = "user") int userId,
            @PathVariable(name = "stage") int stage,
            @PathVariable(name = "theme") String theme){

        ResponseSelfQnAList r = new ResponseSelfQnAList();
        if(!infoRepository.existsById((long)userId)){
            r.setCode(400);
            r.setMessage("해당 유저가 존재하지 않습니다.");
        }
        else {
            r = selfQuestService.getSelfQuestList(userId, stage, theme);
            if (r.getAnswerLists().isEmpty()) {
                r.setCode(500);
                r.setMessage("해당 " + theme + " Ver." + stage + " 에 대한 내용이 존재하지 않습니다.");
                return r;
            }
        }
        return r;
    }

    //주제별 리스트 get 프로시져 결과이미지: https://prnt.sc/120sewo
    @GetMapping(value = "MyPage/10Q10A/theme/{user}")
    public ResponseThemeList getStageList(@PathVariable(name = "user") int userId){
        if(!infoRepository.existsById((long)userId)){
            ResponseThemeList r = new ResponseThemeList();
            r.setCode(400);
            r.setMessage("해당 유저가 존재하지 않습니다.");
            return r;
        }
        return selfQuestService.getThemeList(userId);
    }

    //TODO: 자문자답리스트, 관심리스트, 진행도 표출
}
